package com.peter.cityblocks.blocks;

import com.peter.cityblocks.CityBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExitSignBlock extends VariantPartialBlock {

    public static final String NAME = "exit_sign";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

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
            new VariantSettings().setVariants(12).noOcclusion().lightLevel((state) -> 1));
    public static final BlockItem ITEM = BLOCK.item;

    private static final ResourceLocation[] MODEL_VARIANTS = new ResourceLocation[] {
            Blocks.blockId("exit_sign"),
            Blocks.blockId("exit_sign_left"),
            Blocks.blockId("exit_sign_right"),
            Blocks.blockId("exit_sign_offset"),
            Blocks.blockId("exit_sign_offset_left"),
            Blocks.blockId("exit_sign_offset_right"),
            Blocks.blockId("exit_sign_top"),
            Blocks.blockId("exit_sign_top_left"),
            Blocks.blockId("exit_sign_top_right"),
            Blocks.blockId("exit_sign_top_offset"),
            Blocks.blockId("exit_sign_top_offset_left"),
            Blocks.blockId("exit_sign_top_offset_right"),
    };

    public ExitSignBlock(Properties settings) {
        super(settings, NAME, MODEL_VARIANTS);
    }

    @Override
    public VoxelShape getShape(int variant, Direction direction) {
        return switch (variant) {
            case 0 -> cube(3, 1, 0, 10, 6, 2, direction);
            case 1 -> cube(3, 1, 0, 10, 6, 2, direction);
            case 2 -> cube(3, 1, 0, 10, 6, 2, direction);
            case 3 -> cube(11, 1, 0, 10, 6, 2, direction);
            case 4 -> cube(11, 1, 0, 10, 6, 2, direction);
            case 5 -> cube(11, 1, 0, 10, 6, 2, direction);
            case 6 -> cube(3, 10, 0, 10, 6, 2, direction);
            case 7 -> cube(3, 10, 0, 10, 6, 2, direction);
            case 8 -> cube(3, 10, 0, 10, 6, 2, direction);
            case 9 -> cube(11, 10, 0, 10, 6, 2, direction);
            case 10 -> cube(11, 10, 0, 10, 6, 2, direction);
            case 11 -> cube(11, 10, 0, 10, 6, 2, direction);
        
            default -> Shapes.block();
        };
    }

}
