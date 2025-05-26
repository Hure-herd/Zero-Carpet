package com.zero.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.zero.ZeroSettings;
import com.zero.utils.PortalPearlWarpUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.particle.ParticleTypes.PORTAL;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlTeleportMixin {

    @WrapOperation(
            method = "canTeleportEntityTo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;canUsePortals(Z)Z"
            )
    )
    private static boolean canTeleportEntityTo(Entity entity, boolean allowVehicles, Operation<Boolean> original) {
        if (ZeroSettings.PortalPearlWarp && entity instanceof EnderPearlEntity) {
            if (PortalPearlWarpUtil.isInRange(entity.getX(), entity.getZ())) {
                return false;
            } else {
                return original.call(entity, allowVehicles);
            }
        } else {
            return original.call(entity, allowVehicles);
        }
    }

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void onPearlHit(HitResult hitResult, CallbackInfo ci) {
        if (ZeroSettings.PortalPearlWarp) {
            ThrownItemEntity pearl = (ThrownItemEntity) (Object) this;
            World world = pearl.getWorld();

            if (world.isClient() || pearl.getStack().getItem() != Items.ENDER_PEARL) return;

            if (hitResult.getType() != HitResult.Type.BLOCK) return;
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            BlockPos hitPos = blockHit.getBlockPos();

            if (!world.getBlockState(hitPos).isOf(Blocks.OBSIDIAN)) return;


            if (PortalPearlWarpUtil.isInRange(pearl.getX(), pearl.getZ())) {

                Entity owner = pearl.getOwner();
                if (!(owner instanceof ServerPlayerEntity player)) return;

                ci.cancel();

                double scale = 1.0;
                if (world.getRegistryKey() == World.OVERWORLD) {
                    scale = 0.125; // 主世界坐标缩小8倍
                } else if (world.getRegistryKey() == World.NETHER) {
                    scale = 8.0; // 地狱坐标扩大8倍
                }

                double tx = hitPos.getX() * scale;
                double ty = hitPos.getY();
                double tz = hitPos.getZ() * scale;

                pearl.discard();

                player.teleport(tx, ty, tz, PORTAL.shouldAlwaysSpawn());
                player.setPosition(tx, ty, tz);
                player.networkHandler.requestTeleport(
                        tx,
                        ty,
                        tz,
                        player.getYaw(),
                        player.getPitch()
                );
            }
        }
    }
}