package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;

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

public record PedestrianSignalUpdatePayload(ResourceKey<Level> world, BlockPos pos, int headId, int state) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PedestrianSignalUpdatePayload> ID = new CustomPacketPayload.Type<>(
            CityBlocks.identifier("pedestrian_signal_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PedestrianSignalUpdatePayload> CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), PedestrianSignalUpdatePayload::world,
            BlockPos.STREAM_CODEC, PedestrianSignalUpdatePayload::pos,
            ByteBufCodecs.INT, PedestrianSignalUpdatePayload::headId,
            ByteBufCodecs.INT, PedestrianSignalUpdatePayload::state,
            PedestrianSignalUpdatePayload::new
    );

    @Override
    public Type<PedestrianSignalUpdatePayload> type() {
        return ID;
    }

    public static void registerServer() {
        CityBlocks.LOGGER.info("Registering Pedestrian Signal Packet receiver");
        PayloadTypeRegistry.playC2S().register(PedestrianSignalUpdatePayload.ID, PedestrianSignalUpdatePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PedestrianSignalUpdatePayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                CityBlocks.debug("Received Pedestrian Signal Update: {}", payload.toString());
                PedestrianSignalBlockEntity entity = (PedestrianSignalBlockEntity) context.server()
                        .getLevel(payload.world())
                        .getBlockEntity(payload.pos());
                if (payload.headId() > -1)
                    entity.setHeadId(payload.headId());
                if (payload.state() > -1)
                    entity.setState(payload.state());
            });
        });
    }
    

    @Override
    public final String toString() {
        return String.format(
                "PedestrianSignalUpdatePayload{world=%s; pos=%s; headId=%d; state=%s}", world.location().toString(), pos.toString(),
                headId, LampState.fromCode(state));
    }
}
