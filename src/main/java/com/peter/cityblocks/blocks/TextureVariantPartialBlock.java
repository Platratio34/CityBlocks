package com.peter.cityblocks.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class TextureVariantPartialBlock extends VariantPartialBlock {

    private final VoxelShape[] shapes;

    public TextureVariantPartialBlock(Settings settings, String name, VoxelShape[] shapes) {
        super(settings, name, null);
        this.shapes = shapes;
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        if (shapes == null) {
            return VoxelShapes.cuboid(0, 0, 0, 1, 1, 1);
        }
        return shapes[getDir(state)];
    }

}
