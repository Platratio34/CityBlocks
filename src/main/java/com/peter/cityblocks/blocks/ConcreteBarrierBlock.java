package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConcreteBarrierBlock extends VariantPartialBlock {

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("concrete_barrier"),
            Blocks.blockId("concrete_barrier"),
    };

    public ConcreteBarrierBlock(String name) {
        super(new VariantSettings().setVariants(2).noOcclusion().mapColor(MapColor.COLOR_GRAY), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(4, 0, 0, 8, 18 ,16, direction);
            case 1 -> cube(4, 0, 0, 8, 18, 16, direction);
        
            default -> Shapes.block();
        };
    }

}
