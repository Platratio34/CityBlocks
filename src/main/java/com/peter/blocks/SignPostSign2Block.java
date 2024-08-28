package com.peter.blocks;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostSign2Block extends TextureVariantPartialBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[] {
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.NORTH)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.EAST)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.SOUTH)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.WEST))
    };

    public SignPostSign2Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).nonOpaque(), name, SHAPES);
    }
}
