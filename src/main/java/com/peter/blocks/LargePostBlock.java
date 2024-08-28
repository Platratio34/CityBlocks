package com.peter.blocks;

import com.peter.CityBlocks;
import com.peter.blocks.signal.PedestrianSignalBlock;
import com.peter.blocks.signal.SignalHeadBlock;
import com.peter.blocks.signs.StreetSignBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class LargePostBlock extends Block {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty SIZE = IntProperty.of("size", 1, 9);
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");

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
            VoxelShapes.union(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.NORTH),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.NORTH)),
            VoxelShapes.union(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.EAST),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.EAST)),
            VoxelShapes.union(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.SOUTH),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.SOUTH)),
            VoxelShapes.union(VariantPartialBlock.cube(6, 6, 1, 4, 4, 5, Direction.WEST),
                    VariantPartialBlock.cube(5, 5, 0, 6, 6, 1, Direction.WEST)),
            VoxelShapes.union(VariantPartialBlock.cube(6, 15, 6, 4, -5, 4),
                    VariantPartialBlock.cube(5, 15, 5, 6, 1, 6)),
            VoxelShapes.union(VariantPartialBlock.cube(6, 1, 6, 4, 5, 4),
                    VariantPartialBlock.cube(5, 0, 5, 6, 1, 6)),
    };

    public static final String NAME = "large_post";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    public static final Block BLOCK = Registry.register(Registries.BLOCK, ID,
            new LargePostBlock(Settings.create().nonOpaque()));
    public static final BlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new BlockItem(BLOCK, new Item.Settings()));

    public LargePostBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(FACING, Direction.UP).with(SIZE, 1).with(NORTH, false).with(EAST, false)
                .with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING, SIZE, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getState(super.getPlacementState(ctx).with(FACING, ctx.getSide()), ctx.getBlockPos(), ctx.getWorld());
    }

    private boolean canConnectTo(BlockState otherState, Direction direction) {
        return (otherState.isOf(SignalHeadBlock.BLOCK)
                && otherState.get(SignalHeadBlock.FACING).getOpposite() == direction)
                || (otherState.isOf(PedestrianSignalBlock.BLOCK) && otherState.get(PedestrianSignalBlock.FACING).getOpposite() == direction)
                || (otherState.isOf(BLOCK)
                        && (otherState.get(FACING) == direction || otherState.get(FACING).getOpposite() == direction))
                || (otherState.isOf(StreetSignBlock.BLOCK) && otherState.get(SignalHeadBlock.FACING).getOpposite() == direction);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getState(state, pos, world);
    }

    private BlockState getState(BlockState state, BlockPos pos, WorldAccess world) {
        if (state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) {
            BlockState above = world.getBlockState(pos.up(1));
            int cSize = state.get(SIZE);
            if (above.isOf(BLOCK)) {
                int nextSize = above.get(SIZE) + 1;
                if (nextSize > 9) {
                    nextSize = 9;
                }
                if (nextSize != cSize) {
                    state = state.with(SIZE, nextSize);
                }
            } else if (cSize != 1) {
                state = state.with(SIZE, 1);
            }
            state = state.with(NORTH, canConnectTo(world.getBlockState(pos.north(1)), Direction.NORTH));
            state = state.with(EAST, canConnectTo(world.getBlockState(pos.east(1)), Direction.EAST));
            state = state.with(SOUTH, canConnectTo(world.getBlockState(pos.south(1)), Direction.SOUTH));
            state = state.with(WEST, canConnectTo(world.getBlockState(pos.west(1)), Direction.WEST));
        } else if (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            state = state.with(EAST, canConnectTo(world.getBlockState(pos.east(1)), Direction.EAST));
            state = state.with(WEST, canConnectTo(world.getBlockState(pos.west(1)), Direction.WEST));
            state = state.with(UP, canConnectTo(world.getBlockState(pos.up(1)), Direction.UP));
            state = state.with(DOWN, canConnectTo(world.getBlockState(pos.down(1)), Direction.DOWN));
        } else if (state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) {
            state = state.with(NORTH, canConnectTo(world.getBlockState(pos.north(1)), Direction.NORTH));
            state = state.with(SOUTH, canConnectTo(world.getBlockState(pos.south(1)), Direction.SOUTH));
            state = state.with(UP, canConnectTo(world.getBlockState(pos.up(1)), Direction.UP));
            state = state.with(DOWN, canConnectTo(world.getBlockState(pos.down(1)), Direction.DOWN));
        }
        return state;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return getShape(state);
    }

    public int getSizeModel(BlockState state) {
        if (state.get(SIZE) <= 2) {
            return 0;
        } else if (state.get(SIZE) <= 5) {
            return 1;
        } else if (state.get(SIZE) <= 8) {
            return 2;
        }
        return 3;
    }

    public VoxelShape getShape(BlockState state) {
        if (state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) {
            VoxelShape shape = VERTICAL_SHAPES[getSizeModel(state)];
            if (state.get(NORTH)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[0]);
            }
            if (state.get(EAST)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[1]);
            }
            if (state.get(SOUTH)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[2]);
            }
            if (state.get(WEST)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[3]);
            }
            return shape;
        } else if (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            VoxelShape shape = HORIZONTAL_SHAPES[0];
            if (state.get(EAST)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[1]);
            }
            if (state.get(WEST)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[3]);
            }
            if (state.get(UP)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[4]);
            }
            if (state.get(DOWN)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[5]);
            }
            return shape;
        } else if (state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) {
            VoxelShape shape = HORIZONTAL_SHAPES[1];
            if (state.get(NORTH)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[0]);
            }
            if (state.get(SOUTH)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[2]);
            }
            if (state.get(UP)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[4]);
            }
            if (state.get(DOWN)) {
                shape = VoxelShapes.union(shape, ATTACHMENT_SHAPES[5]);
            }
            return shape;
        }
        return VariantPartialBlock.cube(0, 0, 0, 16, 16, 16);
    }

}
