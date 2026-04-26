package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SignPostSign1Block extends TextureVariantPartialBlock {

    public SignPostSign1Block(String name, int variants) {
        super(new VariantSettings().setVariants(variants).noOcclusion(), name);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return Shapes.or(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, direction));
    }

}
