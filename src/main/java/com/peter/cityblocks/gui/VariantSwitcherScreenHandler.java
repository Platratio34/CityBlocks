package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VariantSwitcherScreenHandler extends AbstractContainerMenu {

    public static final MenuType<VariantSwitcherScreenHandler> TYPE = Registry.register(BuiltInRegistries.MENU,
            CityBlocks.identifier("variant_switcher"),
            new ExtendedScreenHandlerType<VariantSwitcherScreenHandler, VariantSwitcherScreenPacket>(VariantSwitcherScreenHandler::new, VariantSwitcherScreenPacket.PACKET_CODEC));

    public final ResourceKey<Level> world;
    public final BlockPos blockPos;
    public final int cState;

    public VariantSwitcherScreenHandler(int syncId, Inventory inventory, VariantSwitcherScreenPacket packet) {
        super(TYPE, syncId);
        world = packet.world();
        blockPos = packet.pos();
        cState = packet.currentState();
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
