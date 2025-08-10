package com.peter.cityblocks.blocks;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostBlock extends VariantPartialBlock {

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                    cube(7.5, 0, 7.5, 1, 16, 1),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.NORTH)),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.NORTH))
            },
            new VoxelShape[] {
                    cube(7.5, 0, 7.5, 1, 16, 1),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.EAST)),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.EAST))
            },
            new VoxelShape[] {
                    cube(7.5, 0, 7.5, 1, 16, 1),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.SOUTH)),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.SOUTH))
            },
            new VoxelShape[] {
                    cube(7.5, 0, 7.5, 1, 16, 1),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, Direction.WEST)),
                    VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, Direction.WEST))
            }
    };

    private static final Identifier[] MODEL_VARIANTS = new Identifier[] {
            Blocks.blockId("sign_post_post"),
            Blocks.blockId("sign_post_sign_3"),
    };

    public SignPostBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque(), name, SHAPES, MODEL_VARIANTS);
    }

}
