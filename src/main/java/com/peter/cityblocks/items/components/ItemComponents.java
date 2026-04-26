package com.peter.cityblocks.items.components;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import com.mojang.serialization.Codec;
import com.peter.cityblocks.CityBlocks;

public class ItemComponents {

    public static void register() {
    }

    public static <T> DataComponentType<T> register(String id, Codec<T> codec) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CityBlocks.identifier(id), DataComponentType.<T>builder().persistent(codec).build());
    }

    public static final DataComponentType<List<String>> LINES_COMPONENT_TYPE = register("lines", Codec.list(Codec.STRING));

    public static final DataComponentType<Long> KEYCARD_ID_TYPE = register("keycard_id", Codec.LONG);
    public static final DataComponentType<Long> KEYCARD_CRYPTO_TYPE = register("keycard_crypto", Codec.LONG);
}
