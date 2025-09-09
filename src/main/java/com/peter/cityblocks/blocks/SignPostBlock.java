package com.peter.cityblocks.blocks;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class SignPostBlock extends VariantPartialBlock {

    private static final Identifier[] MODEL_VARIANTS = new Identifier[] {
            Blocks.blockId("sign_post_post"),
            Blocks.blockId("sign_post_sign_3"),
    };

    public SignPostBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque(), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(7.5, 0, 7.5, 1, 16, 1);
            case 1 -> VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, direction));
            case 2 -> VoxelShapes.union(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, direction));
        
            default -> VoxelShapes.fullCube();
        };
    }

}
