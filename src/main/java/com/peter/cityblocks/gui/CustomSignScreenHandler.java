package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signs.CustomSignBlockEntity;
import com.peter.cityblocks.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class CustomSignScreenHandler extends AbstractContainerMenu {

    public final CustomSignBlockEntity signEntity;

    public static final MenuType<CustomSignScreenHandler> TYPE = Registry.register(BuiltInRegistries.MENU,
            CityBlocks.identifier("custom_sign"),
            new ExtendedScreenHandlerType<CustomSignScreenHandler, BlockPosScreenPacket>(CustomSignScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public CustomSignScreenHandler(int syncId, Inventory playerInventory, BlockPosScreenPacket packet) {
        super(TYPE, syncId);
        this.signEntity = (CustomSignBlockEntity)playerInventory.player.level().getBlockEntity(packet.pos());
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
