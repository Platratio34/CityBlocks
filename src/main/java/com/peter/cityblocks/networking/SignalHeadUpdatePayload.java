package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record SignalHeadUpdatePayload(RegistryKey<World> world, BlockPos pos, int lamp, int state, int color, int headId)
        implements CustomPayload {

    public static final CustomPayload.Id<SignalHeadUpdatePayload> ID = new CustomPayload.Id<>(
            CityBlocks.identifier("signal_head_update"));
    public static final PacketCodec<RegistryByteBuf, SignalHeadUpdatePayload> CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), SignalHeadUpdatePayload::world,
            BlockPos.PACKET_CODEC, SignalHeadUpdatePayload::pos,
            PacketCodecs.INTEGER, SignalHeadUpdatePayload::lamp,
            PacketCodecs.INTEGER, SignalHeadUpdatePayload::state,
            PacketCodecs.INTEGER, SignalHeadUpdatePayload::color,
            PacketCodecs.INTEGER, SignalHeadUpdatePayload::headId,
            SignalHeadUpdatePayload::new

    );

    @Override
    public Id<SignalHeadUpdatePayload> getId() {
        return ID;
    }

    public void update(SignalHeadBlockEntity entity) {
        if (state > LampState.NULL.code) {
            entity.setState(lamp, LampState.fromCode(state));
        }
        if (color > LampColor.NULL.code) {
            entity.setColor(lamp, LampColor.fromCode(color));
        }
        if (headId > -1) {
            entity.setHeadId(headId);
        }
    }

}
