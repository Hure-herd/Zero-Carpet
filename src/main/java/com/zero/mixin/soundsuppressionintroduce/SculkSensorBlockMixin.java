package com.zero.mixin.soundsuppressionintroduce;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.zero.ZeroSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlock.class)
public abstract class SculkSensorBlockMixin extends BlockWithEntity {
    protected SculkSensorBlockMixin(Settings settings) {
        super(settings);
    }

    @WrapWithCondition(
            method = "onStateReplaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockWithEntity;onStateReplaced" +
                            "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V"
            )
    )
    private boolean removeOnStateReplacedMixin(
            BlockWithEntity instance,
            BlockState blockState,
            World world,
            BlockPos blockPos,
            BlockState newState,
            boolean b
    ) {
        return !ZeroSettings.soundsuppression;
    }

    @Inject(
            method = "onStateReplaced",
            at = @At("TAIL")
    )
    private void onStateReplacedMixin(
            BlockState state,
            World world,
            BlockPos pos,
            BlockState newState,
            boolean moved,
            CallbackInfo ci
    ) {
        if (ZeroSettings.soundsuppression) {
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
