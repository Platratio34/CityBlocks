package com.peter.cityblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.gui.CityBlocksScreenHandlers;
import com.peter.cityblocks.items.Items;
import com.peter.cityblocks.networking.CityBlocksNetworking;

public class CityBlocks implements ModInitializer {

    public static final String MOD_ID = "city-blocks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static boolean debug = false;
    private static boolean dev = false;

	@Override
    public void onInitialize() {
        LOGGER.info("-------------------------------------------------------------------");
        LOGGER.info("  ###  ###  #####  #   #        ###   #     ##    ###  #  #   #### ");
        LOGGER.info(" #      #     #    #   #        #  #  #    #  #  #     # #   #     ");
        LOGGER.info(" #      #     #     # #    ###  ###   #    #  #  #     ##     ###  ");
        LOGGER.info(" #      #     #      #          #  #  #    #  #  #     # #       # ");
        LOGGER.info("  ###  ###    #      #          ###   ####  ##    ###  #  #  ####  ");
        LOGGER.info("-------------------------------------------------------------------");

        ModContainer mod = FabricLoader.getInstance().getModContainer(MOD_ID).get();
        String modVersion = mod.getMetadata().getVersion().getFriendlyString();
        if (modVersion.contains("-dev")) {
            debug = true;
            dev = true;
            LOGGER.warn("You are running a development version of CityBlocks: {}", modVersion);
            LOGGER.warn("\tConsider replacing it with a stable release");
        } else if (modVersion.contains("-debug")) {
            debug = true;
            LOGGER.warn("You are running a debug version of CityBlocks: {}", modVersion);
            LOGGER.warn("\tConsider replacing it with a stable release");
        } else {
            LOGGER.info("Version {}", modVersion);
        }

        if (debug) {
            debug("Debug logging enabled");
        }

        Blocks.init();
        Items.init();

        CityBlocksNetworking.registerServer();
        CityBlocksScreenHandlers.register();

        LOGGER.info("City Blocks loaded");
    }
    
    public static ResourceLocation identifier(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static MutableComponent translatableText(String category, String name) {
        return Component.translatable(String.format("%s.%s.%s", category, MOD_ID, name));
    }
    public static MutableComponent translatableText(String category, String name, Object... args) {
        return Component.translatable(String.format("%s.%s.%s", category, MOD_ID, name), args);
    }

    public static MutableComponent tooltip(String category, String name) {
        return Component.translatable(String.format("%s.%s.%s.tooltip", category, MOD_ID, name));
    }

    public static void debug(String msg) {
        if (debug) {
            LOGGER.info("DEBUG: " + msg);
        }
    }

    public static void debug(String msg, Object... args) {
        if (debug) {
            LOGGER.info("DEBUG: " + msg, args);
        }
    }
    
    public static boolean isDev() {
        return dev;
    }

    public static boolean isDebug() {
        return debug;
    }
}