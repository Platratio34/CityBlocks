package com.peter.cityblocks.ccextended;

import com.peter.cityblocks.CityBlocks;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import dan200.computercraft.core.computer.ComputerSide;
import dan200.computercraft.core.redstone.RedstoneState;
import dan200.computercraft.impl.BundledRedstone;
import dan200.computercraft.shared.util.DirectionUtil;
import dan200.computercraft.shared.util.RedstoneUtil;
import dan200.computercraft.shared.util.TickScheduler;
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
        PeripheralLookup.get().registerForBlockEntity((entity, direction) -> entity.getPeripheral(), BLOCK_ENTITY_TYPE);
    }

    private final TickScheduler.Token tickToken = new TickScheduler.Token(this);
    protected final RedstoneState redstoneState = new RedstoneState(() -> {
        TickScheduler.schedule(tickToken);
    });
    protected final CardReaderPeripheral peripheral = new CardReaderPeripheral(this, redstoneState);

    public CardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
    }

    public CardReaderPeripheral getPeripheral() {
        return peripheral;
    }

    public CardReaderPeripheral getPeripheral(Direction direction) {
        return getPeripheral();
    }

    public void cancelRemoval() {
        super.cancelRemoval();
        TickScheduler.schedule(this.tickToken);
    }

    void update() {
        int changes = this.redstoneState.updateOutput();
        if (changes != 0) {
            for (Direction direction : DirectionUtil.FACINGS) {
                if ((changes & 1 << this.mapSide(direction).ordinal()) != 0) {
                    this.updateRedstoneTo(direction);
                }
            }
        }

        if (this.redstoneState.pollInputChanged()) {
            // this.peripheral.queueRedstoneEvent();
        }

    }

    void neighborChanged() {
        Direction[] var1 = DirectionUtil.FACINGS;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Direction dir = var1[var3];
            this.updateRedstoneInput(dir, this.getPos().offset(dir), false);
        }

    }

    private void updateRedstoneTo(Direction direction) {
        RedstoneUtil.propagateRedstoneOutput(this.getWorld(), this.getPos(), direction);
        this.updateRedstoneInput(direction, this.getPos().offset(direction), true);
    }

    private void updateRedstoneInput(Direction dir, BlockPos targetPos, boolean ticking) {
        boolean changed = this.redstoneState.setInput(this.mapSide(dir),
                RedstoneUtil.getRedstoneInput(this.getWorld(), targetPos, dir),
                BundledRedstone.getOutput(this.getWorld(), targetPos, dir.getOpposite()));
        if (changed && !ticking) {
            TickScheduler.schedule(this.tickToken);
        }

    }

    private ComputerSide mapSide(Direction globalSide) {
        return DirectionUtil.toLocal((Direction) this.getCachedState().get(CardReaderBlock.FACING), globalSide);
    }

    int getRedstoneOutput(Direction side) {
        return this.redstoneState.getExternalOutput(this.mapSide(side));
    }

    int getBundledRedstoneOutput(Direction side) {
        return this.redstoneState.getExternalBundledOutput(this.mapSide(side));
    }

}
