package com.peter.cityblocks;

import com.peter.cityblocks.blockRenderers.CustomSignBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.PedestrianSignalBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.SignalHeadBlockEntityRenderer;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.TooltipedItem;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import com.peter.cityblocks.gui.CityBlocksScreens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;

public class CityBlocksClient implements ClientModInitializer {
	@Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(Blocks.SIGN_POST_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.STREET_SIGN_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_CENTER_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_SIDE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_CENTER_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_SIDE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_BLOCK, BlockRenderLayer.CUTOUT);
        
        BlockRenderLayerMap.putBlock(Blocks.ROAD_ARROW_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_STOP_BAR_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_CENTER_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_SIDE_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_WHITE_SIDE_MERGE_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);

        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_CENTER_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_SIDE_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_LINE_YELLOW_SIDE_MERGE_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        
        BlockRenderLayerMap.putBlock(Blocks.ROAD_ARROW_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(Blocks.ROAD_STOP_BAR_ANDESITE_BLOCK, BlockRenderLayer.CUTOUT);

        BlockEntityRendererFactories.register(SignalHeadBlockEntity.BLOCK_ENTITY_TYPE,
                SignalHeadBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(PedestrianSignalBlockEntity.BLOCK_ENTITY_TYPE,
                PedestrianSignalBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(StreetSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BuildingSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        
        CityBlocksScreens.register();

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            Item i = itemStack.getItem();
            NbtComponent comp = itemStack.getComponents().get(DataComponentTypes.BLOCK_ENTITY_DATA);
            NbtCompound entityData = (comp != null) ? comp.copyNbt() : null;
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
    
    public static SpriteIdentifier blockTexture(String texture) {
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, CityBlocks.identifier("block/" + texture));
    }
}