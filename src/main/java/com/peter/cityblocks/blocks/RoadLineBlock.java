package com.peter.cityblocks.blocks;

import java.util.HashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import com.peter.cityblocks.CityBlocks;

public class RoadLineBlock extends VariantBlock {

    public static final HashMap<String, RoadLineBlock> ROAD_LINE_BLOCKS = new HashMap<>();

    public final RoadLineModel[] models;

    public final RoadType roadType;
    public final ResourceLocation color;
    public final String itemModelId;

    public RoadLineBlock(String name, MapColor color, RoadLineModel[] models, RoadType roadType, ResourceLocation lineColor) {
        super(new VariantSettings().setVariants(models.length).mapColor(color).forceSolidOn(), name, getModelVariants(models, roadType));
        this.models = models;
        this.roadType = roadType;
        this.color = lineColor;
        ROAD_LINE_BLOCKS.put(name, this);
        itemModelId = name;
    }

    public RoadLineBlock(String name, MapColor color, RoadLineModel[] models, RoadType roadType, ResourceLocation lineColor, String itemModelID) {
        super(new VariantSettings().setVariants(models.length).mapColor(color).forceSolidOn(), name, getModelVariants(models, roadType));
        this.models = models;
        this.roadType = roadType;
        this.color = lineColor;
        ROAD_LINE_BLOCKS.put(name, this);
        this.itemModelId = itemModelID;
    }

    private static ResourceLocation[] getModelVariants(RoadLineModel[] models, RoadType roadType) {
        ResourceLocation[] arr = new ResourceLocation[models.length];
        for (int i = 0; i < models.length; i++) {
            arr[i] = CityBlocks.identifier(models[i].model().replace("{}", roadType.extension));
        }
        return arr;
    }

    public static record RoadLineModel(String model, String overlay) {

    }

    public static final ResourceLocation COLOR_WHITE = ResourceLocation.fromNamespaceAndPath("minecraft", "block/calcite");
    public static final ResourceLocation COLOR_YELLOW = ResourceLocation.fromNamespaceAndPath("minecraft", "block/yellow_terracotta");
    public static final RoadLineModel[] WHITE_CENTER = new RoadLineModel[] {
        new RoadLineModel("road_line_white_center{}", "road_line_center"),
        new RoadLineModel("road_line_white_center{}_reflector", "road_line_center_reflector")
    };
    public static final RoadLineModel[] WHITE_SIDE = new RoadLineModel[] {
        new RoadLineModel("road_line_white_side{}", "road_line_side"),
        new RoadLineModel("road_line_white_side{}_angle", "road_line_side_angle"),
        new RoadLineModel("road_line_white_side{}_angle_end", "road_line_side_angle_end"),
        new RoadLineModel("road_line_white_chevron{}", "road_line_white_chevron"),
        new RoadLineModel("road_line_white_side{}_thick", "road_line_white_side_thick"),
    };
    public static final RoadLineModel[] WHITE_SIDE_MERGE = new RoadLineModel[] {
        new RoadLineModel("road_line_white_side{}_merge1", "road_line_side_merge1"),
        new RoadLineModel("road_line_white_side{}_merge2", "road_line_side_merge2"),
        new RoadLineModel("road_line_white_side{}_merge1f", "road_line_side_merge1f"),
        new RoadLineModel("road_line_white_side{}_merge2f", "road_line_side_merge2f"),
        new RoadLineModel("road_line_white_side{}_merge_corner", "road_line_side_merge_corner"),
        new RoadLineModel("road_line_white_side{}_merge_cornerf", "road_line_side_merge_cornerf"),
        new RoadLineModel("road_line_white_side{}_merge_double", "road_line_side_merge_double"),
    };

    public static final RoadLineModel[] YELLOW_CENTER = new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_center{}", "road_line_center"),
        new RoadLineModel("road_line_yellow_double{}", "road_line_double"),
        new RoadLineModel("road_line_yellow_double{}_single", "road_line_double_single"),
        new RoadLineModel("road_line_yellow_double{}_single_reflector", "road_line_double_single_reflector"),
        new RoadLineModel("road_line_yellow_center{}_reflector", "road_line_center_reflector")
    };
    public static final RoadLineModel[] YELLOW_SIDE = new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side{}", "road_line_side"),
        new RoadLineModel("road_line_yellow_side{}_angle", "road_line_side_angle"),
        new RoadLineModel("road_line_yellow_side{}_angle_end", "road_line_side_angle_end"),
        new RoadLineModel("road_line_yellow_chevron{}", "road_line_yellow_chevron"),
    };
    public static final RoadLineModel[] YELLOW_SIDE_MERGE = new RoadLineModel[] {
        new RoadLineModel("road_line_yellow_side{}_merge1", "road_line_side_merge1"),
        new RoadLineModel("road_line_yellow_side{}_merge2", "road_line_side_merge2"),
        new RoadLineModel("road_line_yellow_side{}_merge1f", "road_line_side_merge1f"),
        new RoadLineModel("road_line_yellow_side{}_merge2f", "road_line_side_merge2f"),
        new RoadLineModel("road_line_yellow_side{}_merge_corner", "road_line_side_merge_corner"),
        new RoadLineModel("road_line_yellow_side{}_merge_cornerf", "road_line_side_merge_cornerf"),
        new RoadLineModel("road_line_yellow_side{}_merge_double", "road_line_side_merge_double"),
    };

    public static final RoadLineModel[] ARROW = new RoadLineModel[] {
        new RoadLineModel("road_arrow{}_straight", "road_arrow_straight"),
        new RoadLineModel("road_arrow{}_left", "road_arrow_left"),
        new RoadLineModel("road_arrow{}_right", "road_arrow_right"),
        new RoadLineModel("road_arrow{}_straight_left", "road_arrow_straight_left"),
        new RoadLineModel("road_arrow{}_straight_right", "road_arrow_straight_right"),
        new RoadLineModel("road_arrow{}_left_right", "road_arrow_left_right"),
        new RoadLineModel("road_arrow{}_straight_left_right", "road_arrow_straight_left_right"),
    };
    public static final RoadLineModel[] STOP_BAR = new RoadLineModel[] {
        new RoadLineModel("road_stop_bar{}_center", "road_stop_bar_center"),
        new RoadLineModel("road_stop_bar{}_left", "road_stop_bar_left"),
        new RoadLineModel("road_stop_bar{}_right", "road_stop_bar_right"),
        new RoadLineModel("road_stop_bar{}_left_yellow", "road_stop_bar_left_yellow"),
    };
    

}
