package com.peter.cityblocks.datagen;

import java.util.Optional;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.LargePostBlock;
import com.peter.cityblocks.blocks.RoadLineBlock;
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
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.util.Identifier;

public class ItemModelProvider extends FabricModelProvider {

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
    private static final Identifier ANDESITE_TEXTURE_ID = Identifier.ofVanilla("block/polished_andesite");
    private static final Identifier BLACKSTONE_TOP_TEXTURE_ID = Identifier.ofVanilla("block/blackstone_top");
    private static final Identifier BLACKSTONE_TEXTURE_ID = Identifier.ofVanilla("block/blackstone");

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
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(block, variant, true));
    }

    private void registerBlockItemModelV(BlockStateModelGenerator blockStateModelGenerator, VariantBlock block, String variant, boolean dash) {
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(block, variant, dash));
    }

    private void registerBlockItemModel(BlockStateModelGenerator blockStateModelGenerator, Block block, String name) {
        blockStateModelGenerator.registerParentedItemModel(block, blockModelId(name));
    }

    private static final Model ROAD_LINE_MODEL = new Model(Optional.of(ROAD_LINE_BASE_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_EAST_MODEL = new Model(Optional.of(ROAD_LINE_BASE_EAST_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_SOUTH_MODEL = new Model(Optional.of(ROAD_LINE_BASE_SOUTH_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private static final Model ROAD_LINE_WEST_MODEL = new Model(Optional.of(ROAD_LINE_BASE_WEST_MODEL), Optional.empty(), OVERLAY_TEXTURE_KEY, TOP_TEXTURE_KEY, BASE_TEXTURE_KEY);
    private void registerRoadLineBlockSubModel(BlockStateModelGenerator generator, String id, String overlay,
            boolean andesite) {
        TextureMap map = new TextureMap().put(OVERLAY_TEXTURE_KEY, blockTextureId(overlay));
        if (andesite) {
            map.put(TOP_TEXTURE_KEY, ANDESITE_TEXTURE_ID);
            map.put(BASE_TEXTURE_KEY, ANDESITE_TEXTURE_ID);
        } else {
            map.put(TOP_TEXTURE_KEY, BLACKSTONE_TOP_TEXTURE_ID);
            map.put(BASE_TEXTURE_KEY, BLACKSTONE_TEXTURE_ID);
        }

        ROAD_LINE_MODEL.upload(blockModelId(id), map, generator.modelCollector);
        ROAD_LINE_EAST_MODEL.upload(blockModelId(id+"_east"), map, generator.modelCollector);
        ROAD_LINE_SOUTH_MODEL.upload(blockModelId(id+"_south"), map, generator.modelCollector);
        ROAD_LINE_WEST_MODEL.upload(blockModelId(id+"_west"), map, generator.modelCollector);
    }
    
    private void registerRoadLineBlock(BlockStateModelGenerator generator, RoadLineBlock block) {
        for (RoadLineModel model : block.models) {
            registerRoadLineBlockSubModel(generator, model.model(), model.overlay(), block.andesite);
        }
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        registerBlockItemModel(generator, Blocks.ROAD_LINE_WHITE_CENTER_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_WHITE_SIDE_BLOCK);
        registerBlockItemModelV(generator, Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK, "1", false);
        
        registerBlockItemModel(generator, Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK);
        registerBlockItemModelV(generator, Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK, "1", false);
        
        registerBlockItemModelV(generator, Blocks.ROAD_ARROW_BLOCK, "straight");
        registerBlockItemModelV(generator, Blocks.ROAD_STOP_BAR_BLOCK, "center");
        
        registerBlockItemModel(generator, Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK, "road_line_white_side_andesite_merge1");
        
        registerBlockItemModel(generator, Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK);
        registerBlockItemModel(generator, Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK, "road_line_yellow_side_andesite_merge1");
        
        registerBlockItemModelV(generator, Blocks.ROAD_ARROW_ANDESITE_BLOCK, "straight");
        registerBlockItemModelV(generator, Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK, "center");
        
        registerBlockItemModelV(generator, Blocks.CABLE_BARRIER_BLOCK, "post_middle");
        registerBlockItemModel(generator, Blocks.CONCRETE_BARRIER_BLOCK);
        registerBlockItemModelV(generator, Blocks.CRASH_BARRIER_BLOCK, "straight");
        
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
        registerBlockItemModel(generator, Blocks.EXIT_SIGN_BLOCK);
        registerBlockItemModel(generator, Blocks.BUILDING_SIGN_BLOCK, BuildingSignBlock.NAME + "_address");


        
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_CENTER_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_SIDE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK);

        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK);
        
        registerRoadLineBlock(generator, Blocks.ROAD_ARROW_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_STOP_BAR_BLOCK);
        
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK);
        
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK);
        
        registerRoadLineBlock(generator, Blocks.ROAD_ARROW_ANDESITE_BLOCK);
        registerRoadLineBlock(generator, Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Items.VARIANT_SWITCHER_ITEM, Models.GENERATED);
        itemModelGenerator.register(Items.SIGNAL_LINKER_ITEM, Models.GENERATED);
        itemModelGenerator.register(Items.SLIDING_DOOR_BLOCK_ITEM, Models.GENERATED);
    }

}
