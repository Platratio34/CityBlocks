package com.peter.cityblocks.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record BlockPosScreenPacket(BlockPos pos) {
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockPosScreenPacket> PACKET_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, BlockPosScreenPacket::pos,
                BlockPosScreenPacket::new);
}
