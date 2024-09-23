package com.peter.cityblocks;

import com.peter.cityblocks.blockRenderers.RoadLineBlockRenderer;
import com.peter.cityblocks.blocks.Blocks;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class CityBlocksModelLoader implements ModelLoadingPlugin {

    private static final Identifier POLISHED_ANDESITE = Identifier.ofVanilla("block/polished_andesite");

    private static final String[] LINE_WHITE_SIDE_TEXTURES = new String[] {
        "road_line_white_side", "road_line_white_side_angle", "road_line_white_side_angle_end", "road_line_white_chevron", "road_line_white_side_thick"
    };
    private static final String[] LINE_WHITE_CENTER_TEXTURES = new String[] {
        "road_line_white_center", "road_line_white_center_reflector"
    };
    private static final String[] LINE_WHITE_SIDE_MERGE_TEXTURES = new String[] {
        "road_line_white_side_merge1", "road_line_white_side_merge2", "road_line_white_side_merge1f", "road_line_white_side_merge2f", "road_line_white_side_merge_corner", "road_line_white_side_merge_cornerf", "road_line_white_side_merge_double"
    };

    private static final String[] LINE_YELLOW_SIDE_TEXTURES = new String[] {
        "road_line_yellow_side", "road_line_yellow_side_angle", "road_line_yellow_side_angle_end", "road_line_yellow_chevron"
    };
    private static final String[] LINE_YELLOW_CENTER_TEXTURES = new String[] {
        "road_line_yellow_center", "road_line_yellow_double", "road_line_yellow_double_single", "road_line_yellow_double_single_reflector", "road_line_yellow_center_reflector"
    };
    private static final String[] LINE_YELLOW_SIDE_MERGE_TEXTURES = new String[] {
        "road_line_yellow_side_merge1", "road_line_yellow_side_merge2", "road_line_yellow_side_merge1f", "road_line_yellow_side_merge2f", "road_line_yellow_side_merge_corner", "road_line_yellow_side_merge_cornerf", "road_line_yellow_side_merge_double"
    };

    private static final String[] ARROW_TEXTURES = new String[] {
        "road_arrow_straight", "road_arrow_left", "road_arrow_right", "road_arrow_straight_left", "road_arrow_straight_right", "road_arrow_left_right", "road_arrow_straight_left_right"
    };

    private static final String[] STOP_BAR_TEXTURES = new String[] {
        "road_stop_bar_center", "road_stop_bar_left", "road_stop_bar_right", "road_stop_bar_left_yellow"
    };

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            ModelIdentifier modelId = context.topLevelId();
            if (modelId == null)
                return original;
            if (!modelId.id().getNamespace().equals(CityBlocks.MOD_ID))
                return original;
            
            if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_SIDE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_SIDE_BLOCK, LINE_WHITE_SIDE_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK, LINE_WHITE_SIDE_TEXTURES,
                        modelId.getVariant()).setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_CENTER_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_CENTER_BLOCK, LINE_WHITE_CENTER_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK,
                        LINE_WHITE_CENTER_TEXTURES, modelId.getVariant()).setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK, LINE_WHITE_SIDE_MERGE_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK, LINE_WHITE_SIDE_MERGE_TEXTURES, modelId.getVariant()).setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK, LINE_YELLOW_SIDE_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK, LINE_YELLOW_SIDE_TEXTURES,
                        modelId.getVariant()).setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK, LINE_YELLOW_CENTER_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK,
                        LINE_YELLOW_CENTER_TEXTURES, modelId.getVariant()).setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK, LINE_YELLOW_SIDE_MERGE_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK,
                        LINE_YELLOW_SIDE_MERGE_TEXTURES, modelId.getVariant())
                        .setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_ARROW_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_ARROW_BLOCK, ARROW_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_ARROW_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_ARROW_ANDESITE_BLOCK, ARROW_TEXTURES, modelId.getVariant())
                        .setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            else if (modelId.id().equals(Blocks.ROAD_STOP_BAR_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_STOP_BAR_BLOCK, STOP_BAR_TEXTURES, modelId.getVariant());
            } else if (modelId.id().equals(Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK.id)) {
                return new RoadLineBlockRenderer(Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK, STOP_BAR_TEXTURES, modelId.getVariant())
                        .setBase(POLISHED_ANDESITE, POLISHED_ANDESITE);
            }
            return original;
        });
    }

}
