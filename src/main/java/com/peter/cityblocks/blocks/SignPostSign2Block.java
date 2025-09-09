package com.peter.cityblocks.blocks;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostSign2Block extends TextureVariantPartialBlock {

    public SignPostSign2Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).nonOpaque(), name);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, direction));
    }
}
