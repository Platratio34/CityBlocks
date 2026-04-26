package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import com.peter.cityblocks.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class SignalControllerScreenHandler extends AbstractContainerMenu {

    public final SignalControllerBlockEntity controller;

    public static final MenuType<SignalControllerScreenHandler> TYPE = Registry.register(BuiltInRegistries.MENU,
            CityBlocks.identifier("signal_controller"),
            new ExtendedScreenHandlerType<SignalControllerScreenHandler, BlockPosScreenPacket>(SignalControllerScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public SignalControllerScreenHandler(int syncId, Inventory playerInventory, BlockPosScreenPacket packet) {
        super(TYPE, syncId);
        controller = (SignalControllerBlockEntity)playerInventory.player.level().getBlockEntity(packet.pos());
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
