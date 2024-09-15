package com.peter.networking;

import com.peter.CityBlocks;
import com.peter.blocks.signs.CustomSignBlockEntity;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record CustomSignUpdatePayload(RegistryKey<World> world, BlockPos pos, int variant, boolean updateText, String text) implements CustomPayload {

    public static final CustomPayload.Id<CustomSignUpdatePayload> ID = new CustomPayload.Id<>(
            CityBlocks.identifier("custom_sign_update"));
    public static final PacketCodec<RegistryByteBuf, CustomSignUpdatePayload> CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), CustomSignUpdatePayload::world,
            BlockPos.PACKET_CODEC, CustomSignUpdatePayload::pos,
            PacketCodecs.INTEGER, CustomSignUpdatePayload::variant,
            PacketCodecs.BOOL, CustomSignUpdatePayload::updateText,
            PacketCodecs.STRING, CustomSignUpdatePayload::text,
            CustomSignUpdatePayload::new
    );

    @Override
    public Id<CustomSignUpdatePayload> getId() {
        return ID;
    }
    
    public static void registerServer() {
        CityBlocks.LOGGER.debug("Registering Custom Sign Packet receiver");
        PayloadTypeRegistry.playC2S().register(CustomSignUpdatePayload.ID, CustomSignUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CustomSignUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                CustomSignBlockEntity entity = (CustomSignBlockEntity) context.server().getWorld(payload.world())
                        .getBlockEntity(payload.pos());
                if (payload.variant() > -1) {
                    entity.setVariant(payload.variant());
                }
                if (payload.updateText()) {
                    String[] lines = payload.text().split("\n");
                    entity.setText(lines);
                }
            });
        });
    }

}
