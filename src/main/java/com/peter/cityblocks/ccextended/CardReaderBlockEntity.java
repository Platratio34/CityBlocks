package com.peter.cityblocks.ccextended;

import com.peter.cityblocks.CityBlocks;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class CardReaderBlockEntity extends BlockEntity {

    public static final String NAME = "card_reader_entity";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<CardReaderBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(CardReaderBlockEntity::new, CardReaderBlock.BLOCK).build());

    public static void register() {
        PeripheralLookup.get().registerForBlockEntity(CardReaderBlockEntity::getPeripheral, BLOCK_ENTITY_TYPE);
    }

    private final CardReaderPeripheral peripheral = new CardReaderPeripheral(this);

    public CardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
    }

    public CardReaderPeripheral getPeripheral() {
        return peripheral;
    }

    public CardReaderPeripheral getPeripheral(Direction direction) {
        return getPeripheral();
    }

}
