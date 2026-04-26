package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CableBarrierBlock extends VariantPartialBlock {

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("cable_barrier_post_middle"),
            Blocks.blockId("cable_barrier_cable_middle"),
            Blocks.blockId("cable_barrier_post_end"),
            Blocks.blockId("cable_barrier_cable_end"),
    };

    public CableBarrierBlock(String name) {
        super(new VariantSettings().setVariants(4).noOcclusion().mapColor(MapColor.COLOR_GRAY), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> Shapes.or(cube(7, 0, 7, 2, 16, 2, direction),
                            cube(0, 3, 7.5, 16, 1, 1, direction), cube(0, 8, 7.5, 16, 1, 1, direction),
                            cube(0, 13, 7.5, 16, 1, 1, direction));
            case 1 -> Shapes.or(cube(0, 3, 7.5, 16, 1, 1, direction),
                            cube(0, 8, 7.5, 16, 1, 1, direction), cube(0, 13, 7.5, 16, 1, 1, direction));
            case 2 -> Shapes.or(cube(7, 0, 7, 2, 16, 2, direction),
                            cube(0, 3, 7.5, 8, 1, 1, direction), cube(0, 8, 7.5, 8, 1, 1, direction),
                            cube(0, 13, 7.5, 8, 1, 1, direction));
            case 3 -> Shapes.or(cube(0, 0, 7.5, 4, 13, 1, direction),
                    cube(4, 0, 7.5, 4, 10, 1, direction), cube(8, 0, 7.5, 4, 6, 1, direction));
            default -> Shapes.block();
        };
    }

}
