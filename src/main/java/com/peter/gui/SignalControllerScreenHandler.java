package com.peter.gui;

import com.peter.CityBlocks;
import com.peter.blocks.signal.SignalControllerBlockEntity;
import com.peter.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class SignalControllerScreenHandler extends ScreenHandler {

    public final SignalControllerBlockEntity controller;

    public static final ScreenHandlerType<SignalControllerScreenHandler> TYPE = Registry.register(Registries.SCREEN_HANDLER,
            CityBlocks.identifier("signal_controller"),
            new ExtendedScreenHandlerType<SignalControllerScreenHandler, BlockPosScreenPacket>(SignalControllerScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public SignalControllerScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosScreenPacket packet) {
        super(TYPE, syncId);
        controller = (SignalControllerBlockEntity)playerInventory.player.getWorld().getBlockEntity(packet.pos());
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

}
