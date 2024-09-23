package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemGroups {

    public static final ItemGroup MAIN = Registry.register(Registries.ITEM_GROUP, CityBlocks.identifier("main"),
            FabricItemGroup.builder().icon(() -> new ItemStack(Items.VARIANT_SWITCHER_ITEM))
                    .displayName(CityBlocks.translatableText("itemGroup", "main"))
                    .entries((ctx, entries) -> {
                        entries.add(Items.VARIANT_SWITCHER_ITEM);
                        entries.add(Items.CELLING_LIGHT_BLOCK_ITEM);
                        entries.add(Items.EXIT_SIGN_BLOCK_ITEM);
                        entries.add(Items.BUILDING_SIGN_BLOCK_ITEM);
                    })
                    .build());
    public static final ItemGroup ROAD = Registry.register(Registries.ITEM_GROUP, CityBlocks.identifier("road"),
            FabricItemGroup.builder().icon(() -> new ItemStack(Blocks.ROAD_LINE_WHITE_CENTER_BLOCK))
                    .displayName(CityBlocks.translatableText("itemGroup", "road"))
                    .entries((ctx, entries) -> {
                        entries.add(Items.ROAD_LINE_WHITE_CENTER_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_WHITE_SIDE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_CENTER_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_SIDE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK_ITEM);
                        entries.add(Items.ROAD_ARROW_BLOCK_ITEM);
                        entries.add(Items.ROAD_STOP_BAR_BLOCK_ITEM);

                        entries.add(Items.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_ARROW_ANDESITE_BLOCK_ITEM);
                        entries.add(Items.ROAD_STOP_BAR_ANDESITE_BLOCK_ITEM);

                        entries.add(Items.CABLE_BARRIER_BLOCK_ITEM);
                        entries.add(Items.CONCRETE_BARRIER_BLOCK_ITEM);
                        entries.add(Items.CRASH_BARRIER_BLOCK_ITEM);
                        entries.add(Items.SIGN_POST_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_1_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_1_SPD_BLOCK_ITEM);
                        // entries.add(Items.SIGN_POST_SIGN_2_BLOCK_ITEM);
                        entries.add(Items.SIGNAL_HEAD_BLOCK_ITEM);
                        entries.add(Items.PEDESTRIAN_SIGNAL_BLOCK_ITEM);
                        entries.add(Items.SIGNAL_CONTROLLER_BLOCK_ITEM);
                        entries.add(Items.SIGNAL_LINKER_ITEM);
                        entries.add(Items.LARGE_POST_BLOCK_ITEM);
                        entries.add(Items.STREET_SIGN_BLOCK_ITEM);
                    })
                    .build());

    public static void init() {

    };
}
