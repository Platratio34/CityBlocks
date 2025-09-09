package com.peter.cityblocks.blocks.signs;

import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.blocks.VariantSettings;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class SignPostOffsetBlock extends VariantPartialBlock {

    public static final Identifier[] MODEL_VARIANTS = new Identifier[] {
        Blocks.blockId("sign_post_offset"),
        Blocks.blockId("sign_post_offset")
    };

    public SignPostOffsetBlock(String name) {
        super(new VariantSettings().setVariants(MODEL_VARIANTS.length).nonOpaque(), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return cube(7, 0, 0, 2, 16, 2, direction.getOpposite());
    }

}
