package com.peter.cityblocks.blocks.signs;

import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.blocks.VariantSettings;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class SignPostOffsetBlock extends VariantPartialBlock {

    public static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                cube(7, 0, 0, 2, 16, 2, Direction.SOUTH),
                cube(7, 0, 0, 2, 16, 2, Direction.SOUTH),
            },
            new VoxelShape[] {
                cube(7, 0, 0, 2, 16, 2, Direction.WEST),
                cube(7, 0, 0, 2, 16, 2, Direction.WEST),
            },
            new VoxelShape[] {
                cube(7, 0, 0, 2, 16, 2, Direction.NORTH),
                cube(7, 0, 0, 2, 16, 2, Direction.NORTH),
            },
            new VoxelShape[] {
                cube(7, 0, 0, 2, 16, 2, Direction.EAST),
                cube(7, 0, 0, 2, 16, 2, Direction.EAST),
            }
    };

    public static final Identifier[] MODEL_VARIANTS = new Identifier[] {
        Blocks.blockId("sign_post_offset"),
        Blocks.blockId("sign_post_offset")
    };

    public SignPostOffsetBlock(String name) {
        super(new VariantSettings().setVariants(MODEL_VARIANTS.length).nonOpaque(), name, SHAPES, MODEL_VARIANTS);
    }

}
