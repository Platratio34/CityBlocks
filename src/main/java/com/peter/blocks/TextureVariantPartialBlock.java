package com.peter.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;

public class TextureVariantPartialBlock extends VariantPartialBlock {

    private final VoxelShape[] shapes;

    public TextureVariantPartialBlock(Settings settings, String name, VoxelShape[] shapes) {
        super(settings, name, null);
        this.shapes = shapes;
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return shapes[getDir(state)];
    }

}
