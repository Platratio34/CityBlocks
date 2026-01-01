package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Keycard extends Item {

    public static final String NAME = "keycard";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    
    public static final Keycard[] ITEMS = new Keycard[] {
        register("black"),
        register("gray"),
        register("light_gray"),
        register("white"),
        register("brown"),
        register("red"),
        register("orange"),
        register("yellow"),
        register("lime"),
        register("green"),
        register("cyan"),
        register("light_blue"),
        register("blue"),
        register("purple"),
        register("magenta"),
        register("pink")
    };

    private static final Keycard register(String color) {
        Identifier id = CityBlocks.identifier(NAME + "_" + color);
        return Registry.register(Registries.ITEM, id, new Keycard(new Settings().registryKey(Items.irk(id)), color));
    }

    public final String color;

    public Keycard(Settings settings, String color) {
        super(settings);
        this.color = color;
    }

}
