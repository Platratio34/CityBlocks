package com.peter.cityblocks.blocks;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostSign1Block extends TextureVariantPartialBlock {

    public SignPostSign1Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).nonOpaque(), name);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, direction));
    }

}
