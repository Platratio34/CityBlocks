package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.VariantBlock;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record VariantSwitcherScreenPacket(RegistryKey<World> world, BlockPos pos, int currentState) implements CustomPayload {

    public static final CustomPayload.Id<VariantSwitcherScreenPacket> ID = new CustomPayload.Id<>(CityBlocks.identifier("variant_switcher_screen"));

    public static final PacketCodec<RegistryByteBuf, VariantSwitcherScreenPacket> PACKET_CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), VariantSwitcherScreenPacket::world,
            BlockPos.PACKET_CODEC, VariantSwitcherScreenPacket::pos,
            PacketCodecs.INTEGER, VariantSwitcherScreenPacket::currentState,
        VariantSwitcherScreenPacket::new
    );

    @Override
    public Id<VariantSwitcherScreenPacket> getId() {
        return ID;
    }

    public static void registerServer() {
        CityBlocks.LOGGER.info("Registering variant switcher screen packet receiver");

        PayloadTypeRegistry.playC2S().register(VariantSwitcherScreenPacket.ID, VariantSwitcherScreenPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(VariantSwitcherScreenPacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                World world = context.server().getWorld(payload.world());
                BlockState state = world.getBlockState(payload.pos);
                VariantBlock vBlock = (VariantBlock)state.getBlock();
                if (vBlock != null) {
                    world.setBlockState(payload.pos, state.with(vBlock.variant, payload.currentState));
                }
            });
        });
    }
}
