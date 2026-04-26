package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SignPostSign2Block extends TextureVariantPartialBlock {

    public SignPostSign2Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).noOcclusion(), name);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return Shapes.or(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, direction));
    }
}
