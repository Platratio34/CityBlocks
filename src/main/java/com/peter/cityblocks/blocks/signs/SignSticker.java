package com.peter.cityblocks.blocks.signs;

import com.mojang.serialization.Codec;
import com.peter.cityblocks.CityBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;

public class SignSticker {

    public ResourceLocation stickerId;
    public int x;
    public int y;
    public float z = 0;

    private SignSticker() {
    };

    public SignSticker(ResourceLocation id, int x, int y) {
        stickerId = id;
        this.x = x;
        this.y = y;
    }

    public SignSticker(String id, int x, int y) {
        this(CityBlocks.identifier(id), x, y);
    }

    private static final String NBT_ID = "id";
    private static final String NBT_X = "x";
    private static final String NBT_Y = "y";
    private static final String NBT_Z = "z";
    public static final Codec<SignSticker> CODEC = Codec.withAlternative(CompoundTag.CODEC, TagParser.FLATTENED_CODEC).xmap(SignSticker::fromNbt, a -> a.toNbt());

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString(NBT_ID, String.format("%s:%s",stickerId.getNamespace(),stickerId.getPath()));
        nbt.putInt(NBT_X, x);
        nbt.putInt(NBT_Y, y);
        nbt.putFloat(NBT_Z, z);

        return nbt;
    }
    
    public static SignSticker fromNbt(CompoundTag nbt) {
        SignSticker sticker = new SignSticker();
        String id = nbt.getString(NBT_ID).get();
        sticker.stickerId = ResourceLocation.bySeparator(id, ':');
        sticker.x = nbt.getInt(NBT_X).get();
        sticker.y = nbt.getInt(NBT_Y).get();
        sticker.z = nbt.getFloat(NBT_Z).get();
        return sticker;
    }

}
