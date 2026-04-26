package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SignPostBlock extends VariantPartialBlock {

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("sign_post_post"),
            Blocks.blockId("sign_post_sign_3"),
    };

    public SignPostBlock(String name) {
        super(new VariantSettings().setVariants(2).noOcclusion(), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(7.5, 0, 7.5, 1, 16, 1);
            case 1 -> Shapes.or(cube(7.5, 0, 7.5, 1, 16, 1), cube(2, 1, 8, 12, 14, 1, direction));
            case 2 -> Shapes.or(cube(7.5, 0, 7.5, 1, 16, 1), cube(0, 0, 8, 16, 16, 1, direction));
        
            default -> Shapes.block();
        };
    }

}
