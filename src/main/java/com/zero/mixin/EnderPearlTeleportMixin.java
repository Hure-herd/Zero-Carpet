package com.zero.mixin;

import com.zero.ZeroSettings;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.particle.ParticleTypes.PORTAL;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlTeleportMixin {

    @Redirect(method ="canTeleportEntityTo",at=@At(value = "INVOKE",target = "Lnet/minecraft/entity/Entity;canUsePortals(Z)Z"))
    private static boolean canTeleportEntityTo(Entity pearl, boolean b){
        if(ZeroSettings.PortalPearlWarp && pearl instanceof EnderPearlEntity){
            if(pearl.getX() > 914 && pearl.getX() < 916 && pearl.getZ() > 914 && pearl.getZ() < 916
                    || pearl.getX() > 7323 && pearl.getX() < 7325 && pearl.getZ() > 7323 && pearl.getZ() < 7325
                    || pearl.getX() > 58591 && pearl.getX() < 58593 && pearl.getZ() > 58591 && pearl.getZ() < 58593
                    || pearl.getX() > 468742 && pearl.getX() < 468744 && pearl.getZ() > 468742 && pearl.getZ() < 468744
                    || pearl.getX() < -7323 && pearl.getX() > -7325 && pearl.getZ() < -7323 && pearl.getZ() > -7325
                    || pearl.getX() < -58591 && pearl.getX() > -58593 && pearl.getZ() < -58591 && pearl.getZ() > -58593
                    || pearl.getX() < -468742 && pearl.getX() > -468744 && pearl.getZ() < -468742 && pearl.getZ() > -468744 //地狱的地狱门位置
                    || pearl.getX() > 29999599 && pearl.getX() < 29999601 && pearl.getZ() > 29999599 && pearl.getZ() < 29999601
                    || pearl.getX() > 3749941 && pearl.getX() < 3749943 && pearl.getZ() > 3749941 && pearl.getZ() < 3749943
                    || pearl.getX() > 468734 && pearl.getX() < 468736 && pearl.getZ() > 468734 && pearl.getZ() < 468736
                    || pearl.getX() > 58584 && pearl.getX() < 58586 && pearl.getZ() > 58584 && pearl.getZ() < 58586
                    || pearl.getX() > 7314 && pearl.getX() < 7316 && pearl.getZ() > 7314 && pearl.getZ() < 7316
                    || pearl.getX() < -29999599 && pearl.getX() > -29999601 && pearl.getZ() < -29999599 && pearl.getZ() > -29999601
                    || pearl.getX() < -3749941 && pearl.getX() > -3749943 && pearl.getZ() < -3749941 && pearl.getZ() > -3749943
                    || pearl.getX() < -468734 && pearl.getX() > -468736 && pearl.getZ() < -468734 && pearl.getZ() > -468736
                    || pearl.getX() < -58584 && pearl.getX() > -58586 && pearl.getZ() < -58584 && pearl.getZ() > -58586
                    || pearl.getX() < -7314 && pearl.getX() > -7316 && pearl.getZ() < -7314 && pearl.getZ() > -7316
            ){
                return false;}
            else {
                return true;
            }
        }
        return true;
    }

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void onPearlHit(HitResult hitResult, CallbackInfo ci) {
        if(ZeroSettings.PortalPearlWarp){
        ThrownItemEntity pearl = (ThrownItemEntity) (Object) this;
        World world = pearl.getWorld();

        if (world.isClient() || pearl.getStack().getItem() != Items.ENDER_PEARL) return;

        if (hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockHitResult blockHit = (BlockHitResult) hitResult;
        BlockPos hitPos = blockHit.getBlockPos();

        if (!world.getBlockState(hitPos).isOf(Blocks.OBSIDIAN)) return;


        if(pearl.getX() > 914 && pearl.getX() < 916 && pearl.getZ() > 914 && pearl.getZ() < 916
            || pearl.getX() > 7323 && pearl.getX() < 7325 && pearl.getZ() > 7323 && pearl.getZ() < 7325
            || pearl.getX() > 58591 && pearl.getX() < 58593 && pearl.getZ() > 58591 && pearl.getZ() < 58593
            || pearl.getX() > 468742 && pearl.getX() < 468744 && pearl.getZ() > 468742 && pearl.getZ() < 468744
            || pearl.getX() < -7323 && pearl.getX() > -7325 && pearl.getZ() < -7323 && pearl.getZ() > -7325
            || pearl.getX() < -58591 && pearl.getX() > -58593 && pearl.getZ() < -58591 && pearl.getZ() > -58593
            || pearl.getX() < -468742 && pearl.getX() > -468744 && pearl.getZ() < -468742 && pearl.getZ() > -468744 //地狱的地狱门位置
            || pearl.getX() > 29999599 && pearl.getX() < 29999601 && pearl.getZ() > 29999599 && pearl.getZ() < 29999601
            || pearl.getX() > 3749941 && pearl.getX() < 3749943 && pearl.getZ() > 3749941 && pearl.getZ() < 3749943
            || pearl.getX() > 468734 && pearl.getX() < 468736 && pearl.getZ() > 468734 && pearl.getZ() < 468736
            || pearl.getX() > 58584 && pearl.getX() < 58586 && pearl.getZ() > 58584 && pearl.getZ() < 58586
            || pearl.getX() > 7314 && pearl.getX() < 7316 && pearl.getZ() > 7314 && pearl.getZ() < 7316
            || pearl.getX() < -29999599 && pearl.getX() > -29999601 && pearl.getZ() < -29999599 && pearl.getZ() > -29999601
            || pearl.getX() < -3749941 && pearl.getX() > -3749943 && pearl.getZ() < -3749941 && pearl.getZ() > -3749943
            || pearl.getX() < -468734 && pearl.getX() > -468736 && pearl.getZ() < -468734 && pearl.getZ() > -468736
            || pearl.getX() < -58584 && pearl.getX() > -58586 && pearl.getZ() < -58584 && pearl.getZ() > -58586
            || pearl.getX() < -7314 && pearl.getX() > -7316 && pearl.getZ() < -7314 && pearl.getZ() > -7316 //主世界的地狱门位置
            ){

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
        player.setPosition(tx,ty,tz);
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