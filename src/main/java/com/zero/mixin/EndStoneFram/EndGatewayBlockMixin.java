package com.zero.mixin.EndStoneFram;

import com.zero.ZeroSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EndGatewayBlock.class)
public abstract class EndGatewayBlockMixin extends Block {

    public EndGatewayBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            method = "onEntityCollision",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onEntityCollisionCheck(
            BlockState state,
            World world,
            BlockPos pos,
            Entity entity,
            CallbackInfo ci
    ) {
        if(ZeroSettings.endstonefram) {
            if (!world.isClient && entity.canUsePortals(false)) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof EndGatewayBlockEntity) {
                    EndGatewayBlockEntity endGateway = (EndGatewayBlockEntity) blockEntity;

                    boolean hasNoExit = endGateway.exitPortalPos == null;

                    boolean noHopperMinecart = true;
                    boolean hasValidHopper = true;

                    if(pos.getX() == 163 && pos.getZ() == 767 && entity instanceof ItemEntity){
                        ci.cancel();
                    }

                    if (pos.getX() > 400 ||pos.getZ() > 400) {
                        if (hasNoExit) {
                            Box detectionArea = new Box(pos.down(2)).expand(0);
                            List<HopperMinecartEntity> minecarts = world.getEntitiesByClass(
                                    HopperMinecartEntity.class,
                                    detectionArea,
                                    Entity::isAlive
                            );
                            noHopperMinecart = minecarts.isEmpty();
                            hasValidHopper = checkPointingHoppersWithItems(world, pos);
                        }
                    }
                    if (hasNoExit && hasValidHopper && noHopperMinecart) {
                        ci.cancel();
                    }
                }
            }
        }
    }
    private boolean checkPointingHoppersWithItems(World world, BlockPos gatewayPos) {
        for (Direction direction : Direction.values()) {
            BlockPos hopperPos = gatewayPos.offset(direction);
            BlockState hopperState = world.getBlockState(hopperPos);

            if (hopperState.getBlock() == Blocks.HOPPER) {
                Direction facing = hopperState.get(HopperBlock.FACING);
                if (facing == direction.getOpposite()) {
                    BlockEntity hopperEntity = world.getBlockEntity(hopperPos);
                    if (hopperEntity instanceof HopperBlockEntity) {
                        Inventory inventory = (Inventory) hopperEntity;
                        for (int i = 0; i < inventory.size(); i++) {
                            if (!inventory.getStack(i).isEmpty()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}

