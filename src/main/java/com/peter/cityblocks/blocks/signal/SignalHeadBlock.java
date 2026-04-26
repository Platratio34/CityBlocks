package com.peter.cityblocks.blocks.signal;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.VariantBlock;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.items.SignalLinker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SignalHeadBlock extends BaseEntityBlock {

    public static final String NAME = "signal_head";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    private static final VoxelShape[][] SHAPES = new VoxelShape[][] {
            new VoxelShape[] {
                    VariantPartialBlock.cube(3, 3, 0, 10, 10, 5, Direction.NORTH),
                    VariantPartialBlock.cube(3, 3, 0, 10, 10, 5, Direction.EAST),
                    VariantPartialBlock.cube(3, 3, 0, 10, 10, 5, Direction.SOUTH),
                    VariantPartialBlock.cube(3, 3, 0, 10, 10, 5, Direction.WEST)
            },
            new VoxelShape[] {
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.NORTH),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.EAST),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.SOUTH),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.WEST)
            },
            new VoxelShape[] {
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.NORTH),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.EAST),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.SOUTH),
                    VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.WEST)
            }
    };
    public static final MapCodec<SignalHeadBlock> CODEC = simpleCodec(SignalHeadBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final int MAX_LAMPS = 3;
    public static final IntegerProperty LAMP_COUNT = IntegerProperty.create("lamps", 1, MAX_LAMPS);

    public static final Block BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new SignalHeadBlock(Properties.of().noOcclusion().setId(Blocks.brk(ID))));
    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID, new Item.Properties());

    public static final ResourceLocation BLOCK_ENTITY_ID = SignalHeadBlockEntity.ID;

    public SignalHeadBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LAMP_COUNT, 3));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[] { FACING, LAMP_COUNT });
    }

    @Override
    protected MapCodec<SignalHeadBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection())
                .setValue(LAMP_COUNT, 3);
    }

    public VoxelShape getShape(BlockState state) {
        return SHAPES[state.getValue(LAMP_COUNT)-1][VariantBlock.getDir(state)];
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

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignalHeadBlockEntity(pos, state);
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState) state.setValue(FACING, rotation.rotate((Direction) state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.getValue(FACING)));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
            BlockEntityType<T> type) {
        return createTickerHelper(type, SignalHeadBlockEntity.BLOCK_ENTITY_TYPE,
                world.isClientSide ? SignalHeadBlockEntity::clientTick : SignalHeadBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = (SignalHeadBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }
        return InteractionResult.SUCCESS;
        // return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && stack.is(SignalLinker.ITEM)) {
            SignalControllerBlockEntity controller = SignalLinker.getLinkedController(world, stack);
            if (controller != null) {
                if (controller.link(pos))
                    player.displayClientMessage(CityBlocks.translatableText("chat", "signal_head.linked"), false);
                else
                player.displayClientMessage(CityBlocks.translatableText("chat", "signal_head.un_linked"), false);
            }
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

}
