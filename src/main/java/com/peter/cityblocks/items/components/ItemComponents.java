package com.peter.cityblocks.items.components;

import java.util.List;

import com.mojang.serialization.Codec;
import com.peter.cityblocks.CityBlocks;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemComponents {

    public static void register() {
    }

    public static <T> ComponentType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, CityBlocks.identifier(id), ComponentType.<T>builder().codec(codec).build());
    }

    public static final ComponentType<List<String>> LINES_COMPONENT_TYPE = register("lines", Codec.list(Codec.STRING));

    public static final ComponentType<Long> KEYCARD_ID_TYPE = register("keycard_id", Codec.LONG);
    public static final ComponentType<Long> KEYCARD_CRYPTO_TYPE = register("keycard_crypto", Codec.LONG);
}
