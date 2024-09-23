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

public class Blocks {

    public static final VariantBlock ROAD_LINE_WHITE_CENTER_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(2).solid().mapColor(MapColor.WHITE),
            "road_line_white_center");
    public static final VariantBlock ROAD_LINE_WHITE_SIDE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(5).solid().mapColor(MapColor.WHITE),
            "road_line_white_side");
    public static final VariantBlock ROAD_LINE_WHITE_SIDE_MERGE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(7).solid().mapColor(MapColor.WHITE),
            "road_line_white_side_merge");

    public static final VariantBlock ROAD_LINE_YELLOW_CENTER_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(5).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_center");
    public static final VariantBlock ROAD_LINE_YELLOW_SIDE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(4).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_side");
    public static final VariantBlock ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(7).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_side_merge");

    public static final VariantBlock ROAD_ARROW_BLOCK = new VariantBlock(new VariantSettings().setVariants(7).solid().mapColor(MapColor.LIGHT_GRAY),
            "road_arrow");

    public static final VariantBlock ROAD_STOP_BAR_BLOCK = new VariantBlock(new VariantSettings().setVariants(4).solid().mapColor(MapColor.WHITE),
            "road_stop_bar");

    public static final VariantBlock ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(2).solid().mapColor(MapColor.WHITE),
            "road_line_white_center_andesite");
    public static final VariantBlock ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(5).solid().mapColor(MapColor.WHITE),
            "road_line_white_side_andesite");
    public static final VariantBlock ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(7).solid().mapColor(MapColor.WHITE),
            "road_line_white_side_merge_andesite");

    public static final VariantBlock ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(5).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_center_andesite");
    public static final VariantBlock ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(4).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_side_andesite");
    public static final VariantBlock ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK = new VariantBlock(
            new VariantSettings().setVariants(7).solid().mapColor(MapColor.YELLOW),
            "road_line_yellow_side_merge_andesite");

    public static final VariantBlock ROAD_ARROW_ANDESITE_BLOCK = new VariantBlock(new VariantSettings().setVariants(7).solid().mapColor(MapColor.LIGHT_GRAY),
            "road_arrow_andesite");

    public static final VariantBlock ROAD_STOP_BAR_ANDESITE_BLOCK = new VariantBlock(new VariantSettings().setVariants(4).solid().mapColor(MapColor.WHITE),
            "road_stop_bar_andesite");

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

    public static void init() {
        SignalHeadBlockEntity.register();
        SignalControllerBlockEntity.register();
    };
}
