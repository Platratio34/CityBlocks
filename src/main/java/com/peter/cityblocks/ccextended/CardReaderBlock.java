package com.peter.cityblocks.ccextended;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;

public class CardReaderBlock extends BlockWithEntity {

    public static final String NAME = "card_reader";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final MapCodec<CardReaderBlock> CODEC = createCodec(CardReaderBlock::new);

    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public static final CardReaderBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new CardReaderBlock(Settings.create().nonOpaque().registryKey(Blocks.brk(ID))));
    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID, new Item.Settings());

    public static final Identifier BLOCK_ENTITY_ID = CardReaderBlockEntity.ID;

    protected CardReaderBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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
        return new CardReaderBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            CardReaderBlockEntity reader = (CardReaderBlockEntity) world.getBlockEntity(pos);
            reader.getPeripheral().onUse(stack);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock,
            WireOrientation wireOrientation, boolean notify) {
        ((CardReaderBlockEntity) world.getBlockEntity(pos)).neighborChanged();
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ((CardReaderBlockEntity) world.getBlockEntity(pos)).update();
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        BlockEntity var6 = world.getBlockEntity(pos);
        int output;
        if (var6 instanceof CardReaderBlockEntity reader) {
            output = reader.getRedstoneOutput(direction.getOpposite());
        } else {
            output = 0;
        }

        return output;
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getStrongRedstonePower(state, world, pos, direction);
    }
}
