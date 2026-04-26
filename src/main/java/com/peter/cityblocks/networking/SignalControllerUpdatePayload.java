package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record SignalControllerUpdatePayload(ResourceKey<Level> world, BlockPos pos, int cycleMode) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SignalControllerUpdatePayload> ID = new CustomPacketPayload.Type<>(
            CityBlocks.identifier("signal_controller_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SignalControllerUpdatePayload> CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), SignalControllerUpdatePayload::world,
            BlockPos.STREAM_CODEC, SignalControllerUpdatePayload::pos,
            ByteBufCodecs.INT, SignalControllerUpdatePayload::cycleMode,
            SignalControllerUpdatePayload::new
    );

    @Override
    public Type<SignalControllerUpdatePayload> type() {
        return ID;
    }

    @Override
    public final String toString() {
        return String.format("SignalControllerUpdatePayload{world=%s; pos=%s; cycleMode=%d}",
                world.location().toString(), pos.toString(), cycleMode);
    }


}
