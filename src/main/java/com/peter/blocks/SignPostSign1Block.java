package com.peter.blocks;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostSign1Block extends TextureVariantPartialBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[] {
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.NORTH)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.EAST)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.SOUTH)),
            VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.WEST))
    };

    public SignPostSign1Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).nonOpaque(), name, SHAPES);
    }

}
