package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VariantSwitcherScreenHandler extends ScreenHandler {

    public static final ScreenHandlerType<VariantSwitcherScreenHandler> TYPE = Registry.register(Registries.SCREEN_HANDLER,
            CityBlocks.identifier("variant_switcher"),
            new ExtendedScreenHandlerType<VariantSwitcherScreenHandler, VariantSwitcherScreenPacket>(VariantSwitcherScreenHandler::new, VariantSwitcherScreenPacket.PACKET_CODEC));

    public final RegistryKey<World> world;
    public final BlockPos blockPos;
    public final int cState;

    public VariantSwitcherScreenHandler(int syncId, PlayerInventory inventory, VariantSwitcherScreenPacket packet) {
        super(TYPE, syncId);
        world = packet.world();
        blockPos = packet.pos();
        cState = packet.currentState();
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
