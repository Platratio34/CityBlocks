package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SignalHeadScreenHandler extends AbstractContainerMenu {

    public static final MenuType<SignalHeadScreenHandler> TYPE = Registry.register(BuiltInRegistries.MENU,
            CityBlocks.identifier("signal_head"),
            new ExtendedScreenHandlerType<SignalHeadScreenHandler, BlockPosScreenPacket>(SignalHeadScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public final SignalHeadBlockEntity headEntity;
    public final PedestrianSignalBlockEntity pedestrianEntity;

    public SignalHeadScreenHandler(int syncId, Inventory playerInventory, BlockPosScreenPacket data) {
        this(syncId, playerInventory.player.level().getBlockEntity(data.pos()));
    }

    public SignalHeadScreenHandler(int syncId, BlockEntity blockEntity) {
        super(TYPE, syncId);
        if(blockEntity instanceof SignalHeadBlockEntity)
            headEntity = (SignalHeadBlockEntity) blockEntity;
        else
            headEntity = null;
        if(blockEntity instanceof PedestrianSignalBlockEntity)
            pedestrianEntity = (PedestrianSignalBlockEntity) blockEntity;
        else
            pedestrianEntity = null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

}
