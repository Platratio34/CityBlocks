package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.VariantBlock;

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
import net.minecraft.world.level.block.state.BlockState;

public record VariantSwitcherScreenPacket(ResourceKey<Level> world, BlockPos pos, int currentState) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<VariantSwitcherScreenPacket> ID = new CustomPacketPayload.Type<>(CityBlocks.identifier("variant_switcher_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, VariantSwitcherScreenPacket> PACKET_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), VariantSwitcherScreenPacket::world,
            BlockPos.STREAM_CODEC, VariantSwitcherScreenPacket::pos,
            ByteBufCodecs.INT, VariantSwitcherScreenPacket::currentState,
        VariantSwitcherScreenPacket::new
    );

    @Override
    public Type<VariantSwitcherScreenPacket> type() {
        return ID;
    }

    public static void registerServer() {
        CityBlocks.LOGGER.info("Registering variant switcher screen packet receiver");

        PayloadTypeRegistry.playC2S().register(VariantSwitcherScreenPacket.ID, VariantSwitcherScreenPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(VariantSwitcherScreenPacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                Level world = context.server().getLevel(payload.world());
                BlockState state = world.getBlockState(payload.pos);
                VariantBlock vBlock = (VariantBlock)state.getBlock();
                if (vBlock != null) {
                    world.setBlockAndUpdate(payload.pos, state.setValue(vBlock.variant, payload.currentState));
                }
            });
        });
    }
}
