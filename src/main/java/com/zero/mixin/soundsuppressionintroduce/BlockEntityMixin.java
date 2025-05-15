package com.zero.mixin.soundsuppressionintroduce;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.zero.ZeroSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CalibratedSculkSensorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin{

    @Inject(method = "validateSupports", at = @At("HEAD"), cancellable = true)
    private void allowInvalidBlockEntities(BlockState blockState, CallbackInfo ci) {
        if (ZeroSettings.soundsuppression) {
            ci.cancel();
        }
    }

    @WrapWithCondition(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BlockEntity;validateSupports" +
                            "(Lnet/minecraft/block/BlockState;)V"
            )
    )
    private boolean initMixin(BlockEntity instance, BlockState blockState) {
        return !(ZeroSettings.soundsuppression && instance instanceof CalibratedSculkSensorBlockEntity);
    }
}
