package com.peter.cityblocks.blocks.signs;

import com.peter.cityblocks.CityBlocks;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class SignSticker {

    public Identifier stickerId;
    public int x;
    public int y;
    public float z = 0;

    private SignSticker() {
    };

    public SignSticker(Identifier id, int x, int y) {
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

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();

        nbt.putString(NBT_ID, String.format("%s:%s",stickerId.getNamespace(),stickerId.getPath()));
        nbt.putInt(NBT_X, x);
        nbt.putInt(NBT_Y, y);
        nbt.putFloat(NBT_Z, z);

        return nbt;
    }
    
    public static SignSticker fromNbt(NbtCompound nbt) {
        SignSticker sticker = new SignSticker();
        String id = nbt.getString(NBT_ID);
        sticker.stickerId = Identifier.splitOn(id, ':');
        sticker.x = nbt.getInt(NBT_X);
        sticker.y = nbt.getInt(NBT_Y);
        sticker.z = nbt.getFloat(NBT_Z);
        return sticker;
    }

}
