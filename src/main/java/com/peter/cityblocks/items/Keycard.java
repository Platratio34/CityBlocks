package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Keycard extends Item {

    public static final String NAME = "keycard";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    
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
        ResourceLocation id = CityBlocks.identifier(NAME + "_" + color);
        return Registry.register(BuiltInRegistries.ITEM, id, new Keycard(new Properties().setId(Items.irk(id)), color));
    }

    public final String color;

    public Keycard(Properties settings, String color) {
        super(settings);
        this.color = color;
    }

}
