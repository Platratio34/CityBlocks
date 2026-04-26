package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrashBarrierBlock extends VariantPartialBlock {

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("crash_barrier_straight"),
            Blocks.blockId("crash_barrier_outside"),
            Blocks.blockId("crash_barrier_inside"),
            Blocks.blockId("crash_barrier_end1"),
            Blocks.blockId("crash_barrier_end2"),
            Blocks.blockId("crash_barrier_straight_no_post")
    };

    public CrashBarrierBlock(String name) {
        super(new VariantSettings().setVariants(6).noOcclusion().mapColor(MapColor.SNOW), name, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> Shapes.or(cube(8, 0, 7, 2, 16, 2, direction), cube(6, 7, 0, 2, 8, 16, direction));
            case 1 -> Shapes.or(cube(8, 0, 6, 2, 16, 2, direction), cube(6, 7, 0, 2, 8, 10, direction), cube(8, 7, 8, 8, 8, 2, direction));
            case 2 -> Shapes.or(cube(8, 0, 5, 2, 16, 2, direction), cube(5, 0, 8, 2, 16, 2, direction), cube(6, 7, 0, 2, 8, 6, direction), cube(0, 7, 6, 8, 8, 2, direction));
            case 3 -> Shapes.or(cube(8, 0, 7, 2, 16, 2, direction), cube(6, 7, 0, 2, 8, 16, direction), cube(5, 7, 14, 6, 8, 2, direction));
            case 4 -> Shapes.or(cube(8, 0, 7, 2, 16, 2, direction), cube(6, 7, 0, 2, 8, 16, direction), cube(5, 7, 0, 6, 8, 2, direction));
            case 5 -> cube(6, 7, 0, 2, 8, 16);
        
            default -> Shapes.block();
        };
    }

}
