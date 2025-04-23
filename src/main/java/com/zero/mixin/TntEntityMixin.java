package com.zero.mixin;

import carpet.CarpetSettings;
import carpet.fakes.TntEntityInterface;
import carpet.logging.LoggerRegistry;
import carpet.logging.logHelpers.TNTLogHelper;
import com.zero.ZeroSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TntEntity.class)
public abstract class TntEntityMixin extends Entity implements TntEntityInterface
{
    @Shadow public abstract int getFuse();

    private TNTLogHelper logHelper;
    private boolean mergeBool = false;
    private int mergedTNT = 1;

    public TntEntityMixin(EntityType<?> entityType_1, World world_1)
    {
        super(entityType_1, world_1);
    }


    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/LivingEntity;)V",
            at = @At("RETURN"))
    private void modifyTNTAngle(World world, double x, double y, double z, LivingEntity entity, CallbackInfo ci)
    {
        if (CarpetSettings.hardcodeTNTangle != -1.0D)
            setVelocity(-Math.sin(CarpetSettings.hardcodeTNTangle) * 0.02, 0.2, -Math.cos(CarpetSettings.hardcodeTNTangle) * 0.02);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void initTNTLoggerPrime(EntityType<? extends TntEntity> entityType_1, World world_1, CallbackInfo ci)
    {
        if (LoggerRegistry.__tnt && !world_1.isClient)
        {
            logHelper = new TNTLogHelper();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void initTracker(CallbackInfo ci)
    {
        if (LoggerRegistry.__tnt && logHelper != null && !logHelper.initialized)
        {
            logHelper.onPrimed(getX(), getY(), getZ(), getVelocity());
        }
    }


    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/LivingEntity;)V",
            at = @At(value = "RETURN"))
    private void initTNTLogger(World world_1, double double_1, double double_2, double double_3,
                               LivingEntity livingEntity_1, CallbackInfo ci)
    {
        if(CarpetSettings.tntPrimerMomentumRemoved)
            this.setVelocity(new Vec3d(0.0, 0.20000000298023224D, 0.0));
    }

    @Inject(method = "explode", at = @At(value = "HEAD"))
    private void onExplode(CallbackInfo ci) {
        if (LoggerRegistry.__tnt && logHelper != null)
            logHelper.onExploded(getX(), getY(), getZ(), this.getWorld().getTime());

        if (mergedTNT > 1)
            for (int i = mergedTNT; i < mergedTNT - 1; i++){
                this.getWorld().createExplosion(this, this.getX(), this.getBodyY(0.0625),
                        this.getZ(), 4.0F, World.ExplosionSourceType.TNT);
            }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/TntEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
            ordinal = 1))
    private void tryMergeTnT(CallbackInfo ci)
    {
        if(ZeroSettings.mergeTNTPro){
            Vec3d velocity = getVelocity();
            if(!getEntityWorld().isClient && mergeBool && velocity.x == 0 && velocity.y == 0 && velocity.z == 0){
                mergeBool = false;
                for(Entity entity : getEntityWorld().getOtherEntities(this, this.getBoundingBox())){
                    if(entity instanceof TntEntity && !entity.isRemoved()){
                        TntEntity entityTNTPrimed = (TntEntity)entity;
                        Vec3d tntVelocity = entityTNTPrimed.getVelocity();
                        if(tntVelocity.x == 0 && tntVelocity.y == 0 && tntVelocity.z == 0
                                && this.getX() == entityTNTPrimed.getX() && this.getZ() == entityTNTPrimed.getZ() && this.getY() == entityTNTPrimed.getY()
                                && getFuse() == entityTNTPrimed.getFuse() +1){
                            mergedTNT += ((TntEntityInterface) entityTNTPrimed).getMergedTNT();
                            entityTNTPrimed.remove(RemovalReason.DISCARDED);
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/TntEntity;setFuse(I)V",
            ordinal = 0))
    private void setMergeable(CallbackInfo ci)
    {
        Vec3d velocity = getVelocity();
        if(!getEntityWorld().isClient && (velocity.y != 0 || velocity.x != 0 || velocity.z != 0)){
            mergeBool = true;
        }
    }

    @Override
    public int getMergedTNT() {
        return mergedTNT;
    }
}