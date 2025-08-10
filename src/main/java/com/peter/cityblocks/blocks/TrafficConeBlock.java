package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class TrafficConeBlock extends VariantPartialBlock {

    private static final VoxelShape CONE_SHAPE = VoxelShapes.combine(cube(2, 0, 2, 12, 10, 12), cube(5, 0, 5, 6, 16, 6),
            (a, b) -> a || b);
    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
        new VoxelShape[] {
            CONE_SHAPE,
            CONE_SHAPE,
        }, new VoxelShape[] {
            CONE_SHAPE,
            CONE_SHAPE,
        }, new VoxelShape[] {
            CONE_SHAPE,
            CONE_SHAPE,
        }, new VoxelShape[] {
            CONE_SHAPE,
            CONE_SHAPE,
        }
    };

    private static final Identifier[] MODEL_VARIANTS = new Identifier[] {
            Blocks.blockId("traffic_cone"),
            Blocks.blockId("traffic_cone"),
    };

    public TrafficConeBlock(String name) {
        super(new VariantSettings().setVariants(2).nonOpaque().mapColor(MapColor.ORANGE), name, SHAPES, MODEL_VARIANTS);
    }

}
