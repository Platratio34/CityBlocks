package com.peter.cityblocks.blocks.elevator;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ElevatorControllerBlock extends BlockWithEntity {

    public static final String NAME = "elevator_controller";
    public static final Identifier ID = CityBlocks.identifier(NAME);public static final MapCodec<ElevatorControllerBlock> CODEC = createCodec(ElevatorControllerBlock::new);

    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public static final ElevatorControllerBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new ElevatorControllerBlock(Settings.create().nonOpaque().registryKey(Blocks.brk(ID))));
    public static final BlockItem ITEM = Blocks.registerBlockItem(BLOCK, ID, new Item.Settings());

    public static final Identifier BLOCK_ENTITY_ID = ElevatorControllerBlockEntity.ID;

    public ElevatorControllerBlock(Settings settings) {
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
        return new ElevatorControllerBlockEntity(pos, state);
    }

}
