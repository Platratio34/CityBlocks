package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record SignalHeadUpdatePayload(ResourceKey<Level> world, BlockPos pos, int lamp, int state, int color, int headId, int lampCount)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SignalHeadUpdatePayload> ID = new CustomPacketPayload.Type<>(
            CityBlocks.identifier("signal_head_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SignalHeadUpdatePayload> CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), SignalHeadUpdatePayload::world,
            BlockPos.STREAM_CODEC, SignalHeadUpdatePayload::pos,
            ByteBufCodecs.INT, SignalHeadUpdatePayload::lamp,
            ByteBufCodecs.INT, SignalHeadUpdatePayload::state,
            ByteBufCodecs.INT, SignalHeadUpdatePayload::color,
            ByteBufCodecs.INT, SignalHeadUpdatePayload::headId,
            ByteBufCodecs.INT, SignalHeadUpdatePayload::lampCount,
            SignalHeadUpdatePayload::new

    );

    @Override
    public Type<SignalHeadUpdatePayload> type() {
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
        if (lampCount > -1) {
            entity.setLampCount(lampCount);
        }
    }

    @Override
    public final String toString() {
        return String.format(
                "SignalHeadUpdatePayload{world=%s; pos=%s; lamp=%d; state=%s; color=%s; headId=%d; lampCount=%d}", world.location().toString(), pos.toString(),
                lamp, LampState.fromCode(state), LampColor.fromCode(color), headId, lampCount);
    }

}
