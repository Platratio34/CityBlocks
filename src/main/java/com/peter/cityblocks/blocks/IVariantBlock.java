package com.peter.cityblocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IVariantBlock {

    public BlockState cycle(Level world, BlockPos pos, BlockState state, boolean inverse);

    public int getVariant(BlockState state);

    public String[] getVariants(BlockState state);

    public static int cycleInt(int v, int max) {
        return cycleInt(v, 0, max, false);
    }
    public static int cycleInt(int v, int min, int max) {
        return cycleInt(v, min, max, false);
    }
    public static int cycleInt(int v, int max, boolean inverse) {
        return cycleInt(v, 0, max, inverse);
    }
    public static int cycleInt(int v, int min, int max, boolean inverse) {
        if (inverse) {
            v--;
            if (v < min) {
                return max;
            }
        } else {
            v++;
            if (v > max) {
                return min;
            }
        }
        return v;
    }
}
