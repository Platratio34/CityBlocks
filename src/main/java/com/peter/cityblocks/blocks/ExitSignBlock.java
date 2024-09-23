package com.peter.cityblocks.blocks;

import com.peter.cityblocks.CityBlocks;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class ExitSignBlock extends VariantPartialBlock {

    public static final String NAME = "exit_sign";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                    cube(3, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(3, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(3, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.NORTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.NORTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.NORTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.NORTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.NORTH)
            },
            new VoxelShape[] {
                    cube(3, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(3, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(3, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 1, 0, 10, 6, 2, Direction.EAST),
                    cube(3, 10, 0, 10, 6, 2, Direction.EAST),
                    cube(3, 10, 0, 10, 6, 2, Direction.EAST),
                    cube(3, 10, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 10, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 10, 0, 10, 6, 2, Direction.EAST),
                    cube(11, 10, 0, 10, 6, 2, Direction.EAST)
            },
            new VoxelShape[] {
                    cube(3, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(3, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(3, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 1, 0, 10, 6, 2, Direction.SOUTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.SOUTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.SOUTH),
                    cube(3, 10, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.SOUTH),
                    cube(11, 10, 0, 10, 6, 2, Direction.SOUTH)
            },
            new VoxelShape[] {
                    cube(3, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(3, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(3, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 1, 0, 10, 6, 2, Direction.WEST),
                    cube(3, 10, 0, 10, 6, 2, Direction.WEST),
                    cube(3, 10, 0, 10, 6, 2, Direction.WEST),
                    cube(3, 10, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 10, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 10, 0, 10, 6, 2, Direction.WEST),
                    cube(11, 10, 0, 10, 6, 2, Direction.WEST)
            }
    };

    public static final VariantPartialBlock BLOCK = new ExitSignBlock(
            new VariantSettings().setVariants(12).nonOpaque().luminance((state) -> 1));
    public static final BlockItem ITEM = BLOCK.item;

    public ExitSignBlock(Settings settings) {
        super(settings, NAME, SHAPES);
    }

}
