package com.peter.blocks.signal;

import com.peter.CityBlocks;
import com.peter.networking.SignalControllerUpdatePayload;
import com.peter.networking.SignalHeadUpdatePayload;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SignalNetworking {

    public static void registerServer() {
        CityBlocks.LOGGER.debug("Registering Signal packet receivers");
        PayloadTypeRegistry.playC2S().register(SignalHeadUpdatePayload.ID, SignalHeadUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SignalHeadUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                SignalHeadBlockEntity entity = (SignalHeadBlockEntity) context.server().getWorld(payload.world())
                        .getBlockEntity(payload.pos());
                payload.update(entity);
            });
        });
        PayloadTypeRegistry.playC2S().register(SignalControllerUpdatePayload.ID, SignalControllerUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SignalControllerUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                SignalControllerBlockEntity entity = (SignalControllerBlockEntity) context.server().getWorld(payload.world())
                        .getBlockEntity(payload.pos());
                entity.setCycleMode(payload.cycleMode());
            });
        });
    }
}
