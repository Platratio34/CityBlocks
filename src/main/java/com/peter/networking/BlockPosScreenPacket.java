package com.peter.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record BlockPosScreenPacket(BlockPos pos) {
    public static final PacketCodec<RegistryByteBuf, BlockPosScreenPacket> PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, BlockPosScreenPacket::pos,
                BlockPosScreenPacket::new);
}
