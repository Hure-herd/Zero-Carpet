package com.zero.mixin.soundsuppressionintroduce;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.zero.ZeroSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin {
    @Shadow
    public abstract BlockState getBlockState(BlockPos pos);

    @ModifyExpressionValue(
            method = "setBlockState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BlockEntity;supports(Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private boolean setBlockStateMixin(boolean original, @Local(ordinal = 0) BlockEntity blockEntity) {
        if (!ZeroSettings.soundsuppression) {
            return original;
        }

        BlockState blockState = this.getBlockState(blockEntity.getPos());
        Block block = blockState.getBlock();

        if (blockEntity instanceof CalibratedSculkSensorBlockEntity
                && block instanceof BlockEntityProvider) {
            return true;
        } else {
            return original;
        }
    }
}
