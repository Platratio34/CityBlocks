package com.peter.networking;

import com.peter.CityBlocks;
import com.peter.blocks.signal.PedestrianSignalBlockEntity;
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

public record PedestrianSignalUpdatePayload(RegistryKey<World> world, BlockPos pos, int headId, int state) implements CustomPayload {

    public static final CustomPayload.Id<PedestrianSignalUpdatePayload> ID = new CustomPayload.Id<>(
            CityBlocks.identifier("pedestrian_signal_update"));
    public static final PacketCodec<RegistryByteBuf, PedestrianSignalUpdatePayload> CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), PedestrianSignalUpdatePayload::world,
            BlockPos.PACKET_CODEC, PedestrianSignalUpdatePayload::pos,
            PacketCodecs.INTEGER, PedestrianSignalUpdatePayload::headId,
            PacketCodecs.INTEGER, PedestrianSignalUpdatePayload::state,
            PedestrianSignalUpdatePayload::new
    );

    @Override
    public Id<PedestrianSignalUpdatePayload> getId() {
        return ID;
    }

    public static void registerServer() {
        PayloadTypeRegistry.playC2S().register(PedestrianSignalUpdatePayload.ID, PedestrianSignalUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PedestrianSignalUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                PedestrianSignalBlockEntity entity = (PedestrianSignalBlockEntity) context.server()
                        .getWorld(payload.world())
                        .getBlockEntity(payload.pos());
                if(payload.headId() > -1) entity.setHeadId(payload.headId());
                if(payload.state() > -1) entity.setState(payload.state());
            });
        });
    }
}
