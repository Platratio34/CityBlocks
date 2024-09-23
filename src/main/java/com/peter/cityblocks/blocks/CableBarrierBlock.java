package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class CableBarrierBlock extends VariantPartialBlock {

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                    VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.NORTH),
                            cube(0, 3, 7.5, 16, 1, 1, Direction.NORTH), cube(0, 8, 7.5, 16, 1, 1, Direction.NORTH),
                            cube(0, 13, 7.5, 16, 1, 1, Direction.NORTH)),
                    VoxelShapes.union(cube(0, 3, 7.5, 16, 1, 1, Direction.NORTH),
                            cube(0, 8, 7.5, 16, 1, 1, Direction.NORTH), cube(0, 13, 7.5, 16, 1, 1, Direction.NORTH)),
                    VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.NORTH),
                            cube(0, 3, 7.5, 8, 1, 1, Direction.NORTH), cube(0, 8, 7.5, 8, 1, 1, Direction.NORTH),
                            cube(0, 13, 7.5, 8, 1, 1, Direction.NORTH)),
                    VoxelShapes.union(cube(0, 0, 7.5, 4, 13, 1, Direction.NORTH),
                            cube(4, 0, 7.5, 4, 10, 1, Direction.NORTH), cube(8, 0, 7.5, 4, 6, 1, Direction.NORTH))
            }, new VoxelShape[] {
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.EAST),
                        cube(0, 3, 7.5, 16, 1, 1, Direction.EAST), cube(0, 8, 7.5, 16, 1, 1, Direction.EAST),
                        cube(0, 13, 7.5, 16, 1, 1, Direction.EAST)),
                VoxelShapes.union(cube(0, 3, 7.5, 16, 1, 1, Direction.EAST),
                        cube(0, 8, 7.5, 16, 1, 1, Direction.EAST), cube(0, 13, 7.5, 16, 1, 1, Direction.EAST)),
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.EAST),
                        cube(0, 3, 7.5, 8, 1, 1, Direction.EAST), cube(0, 8, 7.5, 8, 1, 1, Direction.EAST),
                        cube(0, 13, 7.5, 8, 1, 1, Direction.EAST)),
                VoxelShapes.union(cube(0, 0, 7.5, 4, 13, 1, Direction.EAST),
                        cube(4, 0, 7.5, 4, 10, 1, Direction.EAST), cube(8, 0, 7.5, 4, 6, 1, Direction.EAST))
            }, new VoxelShape[] {
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.SOUTH),
                        cube(0, 3, 7.5, 16, 1, 1, Direction.SOUTH), cube(0, 8, 7.5, 16, 1, 1, Direction.SOUTH),
                        cube(0, 13, 7.5, 16, 1, 1, Direction.SOUTH)),
                VoxelShapes.union(cube(0, 3, 7.5, 16, 1, 1, Direction.SOUTH),
                        cube(0, 8, 7.5, 16, 1, 1, Direction.SOUTH), cube(0, 13, 7.5, 16, 1, 1, Direction.SOUTH)),
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.SOUTH),
                        cube(0, 3, 7.5, 8, 1, 1, Direction.SOUTH), cube(0, 8, 7.5, 8, 1, 1, Direction.SOUTH),
                        cube(0, 13, 7.5, 8, 1, 1, Direction.SOUTH)),
                VoxelShapes.union(cube(0, 0, 7.5, 4, 13, 1, Direction.SOUTH),
                        cube(4, 0, 7.5, 4, 10, 1, Direction.SOUTH), cube(8, 0, 7.5, 4, 6, 1, Direction.SOUTH))
            }, new VoxelShape[] {
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.WEST),
                        cube(0, 3, 7.5, 16, 1, 1, Direction.WEST), cube(0, 8, 7.5, 16, 1, 1, Direction.WEST),
                        cube(0, 13, 7.5, 16, 1, 1, Direction.WEST)),
                VoxelShapes.union(cube(0, 3, 7.5, 16, 1, 1, Direction.WEST),
                        cube(0, 8, 7.5, 16, 1, 1, Direction.WEST), cube(0, 13, 7.5, 16, 1, 1, Direction.WEST)),
                VoxelShapes.union(cube(7, 0, 7, 2, 16, 2, Direction.WEST),
                        cube(0, 3, 7.5, 8, 1, 1, Direction.WEST), cube(0, 8, 7.5, 8, 1, 1, Direction.WEST),
                        cube(0, 13, 7.5, 8, 1, 1, Direction.WEST)),
                VoxelShapes.union(cube(0, 0, 7.5, 4, 13, 1, Direction.WEST),
                        cube(4, 0, 7.5, 4, 10, 1, Direction.WEST), cube(8, 0, 7.5, 4, 6, 1, Direction.WEST))
            }
    };

    public CableBarrierBlock(String name) {
        super(new VariantSettings().setVariants(4).nonOpaque().mapColor(MapColor.GRAY), name, SHAPES);
    }

}
