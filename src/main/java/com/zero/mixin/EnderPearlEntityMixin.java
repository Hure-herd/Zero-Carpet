package com.zero.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.zero.ZeroServer;
import com.zero.ZeroSettings;
import com.zero.utils.ChunkUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownEntity {
    protected EnderPearlEntityMixin(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private long highSpeedTick = -1L;

    @Unique
    private long chunkTicketExpiryTicks = 0L;

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void getVector(CallbackInfo ci, @Share("i") LocalIntRef i, @Share("j") LocalIntRef j) {
        i.set(ChunkUtils.getSectionCoordFloored(this.getPos().getX()));
        j.set(ChunkUtils.getSectionCoordFloored(this.getPos().getZ()));
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void loadingChunks(
            CallbackInfo ci,
            @Share("i") LocalIntRef i,
            @Share("j") LocalIntRef j
    ) {
        Entity entity = this.getOwner();
        if(!ZeroSettings.enderpearlloadchunk){
            return;
        }

        double xVel = Math.abs(this.getVelocity().getX());
        double zVel = Math.abs(this.getVelocity().getZ());
        boolean highSpeed = xVel >= 20d || zVel >= 20d;

        if (highSpeed && this.highSpeedTick == -1L) {
            this.highSpeedTick = this.age;
        }

        if (!ZeroServer.shouldKeepPearl && highSpeedTick != -1L && this.age - highSpeedTick > ZeroSettings.Pearltime
        ) {
            ZeroServer.LOGGER.warn(
                    "The pearl(own: {}) has been in high speed for a long time and has been removed",
                    entity instanceof ServerPlayerEntity ? entity.getName().getString() : "unknown"
            );
            this.discard();
        }

        if (this.isAlive()) {
            BlockPos blockPos = BlockPos.ofFloored(this.getPos());
            if (
                    (
                            --this.chunkTicketExpiryTicks <= 0L
                                    || i.get() != ChunkUtils.getSectionCoord(blockPos.getX())
                                    || j.get() != ChunkUtils.getSectionCoord(blockPos.getZ())
                    )
                            && entity instanceof ServerPlayerEntity serverPlayerEntity
            ) {
                this.chunkTicketExpiryTicks = this.handleThrownEnderPearl();
                if (!serverPlayerEntity.getServerWorld().entityList.has(this)) {
                    serverPlayerEntity.getServerWorld().entityList.add(this);
                }
            }
        }
    }

    @Unique
    private long handleThrownEnderPearl() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            ChunkPos chunkPos = this.getChunkPos();
            serverWorld.resetIdleTimeout();
            return ChunkUtils.addEnderPearlTicket(serverWorld, chunkPos) - 1L;
        } else {
            return 0L;
        }
    }
}
