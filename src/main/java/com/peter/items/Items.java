package com.peter.items;

import com.peter.CityBlocks;
import com.peter.blocks.Blocks;
import com.peter.blocks.CellingLightBlock;
import com.peter.blocks.ExitSignBlock;
import com.peter.blocks.LargePostBlock;
import com.peter.blocks.signal.PedestrianSignalBlock;
import com.peter.blocks.signal.SignalControllerBlock;
import com.peter.blocks.signal.SignalHeadBlock;
import com.peter.blocks.signs.BuildingSignBlock;
import com.peter.blocks.signs.StreetSignBlock;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Items {

    public static final Item VARIANT_SWITCHER_ITEM = Registry.register(Registries.ITEM,
            CityBlocks.identifier("variant_switcher"), new VariantSwitcher(new Item.Settings()));

    public static final BlockItem ROAD_LINE_WHITE_CENTER_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_CENTER_BLOCK.item;
    public static final BlockItem ROAD_LINE_WHITE_SIDE_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_SIDE_BLOCK.item;
    public static final BlockItem ROAD_LINE_WHITE_SIDE_MERGE_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_CENTER_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_SIDE_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK.item;
    public static final BlockItem ROAD_ARROW_BLOCK_ITEM = Blocks.ROAD_ARROW_BLOCK.item;
    public static final BlockItem ROAD_STOP_BAR_BLOCK_ITEM = Blocks.ROAD_STOP_BAR_BLOCK.item;
    
    public static final BlockItem ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK_ITEM = Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_ARROW_ANDESITE_BLOCK_ITEM = Blocks.ROAD_ARROW_ANDESITE_BLOCK.item;
    public static final BlockItem ROAD_STOP_BAR_ANDESITE_BLOCK_ITEM = Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK.item;

    public static final BlockItem CABLE_BARRIER_BLOCK_ITEM =Blocks.CABLE_BARRIER_BLOCK.item;
    public static final BlockItem CONCRETE_BARRIER_BLOCK_ITEM = Blocks.CONCRETE_BARRIER_BLOCK.item;
    public static final BlockItem CRASH_BARRIER_BLOCK_ITEM = Blocks.CRASH_BARRIER_BLOCK.item;

    public static final BlockItem SIGN_POST_BLOCK_ITEM = Blocks.SIGN_POST_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_1_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_1_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_1_SPD_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_1_SPD_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_2_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_2_BLOCK.item;
    
    public static final BlockItem SIGNAL_HEAD_BLOCK_ITEM = SignalHeadBlock.ITEM;
    public static final BlockItem PEDESTRIAN_SIGNAL_BLOCK_ITEM = PedestrianSignalBlock.ITEM;
    public static final BlockItem SIGNAL_CONTROLLER_BLOCK_ITEM = SignalControllerBlock.ITEM;
    public static final BlockItem LARGE_POST_BLOCK_ITEM = LargePostBlock.ITEM;

    public static final BlockItem STREET_SIGN_BLOCK_ITEM = StreetSignBlock.ITEM;

    public static final Item SIGNAL_LINKER_ITEM = SignalLinker.ITEM;

    public static final BlockItem CELLING_LIGHT_BLOCK_ITEM = CellingLightBlock.ITEM;
    public static final BlockItem EXIT_SIGN_BLOCK_ITEM = ExitSignBlock.ITEM;
    public static final BlockItem BUILDING_SIGN_BLOCK_ITEM = BuildingSignBlock.ITEM;

    public static void init() {
        ItemGroups.init();
    };
}
