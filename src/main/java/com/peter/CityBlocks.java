package com.peter;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peter.blocks.Blocks;
import com.peter.gui.CityBlocksScreenHandlers;
import com.peter.items.Items;
import com.peter.networking.CityBlocksNetworking;

public class CityBlocks implements ModInitializer {

    public static final String MOD_ID = "city-blocks";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        Blocks.init();
        Items.init();

        CityBlocksNetworking.registerServer();
        CityBlocksScreenHandlers.register();

        LOGGER.info("City Blocks loaded");
    }
    
    public static Identifier identifier(String id) {
        return Identifier.of(MOD_ID, id);
    }

    public static MutableText translatableText(String category, String name) {
        return Text.translatable(String.format("%s.%s.%s", category, MOD_ID, name));
    }
    public static MutableText translatableText(String category, String name, Object... args) {
        return Text.translatable(String.format("%s.%s.%s", category, MOD_ID, name), args);
    }

    public static MutableText tooltip(String category, String name) {
        return Text.translatable(String.format("%s.%s.%s.tooltip", category, MOD_ID, name));
    }
}