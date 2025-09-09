package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class ConcreteBarrierBlock extends VariantPartialBlock {

    private static final Identifier[] MODEL_VARIANTS = new Identifier[] {
            Blocks.blockId("concrete_barrier"),
            Blocks.blockId("concrete_barrier"),
    };

    public ConcreteBarrierBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque().mapColor(MapColor.GRAY), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(4, 0, 0, 8, 18 ,16, direction);
            case 1 -> cube(4, 0, 0, 8, 18, 16, direction);
        
            default -> VoxelShapes.fullCube();
        };
    }

}
