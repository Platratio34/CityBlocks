package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroups {

    public static final CreativeModeTab MAIN = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CityBlocks.identifier("main"),
            FabricItemGroup.builder().icon(() -> new ItemStack(Items.VARIANT_SWITCHER_ITEM))
                    .title(CityBlocks.translatableText("itemGroup", "main"))
                    .displayItems((ctx, entries) -> {
                        entries.accept(Items.VARIANT_SWITCHER_ITEM);
                        entries.accept(Items.CELLING_LIGHT_BLOCK_ITEM);
                        entries.accept(Items.EXIT_SIGN_BLOCK_ITEM);
                        entries.accept(Items.BUILDING_SIGN_BLOCK_ITEM);
                        entries.accept(Items.SLIDING_DOOR_BLOCK_ITEM);
                    })
                    .build());
    public static final CreativeModeTab ROAD = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CityBlocks.identifier("road"),
            FabricItemGroup.builder().icon(() -> new ItemStack(Blocks.ROAD_LINE_WHITE_CENTER_BLOCK))
                    .title(CityBlocks.translatableText("itemGroup", "road"))
                    .displayItems((ctx, entries) -> {
                        entries.accept(Items.ROAD_LINE_WHITE_CENTER_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_WHITE_SIDE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_CENTER_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_SIDE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_ARROW_BLOCK_ITEM);
                        entries.accept(Items.ROAD_STOP_BAR_BLOCK_ITEM);

                        entries.accept(Items.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_ARROW_ANDESITE_BLOCK_ITEM);
                        entries.accept(Items.ROAD_STOP_BAR_ANDESITE_BLOCK_ITEM);

                        entries.accept(Items.CABLE_BARRIER_BLOCK_ITEM);
                        entries.accept(Items.CONCRETE_BARRIER_BLOCK_ITEM);
                        entries.accept(Items.CRASH_BARRIER_BLOCK_ITEM);
                        entries.accept(Items.TRAFFIC_CONE_BLOCK_ITEM);
                        entries.accept(Items.SIGN_POST_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_1_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_1_SPD_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_2_BLOCK_ITEM);
                        entries.accept(Items.SIGNAL_HEAD_BLOCK_ITEM);
                        entries.accept(Items.PEDESTRIAN_SIGNAL_BLOCK_ITEM);
                        entries.accept(Items.SIGNAL_CONTROLLER_BLOCK_ITEM);
                        entries.accept(Items.SIGNAL_LINKER_ITEM);
                        entries.accept(Items.LARGE_POST_BLOCK_ITEM);
                        entries.accept(Items.SIGN_POST_OFFSET_BLOCK_ITEM);
                        entries.accept(Items.STREET_SIGN_BLOCK_ITEM);
                    })
                    .build());

    public static final CreativeModeTab CC_EXTRA = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CityBlocks.identifier("ccextra"),
            FabricItemGroup.builder().icon(() -> new ItemStack(Items.CARD_READER_BLOCK_ITEM))
                    .title(CityBlocks.translatableText("itemGroup", "ccextra"))
                    .displayItems((ctx, entries) -> {
                        entries.accept(Items.CARD_READER_BLOCK_ITEM);
                        entries.accept(Items.KEYCARD_BLACK_ITEM);
                        entries.accept(Items.KEYCARD_GRAY_ITEM);
                        entries.accept(Items.KEYCARD_LIGHT_GRAY_ITEM);
                        entries.accept(Items.KEYCARD_WHITE_ITEM);
                        entries.accept(Items.KEYCARD_BROWN_ITEM);
                        entries.accept(Items.KEYCARD_RED_ITEM);
                        entries.accept(Items.KEYCARD_ORANGE_ITEM);
                        entries.accept(Items.KEYCARD_YELLOW_ITEM);
                        entries.accept(Items.KEYCARD_LIME_ITEM);
                        entries.accept(Items.KEYCARD_GREEN_ITEM);
                        entries.accept(Items.KEYCARD_CYAN_ITEM);
                        entries.accept(Items.KEYCARD_LIGHT_BLUE_ITEM);
                        entries.accept(Items.KEYCARD_BLUE_ITEM);
                        entries.accept(Items.KEYCARD_MAGENTA_ITEM);
                        entries.accept(Items.KEYCARD_PURPLE_ITEM);
                        entries.accept(Items.KEYCARD_PINK_ITEM);
                    })
                    .build());

    public static void init() {

    };
}
