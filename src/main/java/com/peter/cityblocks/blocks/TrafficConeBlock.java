package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class TrafficConeBlock extends VariantPartialBlock {

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
        new VoxelShape[] {
            cube(2, 0, 2, 12, 16 ,12),
        }, new VoxelShape[] {
            cube(2, 0, 2, 12, 16 ,12, Direction.EAST),
        }, new VoxelShape[] {
            cube(2, 0, 2, 12, 16 ,12, Direction.SOUTH),
        }, new VoxelShape[] {
            cube(2, 0, 2, 12, 16 ,12, Direction.WEST),
        }
    };

    public TrafficConeBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque().mapColor(MapColor.ORANGE), name, SHAPES);
    }

}
