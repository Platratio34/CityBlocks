package com.peter.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IVariantBlock {

    public BlockState cycle(World world, BlockPos pos, BlockState state, boolean inverse);

    public int getVariant(BlockState state);

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
