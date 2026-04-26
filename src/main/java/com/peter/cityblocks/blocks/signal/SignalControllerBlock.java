package com.peter.cityblocks.blocks.signal;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SignalControllerBlock extends BaseEntityBlock {

    public static final String NAME = "signal_controller";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    public static final MapCodec<SignalControllerBlock> CODEC = simpleCodec(SignalControllerBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    public static final Block BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new SignalControllerBlock(Properties.of().noOcclusion().setId(Blocks.brk(ID))));
    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID, new Item.Properties());

    public static final ResourceLocation BLOCK_ENTITY_ID = SignalControllerBlockEntity.ID;

    protected SignalControllerBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING,
                ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignalControllerBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<SignalControllerBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && stack.is(SignalLinker.ITEM)) {
            if (SignalLinker.isLinked(stack, pos)) {
                SignalLinker.unLinkController(stack);
                player.displayClientMessage(CityBlocks.translatableText("chat", "signal_controller.un_linked"), false);
            } else {
                SignalLinker.linkController(stack, pos);
                player.displayClientMessage(CityBlocks.translatableText("chat", "signal_controller.linked"), false);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
            BlockEntityType<T> type) {
        return createTickerHelper(type, SignalControllerBlockEntity.BLOCK_ENTITY_TYPE,
                world.isClientSide ? SignalControllerBlockEntity::clientTick : SignalControllerBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = (SignalControllerBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }
        return InteractionResult.SUCCESS;
    }

}
