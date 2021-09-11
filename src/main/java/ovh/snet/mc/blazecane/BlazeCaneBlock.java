package ovh.snet.mc.blazecane;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class BlazeCaneBlock extends SugarCaneBlock {
    public BlazeCaneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos blockPos) {
         BlockState soil = worldReader.getBlockState(blockPos.below());
        if (soil.canSustainPlant(worldReader, blockPos.below(), Direction.UP, this)) return true;
        BlockState blockstate = worldReader.getBlockState(blockPos.below());
        if (blockstate.getBlock() == this) {
            return true;
        } else {
            if (blockstate.is(Blocks.SOUL_SAND) || blockstate.is(Blocks.SOUL_SOIL) ) {
                BlockPos blockpos = blockPos.below();

                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockstate1 = worldReader.getBlockState(blockpos.relative(direction));
                    FluidState fluidstate = worldReader.getFluidState(blockpos.relative(direction));
                    if (fluidstate.is(FluidTags.LAVA) || blockstate1.is(Blocks.MAGMA_BLOCK)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        boolean isValidPlace = state.is(Blocks.SOUL_SOIL) || state.is(Blocks.SOUL_SAND);
        if (!isValidPlace) return false;
        boolean hasLava = false;
        for (Direction face : Direction.Plane.HORIZONTAL) {
            BlockState blockState = world.getBlockState(pos.relative(face));
            net.minecraft.fluid.FluidState fluidState = world.getFluidState(pos.relative(face));
            hasLava |= blockState.is(Blocks.MAGMA_BLOCK);
            hasLava |= fluidState.is(FluidTags.LAVA);
            if (hasLava)
                break; //No point continuing.
        }
        return hasLava;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.get("blaze_cane");
    }

}
