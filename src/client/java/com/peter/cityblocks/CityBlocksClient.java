package com.peter.cityblocks;

import com.peter.cityblocks.blockRenderers.CustomSignBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.PedestrianSignalBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.SignalHeadBlockEntityRenderer;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.RoadLineBlock;
import com.peter.cityblocks.blocks.TooltipedItem;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import com.peter.cityblocks.gui.CityBlocksScreens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;

public class CityBlocksClient implements ClientModInitializer {
	@Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(Blocks.SIGN_POST_BLOCK, ChunkSectionLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.STREET_SIGN_BLOCK, ChunkSectionLayer.CUTOUT);

        for (RoadLineBlock block : RoadLineBlock.ROAD_LINE_BLOCKS.values()) {
            BlockRenderLayerMap.putBlock(block, ChunkSectionLayer.CUTOUT);
        }

        BlockRenderLayerMap.putBlock(Blocks.SLIDING_DOOR_BLOCK, ChunkSectionLayer.CUTOUT);

        BlockEntityRenderers.register(SignalHeadBlockEntity.BLOCK_ENTITY_TYPE,
                SignalHeadBlockEntityRenderer::new);

        BlockEntityRenderers.register(PedestrianSignalBlockEntity.BLOCK_ENTITY_TYPE,
                PedestrianSignalBlockEntityRenderer::new);

        BlockEntityRenderers.register(StreetSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        BlockEntityRenderers.register(BuildingSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        
        CityBlocksScreens.register();

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            Item i = itemStack.getItem();
            CustomData comp = itemStack.getComponents().get(DataComponents.BLOCK_ENTITY_DATA);
            CompoundTag entityData = (comp != null) ? comp.copyTag() : null;
            if (i instanceof TooltipedItem item) {
                item.addTooltip(itemStack, tooltipContext, tooltipType, list);
                if(entityData != null)
                    item.addTooltipEntity(itemStack, tooltipContext, tooltipType, list, entityData);
            } else if(i instanceof BlockItem bItem) {
                if (bItem.getBlock() instanceof TooltipedItem block) {
                    block.addTooltip(itemStack, tooltipContext, tooltipType, list);
                    if(entityData != null)
                        block.addTooltipEntity(itemStack, tooltipContext, tooltipType, list, entityData);
                }
            }
        });

        CityBlocks.LOGGER.info("City Blocks Client initialized");
    }
    
    public static Material blockTexture(String texture) {
        return new Material(TextureAtlas.LOCATION_BLOCKS, CityBlocks.identifier("block/" + texture));
    }
}