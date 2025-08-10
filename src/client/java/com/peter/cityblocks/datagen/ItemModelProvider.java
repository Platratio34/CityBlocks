package com.peter.cityblocks.datagen;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.peter.cityblocks.items.Items;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;

public class ItemModelProvider extends FabricModelProvider {

    public static final Logger LOGGER = LoggerFactory.getLogger("city-blocks-model-generator");

    public ItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    private Identifier blockTextureId(String id) {
        return CityBlocks.identifier("block/" + id);
    }

    private static final Identifier ROAD_LINE_BASE_MODEL = CityBlocks.identifier("block/road_line");
    private static final Identifier ROAD_LINE_BASE_EAST_MODEL = CityBlocks.identifier("block/road_line_east");
    private static final Identifier ROAD_LINE_BASE_SOUTH_MODEL = CityBlocks.identifier("block/road_line_south");
    private static final Identifier ROAD_LINE_BASE_WEST_MODEL = CityBlocks.identifier("block/road_line_west");
    private static final TextureKey OVERLAY_TEXTURE_KEY = TextureKey.of("overlay");
    private static final TextureKey TOP_TEXTURE_KEY = TextureKey.of("top");
    private static final TextureKey BASE_TEXTURE_KEY = TextureKey.of("base");

    private Identifier blockModelId(VariantBlock block) {
        return CityBlocks.identifier("block/" + block.name);
    }

    private Identifier blockModelId(VariantBlock block, String variant, boolean dash) {
        return CityBlocks.identifier("block/" + block.name + (dash ? "_"+ variant : variant) );
    }

    private Identifier blockModelId(String name) {
        return CityBlocks.identifier("block/" + name);
    }

    private void registerBlockItemModel(BlockStateModelGenerator blockStateModelGenerator, VariantBlock block) {
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(block));
    }

    private void registerBlockItemModelV(BlockStateModelGenerator blockStateModelGenerator, VariantBlock block, String variant) {
        registerBlockItemModelV(blockStateModelGenerator, block, variant, true);
    }

    private void registerBlockItemModelV(BlockStateModelGenerator blockStateModelGenerator, VariantBlock block, String variant, boolean dash) {
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(block, variant, dash));
    }

    private void registerBlockItemModel(BlockStateModelGenerator blockStateModelGenerator, Block block, String name) {
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(name));
    }

    private Identifier roadBlockModelId(String name, RoadType roadType) {
        return CityBlocks.identifier("block/" + name.replace("{}", roadType.extension));
    }

    private static final Model ROAD_LINE_MODEL = new Model(Optional.of(ROAD_LINE_BASE_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_EAST_MODEL = new Model(Optional.of(ROAD_LINE_BASE_EAST_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_SOUTH_MODEL = new Model(Optional.of(ROAD_LINE_BASE_SOUTH_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_WEST_MODEL = new Model(Optional.of(ROAD_LINE_BASE_WEST_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private void registerRoadLineBlockSubModel(BlockStateModelGenerator generator, String id, String overlay,
            RoadType roadType) {
        TextureMap map = new TextureMap().put(OVERLAY_TEXTURE_KEY, blockTextureId(overlay));
        map.put(TOP_TEXTURE_KEY, roadType.topTexture);
        map.put(BASE_TEXTURE_KEY, roadType.baseTexture);

        ROAD_LINE_MODEL.upload(roadBlockModelId(id, roadType), map, generator.modelCollector);
        ROAD_LINE_EAST_MODEL.upload(roadBlockModelId(id+"_east", roadType), map, generator.modelCollector);
        ROAD_LINE_SOUTH_MODEL.upload(roadBlockModelId(id+"_south", roadType), map, generator.modelCollector);
        ROAD_LINE_WEST_MODEL.upload(roadBlockModelId(id+"_west", roadType), map, generator.modelCollector);
    }
    
    private void registerRoadLineBlock(BlockStateModelGenerator generator, RoadLineBlock block) {
        var map = BlockStateVariantMap.models(RoadLineBlock.FACING, block.variant);
        for (int i = 0; i < block.models.length; i++) {
            RoadLineModel model = block.models[i];
            map.register(Direction.NORTH, i,
                    BlockStateModelGenerator.createWeightedVariant(roadBlockModelId(model.model(), block.roadType)));
            map.register(Direction.EAST, i, BlockStateModelGenerator
                    .createWeightedVariant(roadBlockModelId(model.model() + "_east", block.roadType)));
            map.register(Direction.SOUTH, i, BlockStateModelGenerator
                    .createWeightedVariant(roadBlockModelId(model.model() + "_south", block.roadType)));
            map.register(Direction.WEST, i, BlockStateModelGenerator
                    .createWeightedVariant(roadBlockModelId(model.model() + "_west", block.roadType)));
            registerRoadLineBlockSubModel(generator, model.model(), model.overlay(), block.roadType);
        }
        generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(map));
    }

    private void registerVariantBlockStates(BlockStateModelGenerator generator, VariantBlock block) {
        var map = BlockStateVariantMap.models(VariantBlock.FACING, block.variant);
        for (int v = 0; v < block.variants; v++) {
            map.register(Direction.NORTH, v, BlockStateModelGenerator.createWeightedVariant(block.modelVariant(v, Direction.NORTH)));
            map.register(Direction.EAST, v, BlockStateModelGenerator.createWeightedVariant(block.modelVariant(v, Direction.EAST)).apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R90)));
            map.register(Direction.SOUTH, v, BlockStateModelGenerator.createWeightedVariant(block.modelVariant(v, Direction.SOUTH)).apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R180)));
            map.register(Direction.WEST, v, BlockStateModelGenerator.createWeightedVariant(block.modelVariant(v, Direction.WEST)).apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R270)));
        }
        generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(map));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

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
        
        registerBlockItemModel(generator, Blocks.SIGNAL_HEAD_BLOCK, SignalHeadBlock.NAME +"_3");
        registerBlockItemModel(generator, Blocks.PEDESTRIAN_SIGNAL_BLOCK, PedestrianSignalBlock.NAME);
        registerBlockItemModel(generator, Blocks.SIGNAL_CONTROLLER_BLOCK, SignalControllerBlock.NAME);
        registerBlockItemModel(generator, Blocks.LARGE_POST, LargePostBlock.NAME + "_v_2");

        registerBlockItemModel(generator, Blocks.STREET_SIGN_BLOCK, StreetSignBlock.NAME+"_1_b");

        registerBlockItemModelV(generator, Blocks.CELLING_LIGHT_BLOCK, "0");
        registerVariantBlockStates(generator, Blocks.CELLING_LIGHT_BLOCK);
        registerBlockItemModel(generator, Blocks.EXIT_SIGN_BLOCK);
        registerBlockItemModel(generator, Blocks.BUILDING_SIGN_BLOCK, BuildingSignBlock.NAME + "_address");
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        LOGGER.info("Generating item models");
        itemModelGenerator.register(Items.VARIANT_SWITCHER_ITEM, Models.GENERATED);
        itemModelGenerator.register(Items.SIGNAL_LINKER_ITEM, Models.GENERATED);
        itemModelGenerator.register(Items.SLIDING_DOOR_BLOCK_ITEM, Models.GENERATED);
    }

}
