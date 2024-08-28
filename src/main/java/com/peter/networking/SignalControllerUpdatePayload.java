package com.peter.networking;

import com.peter.CityBlocks;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record SignalControllerUpdatePayload(RegistryKey<World> world, BlockPos pos, int cycleMode) implements CustomPayload {

    public static final CustomPayload.Id<SignalControllerUpdatePayload> ID = new CustomPayload.Id<>(
            CityBlocks.identifier("signal_controller_update"));
    public static final PacketCodec<RegistryByteBuf, SignalControllerUpdatePayload> CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), SignalControllerUpdatePayload::world,
            BlockPos.PACKET_CODEC, SignalControllerUpdatePayload::pos,
            PacketCodecs.INTEGER, SignalControllerUpdatePayload::cycleMode,
            SignalControllerUpdatePayload::new
    );

    @Override
    public Id<SignalControllerUpdatePayload> getId() {
        return ID;
    }


}
