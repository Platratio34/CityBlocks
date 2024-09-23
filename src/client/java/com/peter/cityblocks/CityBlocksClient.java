package com.peter.cityblocks;

import com.peter.cityblocks.blockRenderers.CustomSignBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.PedestrianSignalBlockEntityRenderer;
import com.peter.cityblocks.blockRenderers.SignalHeadBlockEntityRenderer;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.blocks.signs.BuildingSignBlock;
import com.peter.cityblocks.blocks.signs.StreetSignBlock;
import com.peter.cityblocks.gui.CityBlocksScreens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;

public class CityBlocksClient implements ClientModInitializer {
	@Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.SIGN_POST_BLOCK, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.STREET_SIGN_BLOCK, RenderLayer.getCutout());

        ModelLoadingPlugin.register(new CityBlocksModelLoader());

        BlockEntityRendererFactories.register(SignalHeadBlockEntity.BLOCK_ENTITY_TYPE,
                SignalHeadBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(PedestrianSignalBlockEntity.BLOCK_ENTITY_TYPE,
                PedestrianSignalBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(StreetSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BuildingSignBlock.BLOCK_ENTITY_TYPE,
                CustomSignBlockEntityRenderer::new);
        
        CityBlocksScreens.register();

        CityBlocks.LOGGER.info("City Blocks Client initialized");
    }
    
    public static SpriteIdentifier blockTexture(String texture) {
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, CityBlocks.identifier("block/" + texture));
    }
}