package com.peter.cityblocks.blocks.signal;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.VariantBlock;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.items.SignalLinker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SignalHeadBlock extends BlockWithEntity {

    public static final String NAME = "signal_head";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    private static final VoxelShape[] SHAPES = new VoxelShape[] {
            VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.NORTH),
            VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.EAST),
            VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.SOUTH),
            VariantPartialBlock.cube(3, -2, 0, 10, 20, 5, Direction.WEST)
    };
    public static final MapCodec<SignalHeadBlock> CODEC = createCodec(SignalHeadBlock::new);

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final int MAX_LAMPS = 3;
    public static final IntProperty LAMP_COUNT = IntProperty.of("lamps", 1, MAX_LAMPS);

    public static final Block BLOCK = Registry.register(Registries.BLOCK, ID,
            new SignalHeadBlock(Settings.create().nonOpaque()));
    public static final BlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new BlockItem(BLOCK, new Item.Settings()));

    public static final Identifier BLOCK_ENTITY_ID = SignalHeadBlockEntity.ID;

    public SignalHeadBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(LAMP_COUNT, 3));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[] { FACING, LAMP_COUNT });
    }

    @Override
    protected MapCodec<SignalHeadBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing())
                .with(LAMP_COUNT, 3);
    }

    public VoxelShape getShape(BlockState state) {
        return SHAPES[VariantBlock.getDir(state)];
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

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SignalHeadBlockEntity(pos, state);
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return validateTicker(type, SignalHeadBlockEntity.BLOCK_ENTITY_TYPE,
                world.isClient ? SignalHeadBlockEntity::clientTick : SignalHeadBlockEntity::serverTick);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (SignalHeadBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
        // return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && stack.isOf(SignalLinker.ITEM)) {
            SignalControllerBlockEntity controller = SignalLinker.getLinkedController(world, stack);
            if (controller != null) {
                if (controller.link(pos))
                    player.sendMessage(CityBlocks.translatableText("chat", "signal_head.linked"));
                else
                player.sendMessage(CityBlocks.translatableText("chat", "signal_head.un_linked"));
            }
            return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

}
