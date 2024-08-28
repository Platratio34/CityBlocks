package com.peter.gui;

import com.peter.CityBlocks;
import com.peter.blocks.signs.CustomSignBlockEntity;
import com.peter.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class CustomSignScreenHandler extends ScreenHandler {

    public final CustomSignBlockEntity signEntity;

    public static final ScreenHandlerType<CustomSignScreenHandler> TYPE = Registry.register(Registries.SCREEN_HANDLER,
            CityBlocks.identifier("custom_sign"),
            new ExtendedScreenHandlerType<CustomSignScreenHandler, BlockPosScreenPacket>(CustomSignScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public CustomSignScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosScreenPacket packet) {
        super(TYPE, syncId);
        this.signEntity = (CustomSignBlockEntity)playerInventory.player.getWorld().getBlockEntity(packet.pos());
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
