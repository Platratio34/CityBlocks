package com.peter.cityblocks.blocks;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlock;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LargePostBlock extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 9);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public static final VoxelShape[] VERTICAL_SHAPES = new VoxelShape[] {
            VariantPartialBlock.cube(6, 0, 6, 4, 16, 4),
            VariantPartialBlock.cube(5, 0, 5, 6, 16, 6),
            VariantPartialBlock.cube(4, 0, 4, 8, 16, 8),
            VariantPartialBlock.cube(3, 0, 3, 10, 16, 10),
    };
    public static final VoxelShape[] HORIZONTAL_SHAPES = new VoxelShape[] {
            VariantPartialBlock.cube(6, 6, 0, 4, 4, 16),
            VariantPartialBlock.cube(0, 6, 6, 16, 4, 4)
    };
    public static final VoxelShape[] ATTACHMENT_SHAPES = new VoxelShape[] {
            Shapes.or(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.NORTH),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.NORTH)),
            Shapes.or(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.EAST),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.EAST)),
            Shapes.or(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.SOUTH),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.SOUTH)),
            Shapes.or(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.WEST),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.WEST)),
            Shapes.or(VariantPartialBlock.cube(6, 15, 6, 4, -5, 4),
                    VariantPartialBlock.cube(5, 15, 5, 6, 1, 6)),
            Shapes.or(VariantPartialBlock.cube(6, 1, 6, 4, 5, 4),
                    VariantPartialBlock.cube(5, 0, 5, 6, 1, 6)),
    };

    public static final String NAME = "large_post";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final Block BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new LargePostBlock(Properties.of().noOcclusion().setId(Blocks.brk(ID))));
    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID, new Item.Properties());

    public LargePostBlock(Properties settings) {
        super(settings);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP).setValue(SIZE, 1).setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING, SIZE, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getState(super.getStateForPlacement(ctx).setValue(FACING, ctx.getClickedFace()), ctx.getClickedPos(), ctx.getLevel());
    }

    private boolean canConnectTo(BlockState otherState, Direction direction) {
        return (otherState.is(SignalHeadBlock.BLOCK)
                && otherState.getValue(SignalHeadBlock.FACING).getOpposite() == direction)
                || (otherState.is(PedestrianSignalBlock.BLOCK) && otherState.getValue(PedestrianSignalBlock.FACING).getOpposite() == direction)
                || (otherState.is(BLOCK)
                        && (otherState.getValue(FACING) == direction || otherState.getValue(FACING).getOpposite() == direction))
                || (otherState.is(StreetSignBlock.BLOCK) && otherState.getValue(SignalHeadBlock.FACING).getOpposite() == direction);
    }
    
    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView,
            BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return getState(state, pos, world);
    }

    private BlockState getState(BlockState state, BlockPos pos, LevelReader world) {
        if (state.getValue(FACING) == Direction.UP || state.getValue(FACING) == Direction.DOWN) {
            BlockState above = world.getBlockState(pos.above(1));
            int cSize = state.getValue(SIZE);
            if (above.is(BLOCK)) {
                int nextSize = above.getValue(SIZE) + 1;
                if (nextSize > 9) {
                    nextSize = 9;
                }
                if (nextSize != cSize) {
                    state = state.setValue(SIZE, nextSize);
                }
            } else if (cSize != 1) {
                state = state.setValue(SIZE, 1);
            }
            state = state.setValue(NORTH, canConnectTo(world.getBlockState(pos.north(1)), Direction.NORTH));
            state = state.setValue(EAST, canConnectTo(world.getBlockState(pos.east(1)), Direction.EAST));
            state = state.setValue(SOUTH, canConnectTo(world.getBlockState(pos.south(1)), Direction.SOUTH));
            state = state.setValue(WEST, canConnectTo(world.getBlockState(pos.west(1)), Direction.WEST));
        } else if (state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH) {
            state = state.setValue(EAST, canConnectTo(world.getBlockState(pos.east(1)), Direction.EAST));
            state = state.setValue(WEST, canConnectTo(world.getBlockState(pos.west(1)), Direction.WEST));
            state = state.setValue(UP, canConnectTo(world.getBlockState(pos.above(1)), Direction.UP));
            state = state.setValue(DOWN, canConnectTo(world.getBlockState(pos.below(1)), Direction.DOWN));
        } else if (state.getValue(FACING) == Direction.EAST || state.getValue(FACING) == Direction.WEST) {
            state = state.setValue(NORTH, canConnectTo(world.getBlockState(pos.north(1)), Direction.NORTH));
            state = state.setValue(SOUTH, canConnectTo(world.getBlockState(pos.south(1)), Direction.SOUTH));
            state = state.setValue(UP, canConnectTo(world.getBlockState(pos.above(1)), Direction.UP));
            state = state.setValue(DOWN, canConnectTo(world.getBlockState(pos.below(1)), Direction.DOWN));
        }
        return state;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return getShape(state);
    }

    public int getSizeModel(BlockState state) {
        if (state.getValue(SIZE) <= 2) {
            return 0;
        } else if (state.getValue(SIZE) <= 5) {
            return 1;
        } else if (state.getValue(SIZE) <= 8) {
            return 2;
        }
        return 3;
    }

    public VoxelShape getShape(BlockState state) {
        if (state.getValue(FACING) == Direction.UP || state.getValue(FACING) == Direction.DOWN) {
            VoxelShape shape = VERTICAL_SHAPES[getSizeModel(state)];
            if (state.getValue(NORTH)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[0]);
            }
            if (state.getValue(EAST)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[1]);
            }
            if (state.getValue(SOUTH)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[2]);
            }
            if (state.getValue(WEST)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[3]);
            }
            return shape;
        } else if (state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH) {
            VoxelShape shape = HORIZONTAL_SHAPES[0];
            if (state.getValue(EAST)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[1]);
            }
            if (state.getValue(WEST)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[3]);
            }
            if (state.getValue(UP)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[4]);
            }
            if (state.getValue(DOWN)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[5]);
            }
            return shape;
        } else if (state.getValue(FACING) == Direction.EAST || state.getValue(FACING) == Direction.WEST) {
            VoxelShape shape = HORIZONTAL_SHAPES[1];
            if (state.getValue(NORTH)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[0]);
            }
            if (state.getValue(SOUTH)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[2]);
            }
            if (state.getValue(UP)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[4]);
            }
            if (state.getValue(DOWN)) {
                shape = Shapes.or(shape, ATTACHMENT_SHAPES[5]);
            }
            return shape;
        }
        return VariantPartialBlock.cube(0, 0, 0, 16, 16, 16);
    }

}
