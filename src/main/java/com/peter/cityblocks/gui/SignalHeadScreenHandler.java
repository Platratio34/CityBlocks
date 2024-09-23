package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.networking.BlockPosScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class SignalHeadScreenHandler extends ScreenHandler {

    public static final ScreenHandlerType<SignalHeadScreenHandler> TYPE = Registry.register(Registries.SCREEN_HANDLER,
            CityBlocks.identifier("signal_head"),
            new ExtendedScreenHandlerType<SignalHeadScreenHandler, BlockPosScreenPacket>(SignalHeadScreenHandler::new, BlockPosScreenPacket.PACKET_CODEC));

    public final SignalHeadBlockEntity headEntity;
    public final PedestrianSignalBlockEntity pedestrianEntity;

    public SignalHeadScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosScreenPacket data) {
        this(syncId, playerInventory.player.getWorld().getBlockEntity(data.pos()));
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
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

}
