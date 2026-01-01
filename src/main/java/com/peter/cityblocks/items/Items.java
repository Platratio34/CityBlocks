package com.peter.cityblocks.items;

import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.CellingLightBlock;
import com.peter.cityblocks.blocks.ExitSignBlock;
import com.peter.cityblocks.blocks.LargePostBlock;
import com.peter.cityblocks.blocks.SlidingDoorBlock;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlock;
import com.peter.cityblocks.blocks.signal.SignalControllerBlock;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import com.peter.cityblocks.ccextended.CardReaderBlock;
import com.peter.cityblocks.items.components.ItemComponents;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Items {

    public static final Item VARIANT_SWITCHER_ITEM = VariantSwitcher.ITEM;

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
    public static final BlockItem TRAFFIC_CONE_BLOCK_ITEM = Blocks.TRAFFIC_CONE_BLOCK.item;

    public static final BlockItem SIGN_POST_BLOCK_ITEM = Blocks.SIGN_POST_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_1_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_1_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_1_SPD_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_1_SPD_BLOCK.item;
    public static final BlockItem SIGN_POST_SIGN_2_BLOCK_ITEM = Blocks.SIGN_POST_SIGN_2_BLOCK.item;
    public static final BlockItem SIGN_POST_OFFSET_BLOCK_ITEM = Blocks.SIGN_POST_OFFSET_BLOCK.item;
    
    public static final BlockItem SIGNAL_HEAD_BLOCK_ITEM = SignalHeadBlock.ITEM;
    public static final BlockItem PEDESTRIAN_SIGNAL_BLOCK_ITEM = PedestrianSignalBlock.ITEM;
    public static final BlockItem SIGNAL_CONTROLLER_BLOCK_ITEM = SignalControllerBlock.ITEM;
    public static final BlockItem LARGE_POST_BLOCK_ITEM = LargePostBlock.ITEM;

    public static final BlockItem STREET_SIGN_BLOCK_ITEM = StreetSignBlock.ITEM;

    public static final Item SIGNAL_LINKER_ITEM = SignalLinker.ITEM;

    public static final BlockItem CELLING_LIGHT_BLOCK_ITEM = CellingLightBlock.ITEM;
    public static final BlockItem EXIT_SIGN_BLOCK_ITEM = ExitSignBlock.ITEM;
    public static final BlockItem BUILDING_SIGN_BLOCK_ITEM = BuildingSignBlock.ITEM;

    public static final BlockItem SLIDING_DOOR_BLOCK_ITEM = SlidingDoorBlock.ITEM;

    public static final BlockItem CARD_READER_BLOCK_ITEM = CardReaderBlock.ITEM;

    public static final Keycard KEYCARD_BLACK_ITEM = Keycard.ITEMS[0];
    public static final Keycard KEYCARD_GRAY_ITEM = Keycard.ITEMS[1];
    public static final Keycard KEYCARD_LIGHT_GRAY_ITEM = Keycard.ITEMS[2];
    public static final Keycard KEYCARD_WHITE_ITEM = Keycard.ITEMS[3];
    public static final Keycard KEYCARD_BROWN_ITEM = Keycard.ITEMS[4];
    public static final Keycard KEYCARD_RED_ITEM = Keycard.ITEMS[5];
    public static final Keycard KEYCARD_ORANGE_ITEM = Keycard.ITEMS[6];
    public static final Keycard KEYCARD_YELLOW_ITEM = Keycard.ITEMS[7];
    public static final Keycard KEYCARD_LIME_ITEM = Keycard.ITEMS[8];
    public static final Keycard KEYCARD_GREEN_ITEM = Keycard.ITEMS[9];
    public static final Keycard KEYCARD_CYAN_ITEM = Keycard.ITEMS[10];
    public static final Keycard KEYCARD_LIGHT_BLUE_ITEM = Keycard.ITEMS[11];
    public static final Keycard KEYCARD_BLUE_ITEM = Keycard.ITEMS[12];
    public static final Keycard KEYCARD_MAGENTA_ITEM = Keycard.ITEMS[13];
    public static final Keycard KEYCARD_PURPLE_ITEM = Keycard.ITEMS[14];
    public static final Keycard KEYCARD_PINK_ITEM = Keycard.ITEMS[15];

    public static void init() {
        ItemGroups.init();
        ItemComponents.register();
    };

    public static RegistryKey<Item> irk(Identifier id) {
        return RegistryKey.of(RegistryKeys.ITEM, id);
    }
}
