package com.peter.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class ConcreteBarrierBlock extends VariantPartialBlock {

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
        new VoxelShape[] {
            cube(4, 0, 0, 8, 18 ,16),
            cube(4, 0, 0, 8, 18 ,16),
        }, new VoxelShape[] {
            cube(4, 0, 0, 8, 18 ,16, Direction.EAST),
            cube(4, 0, 0, 8, 18 ,16, Direction.EAST),
        }, new VoxelShape[] {
            cube(4, 0, 0, 8, 18 ,16, Direction.SOUTH),
            cube(4, 0, 0, 8, 18 ,16, Direction.SOUTH),
        }, new VoxelShape[] {
            cube(4, 0, 0, 8, 18 ,16, Direction.WEST),
            cube(4, 0, 0, 8, 18 ,16, Direction.WEST),
        }
    };

    public ConcreteBarrierBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque().mapColor(MapColor.GRAY), name, SHAPES);
    }

}
