package com.peter.cityblocks.blocks;

import com.peter.cityblocks.blocks.RoadLineBlock.RoadLineModel;
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
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_center", "road_line_white_center"),
        new RoadLineModel("road_line_white_center_reflector", "road_line_white_center_reflector")
    });
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_BLOCK = new RoadLineBlock("road_line_white_side",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_side", "road_line_white_side"),
        new RoadLineModel("road_line_white_side_angle", "road_line_white_side_angle"),
        new RoadLineModel("road_line_white_side_angle_end", "road_line_white_side_angle_end"),
        new RoadLineModel("road_line_white_chevron", "road_line_white_chevron"),
        new RoadLineModel("road_line_white_side_thick", "road_line_white_side_thick"),
    });
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_MERGE_BLOCK = new RoadLineBlock("road_line_white_side_merge",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_side_merge1", "road_line_white_side_merge1"),
        new RoadLineModel("road_line_white_side_merge2", "road_line_white_side_merge2"),
        new RoadLineModel("road_line_white_side_merge1f", "road_line_white_side_merge1f"),
        new RoadLineModel("road_line_white_side_merge2f", "road_line_white_side_merge2f"),
        new RoadLineModel("road_line_white_side_merge_corner", "road_line_white_side_merge_corner"),
        new RoadLineModel("road_line_white_side_merge_cornerf", "road_line_white_side_merge_cornerf"),
        new RoadLineModel("road_line_white_side_merge_double", "road_line_white_side_merge_double"),
    });

    public static final RoadLineBlock ROAD_LINE_YELLOW_CENTER_BLOCK = new RoadLineBlock("road_line_yellow_center",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_center", "road_line_yellow_center"),
        new RoadLineModel("road_line_yellow_double", "road_line_yellow_double"),
        new RoadLineModel("road_line_yellow_double_single", "road_line_yellow_double_single"),
        new RoadLineModel("road_line_yellow_double_single_reflector", "road_line_yellow_double_single_reflector"),
        new RoadLineModel("road_line_yellow_center_reflector", "road_line_yellow_center_reflector")
    });
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_BLOCK = new RoadLineBlock("road_line_yellow_side",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side", "road_line_yellow_side"),
        new RoadLineModel("road_line_yellow_side_angle", "road_line_yellow_side_angle"),
        new RoadLineModel("road_line_yellow_side_angle_end", "road_line_yellow_side_angle_end"),
        new RoadLineModel("road_line_yellow_chevron", "road_line_yellow_chevron"),
    });
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK = new RoadLineBlock("road_line_yellow_side_merge",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side_merge1", "road_line_yellow_side_merge1"),
        new RoadLineModel("road_line_yellow_side_merge2", "road_line_yellow_side_merge2"),
        new RoadLineModel("road_line_yellow_side_merge1f", "road_line_yellow_side_merge1f"),
        new RoadLineModel("road_line_yellow_side_merge2f", "road_line_yellow_side_merge2f"),
        new RoadLineModel("road_line_yellow_side_merge_corner", "road_line_yellow_side_merge_corner"),
        new RoadLineModel("road_line_yellow_side_merge_cornerf", "road_line_yellow_side_merge_cornerf"),
        new RoadLineModel("road_line_yellow_side_merge_double", "road_line_yellow_side_merge_double"),
            });

    public static final RoadLineBlock ROAD_ARROW_BLOCK = new RoadLineBlock("road_arrow",
            MapColor.LIGHT_GRAY, new RoadLineModel[] {
        new RoadLineModel("road_arrow_straight", "road_arrow_straight"),
        new RoadLineModel("road_arrow_left", "road_arrow_left"),
        new RoadLineModel("road_arrow_right", "road_arrow_right"),
        new RoadLineModel("road_arrow_straight_left", "road_arrow_straight_left"),
        new RoadLineModel("road_arrow_straight_right", "road_arrow_straight_right"),
        new RoadLineModel("road_arrow_left_right", "road_arrow_left_right"),
        new RoadLineModel("road_arrow_straight_left_right", "road_arrow_straight_left_right"),
    });
    public static final RoadLineBlock ROAD_STOP_BAR_BLOCK = new RoadLineBlock("road_stop_bar",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_stop_bar_center", "road_stop_bar_center"),
        new RoadLineModel("road_stop_bar_left", "road_stop_bar_left"),
        new RoadLineModel("road_stop_bar_right", "road_stop_bar_right"),
        new RoadLineModel("road_stop_bar_left_yellow", "road_stop_bar_left_yellow"),
    });

    public static final RoadLineBlock ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_center_andesite",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_center_andesite", "road_line_white_center"),
        new RoadLineModel("road_line_white_center_andesite_reflector", "road_line_white_center_reflector")
    }).andesite();
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_side_andesite",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_side_andesite", "road_line_white_side"),
        new RoadLineModel("road_line_white_side_andesite_angle", "road_line_white_side_angle"),
        new RoadLineModel("road_line_white_side_andesite_angle_end", "road_line_white_side_angle_end"),
        new RoadLineModel("road_line_white_chevron_andesite", "road_line_white_chevron"),
        new RoadLineModel("road_line_white_side_andesite_thick", "road_line_white_side_thick"),
    }).andesite();
    public static final RoadLineBlock ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK = new RoadLineBlock("road_line_white_side_merge_andesite",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_line_white_side_andesite_merge1", "road_line_white_side_merge1"),
        new RoadLineModel("road_line_white_side_andesite_merge2", "road_line_white_side_merge2"),
        new RoadLineModel("road_line_white_side_andesite_merge1f", "road_line_white_side_merge1f"),
        new RoadLineModel("road_line_white_side_andesite_merge2f", "road_line_white_side_merge2f"),
        new RoadLineModel("road_line_white_side_andesite_merge_corner", "road_line_white_side_merge_corner"),
        new RoadLineModel("road_line_white_side_andesite_merge_cornerf", "road_line_white_side_merge_cornerf"),
        new RoadLineModel("road_line_white_side_andesite_merge_double", "road_line_white_side_merge_double"),
    }).andesite();


    public static final RoadLineBlock ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_center_andesite",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_center_andesite", "road_line_yellow_center"),
        new RoadLineModel("road_line_yellow_double_andesite", "road_line_yellow_double"),
        new RoadLineModel("road_line_yellow_double_andesite_single", "road_line_yellow_double_single"),
        new RoadLineModel("road_line_yellow_double_andesite_single_reflector", "road_line_yellow_double_single_reflector"),
        new RoadLineModel("road_line_yellow_center_andesite_reflector", "road_line_yellow_center_reflector")
    }).andesite();
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_side_andesite",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side_andesite", "road_line_yellow_side"),
        new RoadLineModel("road_line_yellow_side_andesite_angle", "road_line_yellow_side_angle"),
        new RoadLineModel("road_line_yellow_side_andesite_angle_end", "road_line_yellow_side_angle_end"),
        new RoadLineModel("road_line_yellow_chevron_andesite", "road_line_yellow_chevron"),
    }).andesite();
    public static final RoadLineBlock ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK = new RoadLineBlock("road_line_yellow_side_merge_andesite",
            MapColor.YELLOW, new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side_andesite_merge1", "road_line_yellow_side_merge1"),
        new RoadLineModel("road_line_yellow_side_andesite_merge2", "road_line_yellow_side_merge2"),
        new RoadLineModel("road_line_yellow_side_andesite_merge1f", "road_line_yellow_side_merge1f"),
        new RoadLineModel("road_line_yellow_side_andesite_merge2f", "road_line_yellow_side_merge2f"),
        new RoadLineModel("road_line_yellow_side_andesite_merge_corner", "road_line_yellow_side_merge_corner"),
        new RoadLineModel("road_line_yellow_side_andesite_merge_cornerf", "road_line_yellow_side_merge_cornerf"),
        new RoadLineModel("road_line_yellow_side_andesite_merge_double", "road_line_yellow_side_merge_double"),
    }).andesite();

    public static final RoadLineBlock ROAD_ARROW_ANDESITE_BLOCK = new RoadLineBlock("road_arrow_andesite",
            MapColor.LIGHT_GRAY, new RoadLineModel[] {
        new RoadLineModel("road_arrow_andesite_straight", "road_arrow_straight"),
        new RoadLineModel("road_arrow_andesite_left", "road_arrow_left"),
        new RoadLineModel("road_arrow_andesite_right", "road_arrow_right"),
        new RoadLineModel("road_arrow_andesite_straight_left", "road_arrow_straight_left"),
        new RoadLineModel("road_arrow_andesite_straight_right", "road_arrow_straight_right"),
        new RoadLineModel("road_arrow_andesite_left_right", "road_arrow_left_right"),
        new RoadLineModel("road_arrow_andesite_straight_left_right", "road_arrow_straight_left_right"),
    }).andesite();
    
    public static final RoadLineBlock ROAD_STOP_BAR_ANDESITE_BLOCK = new RoadLineBlock("road_stop_bar_andesite",
            MapColor.WHITE, new RoadLineModel[] {
        new RoadLineModel("road_stop_bar_andesite_center", "road_stop_bar_center"),
        new RoadLineModel("road_stop_bar_andesite_left", "road_stop_bar_left"),
        new RoadLineModel("road_stop_bar_andesite_right", "road_stop_bar_right"),
        new RoadLineModel("road_stop_bar_andesite_left_yellow", "road_stop_bar_left_yellow"),
    }).andesite();

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
