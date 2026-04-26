package com.peter.cityblocks.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrafficConeBlock extends VariantPartialBlock {

    private static final VoxelShape CONE_SHAPE = Shapes.joinUnoptimized(cube(2, 0, 2, 12, 10, 12), cube(5, 0, 5, 6, 16, 6),
            (a, b) -> a || b);
    private static final VoxelShape BARREL_SHAPE = cube(2, 0, 2, 12, 22, 12);
    private static final VoxelShape STICK_SHAPE = Shapes.joinUnoptimized(cube(6, 0, 6, 4, 23, 4), cube(1,0,1,14,1,14), (a,b) -> a || b);

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("traffic_cone"),
            Blocks.blockId("traffic_barrel"),
            Blocks.blockId("traffic_stick"),
    };

    public TrafficConeBlock(String name) {
        super(new VariantSettings().setVariants(MODEL_VARIANTS.length).noOcclusion().mapColor(MapColor.COLOR_ORANGE), name, MODEL_VARIANTS);
    }
    
    @Override
    public VoxelShape getShape(int variant, Direction dir) {
        return switch (variant) {
            case 0 -> CONE_SHAPE;
            case 1 -> BARREL_SHAPE;
            case 2 -> STICK_SHAPE;
            default -> Shapes.block();
        };
    }

}
