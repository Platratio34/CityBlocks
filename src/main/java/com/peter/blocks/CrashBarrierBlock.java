package com.peter.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class CrashBarrierBlock extends VariantPartialBlock {

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
        new VoxelShape[] {
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2), cube(6, 7, 0, 2, 8, 16)),
            VoxelShapes.union(cube(8, 0, 6, 2, 16, 2), cube(6, 7, 0, 2, 8, 10), cube(8, 7, 8, 8, 8, 2)),
            VoxelShapes.union(cube(8, 0, 5, 2, 16, 2), cube(5, 0, 8, 2, 16, 2), cube(6, 7, 0, 2, 8, 6), cube(0, 7, 6, 8, 8, 2)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2), cube(6, 7, 0, 2, 8, 16), cube(5, 7, 14, 6, 8, 2)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2), cube(6, 7, 0, 2, 8, 16), cube(5, 7, 0, 6, 8, 2)),
            cube(6, 7, 0, 2, 8, 16)
        }, new VoxelShape[] {
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.EAST), cube(6, 7, 0, 2, 8, 16, Direction.EAST)),
            VoxelShapes.union(cube(8, 0, 6, 2, 16, 2, Direction.EAST), cube(6, 7, 0, 2, 8, 10, Direction.EAST), cube(8, 7, 8, 8, 8, 2, Direction.EAST)),
            VoxelShapes.union(cube(8, 0, 5, 2, 16, 2, Direction.EAST), cube(5, 0, 8, 2, 16, 2, Direction.EAST), cube(6, 7, 0, 2, 8, 6, Direction.EAST), cube(0, 7, 6, 8, 8, 2, Direction.EAST)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.EAST), cube(6, 7, 0, 2, 8, 16, Direction.EAST), cube(5, 7, 14, 6, 8, 2, Direction.EAST)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.EAST), cube(6, 7, 0, 2, 8, 16, Direction.EAST), cube(5, 7, 0, 6, 8, 2, Direction.EAST)),
            cube(6, 7, 0, 2, 8, 16, Direction.EAST)
        }, new VoxelShape[] {
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.SOUTH), cube(6, 7, 0, 2, 8, 16, Direction.SOUTH)),
            VoxelShapes.union(cube(8, 0, 6, 2, 16, 2, Direction.SOUTH), cube(6, 7, 0, 2, 8, 10, Direction.SOUTH), cube(8, 7, 8, 8, 8, 2, Direction.SOUTH)),
            VoxelShapes.union(cube(8, 0, 5, 2, 16, 2, Direction.SOUTH), cube(5, 0, 8, 2, 16, 2, Direction.SOUTH), cube(6, 7, 0, 2, 8, 6, Direction.SOUTH), cube(0, 7, 6, 8, 8, 2, Direction.SOUTH)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.SOUTH), cube(6, 7, 0, 2, 8, 16, Direction.SOUTH), cube(5, 7, 14, 6, 8, 2, Direction.SOUTH)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.SOUTH), cube(6, 7, 0, 2, 8, 16, Direction.SOUTH), cube(5, 7, 0, 6, 8, 2, Direction.SOUTH)),
            cube(6, 7, 0, 2, 8, 16, Direction.SOUTH)
        }, new VoxelShape[] {
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.WEST), cube(6, 7, 0, 2, 8, 16, Direction.WEST)),
            VoxelShapes.union(cube(8, 0, 6, 2, 16, 2, Direction.WEST), cube(6, 7, 0, 2, 8, 10, Direction.WEST), cube(8, 7, 8, 8, 8, 2, Direction.WEST)),
            VoxelShapes.union(cube(8, 0, 5, 2, 16, 2, Direction.WEST), cube(5, 0, 8, 2, 16, 2, Direction.WEST), cube(6, 7, 0, 2, 8, 6, Direction.WEST), cube(0, 7, 6, 8, 8, 2, Direction.WEST)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.WEST), cube(6, 7, 0, 2, 8, 16, Direction.WEST), cube(5, 7, 14, 6, 8, 2, Direction.WEST)),
            VoxelShapes.union(cube(8, 0, 7, 2, 16, 2, Direction.WEST), cube(6, 7, 0, 2, 8, 16, Direction.WEST), cube(5, 7, 0, 6, 8, 2, Direction.WEST)),
            cube(6, 7, 0, 2, 8, 16, Direction.WEST)
        }
    };

    public CrashBarrierBlock(String name) {
        super(new VariantSettings().setVariants(6).nonOpaque().mapColor(MapColor.WHITE), name, SHAPES);
    }

}
