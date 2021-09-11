package ovh.snet.mc.blazecane;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class EnderPearlCaneBlock extends SugarCaneBlock {
    public EnderPearlCaneBlock(Properties properties) {
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
            if (blockstate.is(Blocks.END_STONE) ) {
                BlockPos blockpos = blockPos.below();

                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockstate1 = worldReader.getBlockState(blockpos.relative(direction));
                    if (blockstate1.is(Blocks.OBSIDIAN)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        boolean isValidPlace = state.is(Blocks.END_STONE);
        if (!isValidPlace) return false;
        boolean hasObsidian = false;
        for (Direction face : Direction.Plane.HORIZONTAL) {
            BlockState blockState = world.getBlockState(pos.relative(face));
            hasObsidian |= blockState.is(Blocks.OBSIDIAN);
            if (hasObsidian)
                break; //No point continuing.
        }
        return hasObsidian;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.get("ender_pearl_cane");
    }

}
