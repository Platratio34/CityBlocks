package com.peter.cityblocks.blocks.signal;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.items.SignalLinker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
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
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalControllerBlock extends BlockWithEntity {

    public static final String NAME = "signal_controller";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final MapCodec<SignalControllerBlock> CODEC = createCodec(SignalControllerBlock::new);

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    
    public static final Block BLOCK = Registry.register(Registries.BLOCK, ID,
            new SignalControllerBlock(Settings.create().nonOpaque()));
    public static final BlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new BlockItem(BLOCK, new Item.Settings()));

    public static final Identifier BLOCK_ENTITY_ID = SignalControllerBlockEntity.ID;

    protected SignalControllerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING,
                ctx.getHorizontalPlayerFacing());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SignalControllerBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<SignalControllerBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && stack.isOf(SignalLinker.ITEM)) {
            if (SignalLinker.isLinked(stack, pos)) {
                SignalLinker.unLinkController(stack);
                player.sendMessage(CityBlocks.translatableText("chat", "signal_controller.un_linked"));
            } else {
                SignalLinker.linkController(stack, pos);
                player.sendMessage(CityBlocks.translatableText("chat", "signal_controller.linked"));
            }
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return validateTicker(type, SignalControllerBlockEntity.BLOCK_ENTITY_TYPE,
                world.isClient ? SignalControllerBlockEntity::clientTick : SignalControllerBlockEntity::serverTick);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (SignalControllerBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

}
