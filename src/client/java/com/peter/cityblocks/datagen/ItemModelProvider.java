package com.peter.cityblocks.datagen;

import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mojang.math.Quadrant;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.LargePostBlock;
import com.peter.cityblocks.blocks.RoadLineBlock;
import com.peter.cityblocks.blocks.RoadType;
import com.peter.cityblocks.blocks.RoadLineBlock.RoadLineModel;
import com.peter.cityblocks.blocks.VariantBlock;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlock;
import com.peter.cityblocks.blocks.signal.SignalControllerBlock;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import com.peter.cityblocks.ccextended.CardReaderBlock;
import com.peter.cityblocks.items.Items;
import com.peter.cityblocks.items.Keycard;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ItemModelProvider extends FabricModelProvider {

    public static final Logger LOGGER = LoggerFactory.getLogger("city-blocks-model-generator");

    public ItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    private ResourceLocation blockTextureId(String id) {
        return CityBlocks.identifier("block/" + id);
    }

    private static final ResourceLocation ROAD_LINE_BASE_MODEL = CityBlocks.identifier("block/road_line");
    private static final ResourceLocation ROAD_LINE_BASE_EAST_MODEL = CityBlocks.identifier("block/road_line_east");
    private static final ResourceLocation ROAD_LINE_BASE_SOUTH_MODEL = CityBlocks.identifier("block/road_line_south");
    private static final ResourceLocation ROAD_LINE_BASE_WEST_MODEL = CityBlocks.identifier("block/road_line_west");
    private static final TextureSlot OVERLAY_TEXTURE_KEY = TextureSlot.create("overlay");
    private static final TextureSlot TOP_TEXTURE_KEY = TextureSlot.create("top");
    private static final TextureSlot BASE_TEXTURE_KEY = TextureSlot.create("base");
    private static final TextureSlot LINE_TEXTURE_KEY = TextureSlot.create("line");

    private static ResourceLocation blockModelId(VariantBlock block) {
        return CityBlocks.identifier("block/" + block.name);
    }

    private static ResourceLocation blockModelId(VariantBlock block, String variant, boolean dash) {
        return CityBlocks.identifier("block/" + block.name + (dash ? "_"+ variant : variant) );
    }

    private static ResourceLocation blockModelId(String name) {
        return CityBlocks.identifier("block/" + name);
    }

    private void registerBlockItemModel(BlockModelGenerators blockStateModelGenerator, VariantBlock block) {
        blockStateModelGenerator.registerSimpleItemModel(block, blockModelId(block));
    }

    private void registerBlockItemModelV(BlockModelGenerators blockStateModelGenerator, VariantBlock block, String variant) {
        registerBlockItemModelV(blockStateModelGenerator, block, variant, true);
    }

    private void registerBlockItemModelV(BlockModelGenerators blockStateModelGenerator, VariantBlock block, String variant, boolean dash) {
        blockStateModelGenerator.registerSimpleItemModel(block, blockModelId(block, variant, dash));
    }

    private void registerBlockItemModel(BlockModelGenerators blockStateModelGenerator, Block block, String name) {
        blockStateModelGenerator.registerSimpleItemModel(block, blockModelId(name));
    }

    private ResourceLocation roadBlockModelId(String name, RoadType roadType) {
        return CityBlocks.identifier("block/" + name.replace("{}", roadType.extension));
    }

    private static final ModelTemplate ROAD_LINE_MODEL = new ModelTemplate(Optional.of(ROAD_LINE_BASE_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final ModelTemplate ROAD_LINE_EAST_MODEL = new ModelTemplate(Optional.of(ROAD_LINE_BASE_EAST_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final ModelTemplate ROAD_LINE_SOUTH_MODEL = new ModelTemplate(Optional.of(ROAD_LINE_BASE_SOUTH_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final ModelTemplate ROAD_LINE_WEST_MODEL = new ModelTemplate(Optional.of(ROAD_LINE_BASE_WEST_MODEL),
            Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final HashMap<String, ModelTemplate> ROAD_LINE_MODELS = new HashMap<>();

    private static final ModelTemplate createRoadLineModel(ResourceLocation id) {
        return new ModelTemplate(Optional.of(id), Optional.empty(), LINE_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    }
    static {
        ROAD_LINE_MODELS.put("road_line_side", createRoadLineModel(blockModelId("road_line_side")));
        ROAD_LINE_MODELS.put("road_line_side_angle", createRoadLineModel(blockModelId("road_line_angle")));
        ROAD_LINE_MODELS.put("road_line_side_angle_end", createRoadLineModel(blockModelId("road_line_angle_end")));
        
        ROAD_LINE_MODELS.put("road_line_center", createRoadLineModel(blockModelId("road_line_center")));
        ROAD_LINE_MODELS.put("road_line_center_reflector", createRoadLineModel(blockModelId("road_line_dot")));
        ROAD_LINE_MODELS.put("road_line_double", createRoadLineModel(blockModelId("road_line_double")));
        ROAD_LINE_MODELS.put("road_line_double_single", createRoadLineModel(blockModelId("road_line_double_single")));
        ROAD_LINE_MODELS.put("road_line_double_single_reflector", createRoadLineModel(blockModelId("road_line_double_single_dot")));
        ROAD_LINE_MODELS.put("road_line_double_reflector", createRoadLineModel(blockModelId("road_line_double_dot")));
        
        ROAD_LINE_MODELS.put("road_line_side_merge1", createRoadLineModel(blockModelId("road_line_merge_1")));
        ROAD_LINE_MODELS.put("road_line_side_merge1f", createRoadLineModel(blockModelId("road_line_merge_1f")));
        ROAD_LINE_MODELS.put("road_line_side_merge2", createRoadLineModel(blockModelId("road_line_merge_2")));
        ROAD_LINE_MODELS.put("road_line_side_merge2f", createRoadLineModel(blockModelId("road_line_merge_2f")));
        ROAD_LINE_MODELS.put("road_line_side_merge_corner", createRoadLineModel(blockModelId("road_line_merge_corner")));
        ROAD_LINE_MODELS.put("road_line_side_merge_cornerf", createRoadLineModel(blockModelId("road_line_merge_cornerf")));
        ROAD_LINE_MODELS.put("road_line_side_merge_double", createRoadLineModel(blockModelId("road_line_merge_double")));
    }
    private void registerRoadLineBlockSubModel(BlockModelGenerators generator, String id, String overlay,
            RoadType roadType, ResourceLocation color) {
        TextureMapping map = new TextureMapping();
        map.put(TOP_TEXTURE_KEY, roadType.topTexture);
        map.put(BASE_TEXTURE_KEY, roadType.baseTexture);

        // ROAD_LINE_MODEL.upload(roadBlockModelId(id, roadType), map, generator.modelCollector);
        // ROAD_LINE_EAST_MODEL.upload(roadBlockModelId(id+"_east", roadType), map, generator.modelCollector);
        // ROAD_LINE_SOUTH_MODEL.upload(roadBlockModelId(id+"_south", roadType), map, generator.modelCollector);
        // ROAD_LINE_WEST_MODEL.upload(roadBlockModelId(id+"_west", roadType), map, generator.modelCollector);
        if (ROAD_LINE_MODELS.containsKey(overlay)) {
            map.put(LINE_TEXTURE_KEY, color);
            ROAD_LINE_MODELS.get(overlay).create(roadBlockModelId(id, roadType), map, generator.modelOutput);
        } else {
            map.put(OVERLAY_TEXTURE_KEY, blockTextureId(overlay));
            ROAD_LINE_MODEL.create(roadBlockModelId(id, roadType), map, generator.modelOutput);
            ROAD_LINE_EAST_MODEL.create(roadBlockModelId(id+"_east", roadType), map, generator.modelOutput);
            ROAD_LINE_SOUTH_MODEL.create(roadBlockModelId(id+"_south", roadType), map, generator.modelOutput);
            ROAD_LINE_WEST_MODEL.create(roadBlockModelId(id+"_west", roadType), map, generator.modelOutput);
        }
    }
    
    private void registerRoadLineBlock(BlockModelGenerators generator, RoadLineBlock block) {
        var map = PropertyDispatch.initial(RoadLineBlock.FACING, block.variant);
        for (int i = 0; i < block.models.length; i++) {
            RoadLineModel model = block.models[i];
            boolean isTextured = !ROAD_LINE_MODELS.containsKey(model.overlay());
            map.select(Direction.NORTH, i,
                    BlockModelGenerators.plainVariant(roadBlockModelId(model.model(), block.roadType)).with(VariantMutator.UV_LOCK.withValue(true)));
            if (isTextured) {
                map.select(Direction.EAST, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model() + "_east", block.roadType)));
                map.select(Direction.SOUTH, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model() + "_south", block.roadType)));
                map.select(Direction.WEST, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model() + "_west", block.roadType)));
            } else  {
                map.select(Direction.EAST, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model(), block.roadType))
                        .with(VariantMutator.UV_LOCK.withValue(true))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R90)));
                map.select(Direction.SOUTH, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model(), block.roadType))
                        .with(VariantMutator.UV_LOCK.withValue(true))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R180)));
                map.select(Direction.WEST, i, BlockModelGenerators
                        .plainVariant(roadBlockModelId(model.model(), block.roadType))
                        .with(VariantMutator.UV_LOCK.withValue(true))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R270)));
            }
            registerRoadLineBlockSubModel(generator, model.model(), model.overlay(), block.roadType, block.color);
        }
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(map));
    }

    private void registerVariantBlockStates(BlockModelGenerators generator, VariantBlock block) {
        var map = PropertyDispatch.initial(VariantBlock.FACING, block.variant);
        for (int v = 0; v < block.variants; v++) {
            map.select(Direction.NORTH, v, BlockModelGenerators.plainVariant(block.modelVariant(v, Direction.NORTH)));
            map.select(Direction.EAST, v, BlockModelGenerators.plainVariant(block.modelVariant(v, Direction.EAST)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)));
            map.select(Direction.SOUTH, v, BlockModelGenerators.plainVariant(block.modelVariant(v, Direction.SOUTH)).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)));
            map.select(Direction.WEST, v, BlockModelGenerators.plainVariant(block.modelVariant(v, Direction.WEST)).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)));
        }
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(map));
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

        LOGGER.info("Generating road line block/block-item models:");
        for (RoadLineBlock block : RoadLineBlock.ROAD_LINE_BLOCKS.values()) {
            LOGGER.info("- {}", block.name);
            registerRoadLineBlock(generator, block);
            registerBlockItemModel(generator, block, block.itemModelId);
        }
        
        LOGGER.info("Generating block-item models");
        registerBlockItemModelV(generator, Blocks.CABLE_BARRIER_BLOCK, "post_middle");
        registerVariantBlockStates(generator, Blocks.CABLE_BARRIER_BLOCK);
        registerBlockItemModel(generator, Blocks.CONCRETE_BARRIER_BLOCK);
        registerVariantBlockStates(generator, Blocks.CONCRETE_BARRIER_BLOCK);
        registerBlockItemModelV(generator, Blocks.CRASH_BARRIER_BLOCK, "straight");
        registerVariantBlockStates(generator, Blocks.CRASH_BARRIER_BLOCK);
        registerBlockItemModel(generator, Blocks.TRAFFIC_CONE_BLOCK);
        registerVariantBlockStates(generator, Blocks.TRAFFIC_CONE_BLOCK);
        
        registerBlockItemModel(generator, Blocks.SIGN_POST_BLOCK, "sign_post_post");
        registerBlockItemModel(generator, Blocks.SIGN_POST_SIGN_1_BLOCK);
        registerBlockItemModel(generator, Blocks.SIGN_POST_SIGN_1_SPD_BLOCK);
        registerBlockItemModel(generator, Blocks.SIGN_POST_SIGN_2_BLOCK);
        registerBlockItemModel(generator, Blocks.SIGN_POST_OFFSET_BLOCK);
        
        registerBlockItemModel(generator, Blocks.SIGNAL_HEAD_BLOCK, SignalHeadBlock.NAME +"_3");
        registerBlockItemModel(generator, Blocks.PEDESTRIAN_SIGNAL_BLOCK, PedestrianSignalBlock.NAME);
        registerBlockItemModel(generator, Blocks.SIGNAL_CONTROLLER_BLOCK, SignalControllerBlock.NAME);
        registerBlockItemModel(generator, Blocks.LARGE_POST, LargePostBlock.NAME + "_v_2");

        registerBlockItemModel(generator, Blocks.STREET_SIGN_BLOCK, StreetSignBlock.NAME+"_1_b");

        registerBlockItemModelV(generator, Blocks.CELLING_LIGHT_BLOCK, "0");
        registerVariantBlockStates(generator, Blocks.CELLING_LIGHT_BLOCK);
        registerBlockItemModel(generator, Blocks.EXIT_SIGN_BLOCK);
        registerBlockItemModel(generator, Blocks.BUILDING_SIGN_BLOCK, BuildingSignBlock.NAME + "_address");

        registerBlockItemModel(generator, Blocks.CARD_READER_BLOCK, CardReaderBlock.NAME);
        
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        LOGGER.info("Generating item models");
        itemModelGenerator.generateFlatItem(Items.VARIANT_SWITCHER_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(Items.SIGNAL_LINKER_ITEM, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(Items.SLIDING_DOOR_BLOCK_ITEM, ModelTemplates.FLAT_ITEM);

        for (Keycard card : Keycard.ITEMS) {
            itemModelGenerator.generateFlatItem(card, ModelTemplates.FLAT_ITEM);
        }
    }

}
