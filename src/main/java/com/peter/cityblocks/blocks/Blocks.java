package com.peter.cityblocks.blocks;

import com.peter.cityblocks.blocks.signal.PedestrianSignalBlock;
import com.peter.cityblocks.blocks.signal.SignalControllerBlock;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.CustomSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Blocks {

    public static final RoadLineBlock ROAD_LINE_WHITE_CENTER_BLOCK = new RoadLineBlock("road_line_white_center",
            MapColor.WHITE, RoadLineBlock.WHITE_CENTER, RoadType.BLACKSTONE);
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_BLOCK = new RoadLineBlock("road_line_white_side",
            MapColor.WHITE, RoadLineBlock.WHITE_SIDE, RoadType.BLACKSTONE);
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_MERGE_BLOCK = new RoadLineBlock("road_line_white_side_merge",
            MapColor.WHITE, RoadLineBlock.WHITE_SIDE_MERGE, RoadType.BLACKSTONE);

    public static final RoadLineBlock ROAD_LINE_YELLOW_CENTER_BLOCK = new RoadLineBlock("road_line_yellow_center",
            MapColor.YELLOW, RoadLineBlock.YELLOW_CENTER, RoadType.BLACKSTONE);
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_BLOCK = new RoadLineBlock("road_line_yellow_side",
            MapColor.YELLOW, RoadLineBlock.YELLOW_SIDE, RoadType.BLACKSTONE);
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK = new RoadLineBlock("road_line_yellow_side_merge",
            MapColor.YELLOW, RoadLineBlock.YELLOW_SIDE_MERGE, RoadType.BLACKSTONE);

    public static final RoadLineBlock ROAD_ARROW_BLOCK = new RoadLineBlock("road_arrow",
            MapColor.LIGHT_GRAY, RoadLineBlock.ARROW, RoadType.BLACKSTONE);
    public static final RoadLineBlock ROAD_STOP_BAR_BLOCK = new RoadLineBlock("road_stop_bar",
            MapColor.WHITE, RoadLineBlock.STOP_BAR, RoadType.BLACKSTONE);

    public static final RoadLineBlock ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_center_andesite",
            MapColor.WHITE, RoadLineBlock.WHITE_CENTER, RoadType.ANDESITE);
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_side_andesite",
            MapColor.WHITE, RoadLineBlock.WHITE_SIDE, RoadType.ANDESITE);
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_side_merge_andesite",
            MapColor.WHITE, RoadLineBlock.WHITE_SIDE_MERGE, RoadType.ANDESITE);


    public static final RoadLineBlock ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_center_andesite",
            MapColor.YELLOW, RoadLineBlock.YELLOW_CENTER, RoadType.ANDESITE);
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_side_andesite",
            MapColor.YELLOW, RoadLineBlock.YELLOW_SIDE, RoadType.ANDESITE);
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_side_merge_andesite",
            MapColor.YELLOW, RoadLineBlock.YELLOW_SIDE_MERGE, RoadType.ANDESITE);

    public static final RoadLineBlock ROAD_ARROW_ANDESITE_BLOCK = new RoadLineBlock("road_arrow_andesite",
            MapColor.LIGHT_GRAY, RoadLineBlock.ARROW, RoadType.ANDESITE);
    
    public static final RoadLineBlock ROAD_STOP_BAR_ANDESITE_BLOCK = new RoadLineBlock("road_stop_bar_andesite",
            MapColor.WHITE, RoadLineBlock.STOP_BAR, RoadType.ANDESITE);

    public static final VariantPartialBlock CABLE_BARRIER_BLOCK = new CableBarrierBlock("cable_barrier");
    public static final VariantPartialBlock CONCRETE_BARRIER_BLOCK = new ConcreteBarrierBlock("concrete_barrier");
    public static final VariantPartialBlock CRASH_BARRIER_BLOCK = new CrashBarrierBlock("crash_barrier");

    public static final VariantPartialBlock SIGN_POST_BLOCK = new SignPostBlock("sign_post");
    public static final VariantPartialBlock SIGN_POST_SIGN_1_BLOCK = new SignPostSign1Block("sign_post_sign_1", 2);
    public static final VariantPartialBlock SIGN_POST_SIGN_1_SPD_BLOCK = new SignPostSign1Block("sign_post_sign_1_spd", 12);
    public static final VariantPartialBlock SIGN_POST_SIGN_2_BLOCK = new SignPostSign2Block("sign_post_sign_2", 4);

    public static final Block SIGNAL_HEAD_BLOCK = SignalHeadBlock.BLOCK;
    public static final Block PEDESTRIAN_SIGNAL_BLOCK = PedestrianSignalBlock.BLOCK;
    public static final Block SIGNAL_CONTROLLER_BLOCK = SignalControllerBlock.BLOCK;
    public static final Block LARGE_POST = LargePostBlock.BLOCK;

    public static final CustomSignBlock STREET_SIGN_BLOCK = StreetSignBlock.BLOCK;

    public static final VariantPartialBlock CELLING_LIGHT_BLOCK = CellingLightBlock.BLOCK;
    public static final VariantPartialBlock EXIT_SIGN_BLOCK = ExitSignBlock.BLOCK;
    public static final CustomSignBlock BUILDING_SIGN_BLOCK = BuildingSignBlock.BLOCK;

    public static final SlidingDoorBlock SLIDING_DOOR_BLOCK = SlidingDoorBlock.BLOCK;

    public static void init() {
        SignalHeadBlockEntity.register();
        SignalControllerBlockEntity.register();
    };

    public static BlockItem registerBlockItem(Block block, Identifier id, Item.Settings settings) {
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id));
        return Registry.register(Registries.ITEM, id, new BlockItem(block, settings));
    }

    public static RegistryKey<Item> irk(Identifier id) {
        return RegistryKey.of(RegistryKeys.ITEM, id);
    }

    public static RegistryKey<Block> brk(Identifier id) {
        return RegistryKey.of(RegistryKeys.BLOCK, id);
    }
}
