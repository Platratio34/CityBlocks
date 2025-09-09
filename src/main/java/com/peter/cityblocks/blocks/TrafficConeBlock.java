package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class TrafficConeBlock extends VariantPartialBlock {

    private static final VoxelShape CONE_SHAPE = VoxelShapes.combine(cube(2, 0, 2, 12, 10, 12), cube(5, 0, 5, 6, 16, 6),
            (a, b) -> a || b);
    private static final VoxelShape BARREL_SHAPE = cube(2, 0, 2, 12, 22, 12);
    private static final VoxelShape STICK_SHAPE = VoxelShapes.combine(cube(6, 0, 6, 4, 23, 4), cube(1,0,1,14,1,14), (a,b) -> a || b);

    private static final Identifier[] MODEL_VARIANTS = new Identifier[] {
            Blocks.blockId("traffic_cone"),
            Blocks.blockId("traffic_barrel"),
            Blocks.blockId("traffic_stick"),
    };

    public TrafficConeBlock(String name) {
        super(new VariantSettings().setVariants(MODEL_VARIANTS.length).nonOpaque().mapColor(MapColor.ORANGE), name, MODEL_VARIANTS);
    }
    
    @Override
    public VoxelShape getShape(int variant, Direction dir) {
        return switch (variant) {
            case 0 -> CONE_SHAPE;
            case 1 -> BARREL_SHAPE;
            case 2 -> STICK_SHAPE;
            default -> VoxelShapes.fullCube();
        };
    }

}
