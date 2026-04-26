package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signs.CustomSignBlockEntity;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record CustomSignUpdatePayload(ResourceKey<Level> world, BlockPos pos, int variant, boolean updateText, String text) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CustomSignUpdatePayload> ID = new CustomPacketPayload.Type<>(
            CityBlocks.identifier("custom_sign_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CustomSignUpdatePayload> CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), CustomSignUpdatePayload::world,
            BlockPos.STREAM_CODEC, CustomSignUpdatePayload::pos,
            ByteBufCodecs.INT, CustomSignUpdatePayload::variant,
            ByteBufCodecs.BOOL, CustomSignUpdatePayload::updateText,
            ByteBufCodecs.STRING_UTF8, CustomSignUpdatePayload::text,
            CustomSignUpdatePayload::new
    );

    @Override
    public Type<CustomSignUpdatePayload> type() {
        return ID;
    }
    
    public static void registerServer() {
        CityBlocks.LOGGER.info("Registering Custom Sign Packet receiver");
        PayloadTypeRegistry.playC2S().register(CustomSignUpdatePayload.ID, CustomSignUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CustomSignUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                CustomSignBlockEntity entity = (CustomSignBlockEntity) context.server().getLevel(payload.world())
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
