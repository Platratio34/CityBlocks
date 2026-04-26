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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CardReaderBlockEntity extends BlockEntity {

    public static final String NAME = "card_reader_entity";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<CardReaderBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
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

    public void clearRemoved() {
        super.clearRemoved();
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
            this.updateRedstoneInput(dir, this.getBlockPos().relative(dir), false);
        }

    }

    private void updateRedstoneTo(Direction direction) {
        RedstoneUtil.propagateRedstoneOutput(this.getLevel(), this.getBlockPos(), direction);
        this.updateRedstoneInput(direction, this.getBlockPos().relative(direction), true);
    }

    private void updateRedstoneInput(Direction dir, BlockPos targetPos, boolean ticking) {
        boolean changed = this.redstoneState.setInput(this.mapSide(dir),
                RedstoneUtil.getRedstoneInput(this.getLevel(), targetPos, dir),
                BundledRedstone.getOutput(this.getLevel(), targetPos, dir.getOpposite()));
        if (changed && !ticking) {
            TickScheduler.schedule(this.tickToken);
        }

    }

    private ComputerSide mapSide(Direction globalSide) {
        return DirectionUtil.toLocal((Direction) this.getBlockState().getValue(CardReaderBlock.FACING), globalSide);
    }

    int getRedstoneOutput(Direction side) {
        return this.redstoneState.getExternalOutput(this.mapSide(side));
    }

    int getBundledRedstoneOutput(Direction side) {
        return this.redstoneState.getExternalBundledOutput(this.mapSide(side));
    }

}
