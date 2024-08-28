package com.peter.blockRenderers;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.peter.CityBlocksClient;
import com.peter.blocks.VariantBlock;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

public class RoadLineBlockRenderer implements UnbakedModel, BakedModel {

    private final VariantBlock block;

    private final SpriteIdentifier[] textureIds;
    private final Sprite[] sprites;
    private final int texture;
    private final Direction facing;

    private SpriteIdentifier SIDE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/blackstone"));
    private Sprite SIDE_SPRITE;
    private SpriteIdentifier TOP_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/blackstone_top"));
    private Sprite TOP_SPRITE;

    private Mesh mesh;

    private final boolean isInventory;

    public RoadLineBlockRenderer(VariantBlock block, SpriteIdentifier[] textureIds, String variantString) {
        this.block = block;
        this.textureIds = textureIds;
        sprites = new Sprite[textureIds.length];
        isInventory = variantString.equals("inventory");
        texture = getVariantId(variantString);
        facing = getFacing(variantString);
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public RoadLineBlockRenderer(VariantBlock block, String[] textures, String variantString) {
        this.block = block;
        textureIds = new SpriteIdentifier[textures.length];
        sprites = new Sprite[textureIds.length];

        for (int i = 0; i < textures.length; i++) {
            textureIds[i] = CityBlocksClient.blockTexture(textures[i]);
        }
        isInventory = variantString.equals("inventory");
        texture = getVariantId(variantString);
        facing = getFacing(variantString);
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public RoadLineBlockRenderer setBase(Identifier top, Identifier side) {
        this.TOP_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, top);
        this.SIDE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, side);
        return this;
    }

    private int getVariantId(String variantString) {
        Pattern pattern = Pattern.compile("variant=(\\d+)");
        Matcher matcher = pattern.matcher(variantString);
        if (!matcher.find()) {
            return 0;
        }
        String var = matcher.group(1);
        return Integer.parseInt(var);
    }

    private Direction getFacing(String variantString) {
        Pattern pattern = Pattern.compile("facing=([^,]+)");
        Matcher matcher = pattern.matcher(variantString);
        if (!matcher.find()) {
            return Direction.NORTH;
        }
        String var = matcher.group(1);
        return Direction.byName(var);
    }


    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Sprite getParticleSprite() {
        return sprites[0];
    }

    @Override
    public List<BakedQuad> getQuads(BlockState arg0, Direction arg1, Random arg2) {
        return List.of();
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings) {
        for (int i = 0; i < textureIds.length; i++) {
            sprites[i] = textureGetter.apply(textureIds[i]);
        }
        SIDE_SPRITE = textureGetter.apply(SIDE_ID);
        TOP_SPRITE = textureGetter.apply(TOP_ID);

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        Sprite sprite = sprites[texture];

        // if (isInventory) {
        //     emitter.square(Direction.SOUTH, 0, 0, 1, 1, -0.1f);
        //     emitter.spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
        //     emitter.color(-1, -1, -1, -1);
        //     emitter.emit();

        //     emitter.square(Direction.SOUTH, 0, 0, 1, 1, 0);
        //     emitter.spriteBake(TOP_SPRITE, MutableQuadView.BAKE_LOCK_UV);
        //     emitter.color(-1, -1, -1, -1);
        //     emitter.emit();
        // } else {

            int bakeFlag = MutableQuadView.BAKE_ROTATE_NONE;
            switch (facing) {
                case Direction.EAST:
                    bakeFlag = MutableQuadView.BAKE_ROTATE_90;
                    break;
                case Direction.SOUTH:
                    bakeFlag = MutableQuadView.BAKE_ROTATE_180;
                    break;
                case Direction.WEST:
                    bakeFlag = MutableQuadView.BAKE_ROTATE_270;
                    break;

                default:
                    break;
            }

            emitter.square(Direction.UP, 0, 0, 1, 1, -0.01f);
            emitter.uv(0, 0, 0);
            emitter.uv(1, 0, 16);
            emitter.uv(2, 16, 16);
            emitter.uv(3, 16, 0);
            emitter.spriteBake(sprite, bakeFlag);
            emitter.color(-1, -1, -1, -1);
            emitter.emit();

            for (Direction dir : Direction.values()) {
                Sprite spr = dir.getAxis().equals(Axis.Y) ? TOP_SPRITE : SIDE_SPRITE;
                emitter.square(dir, 0, 0, 1, 1, 0);
                emitter.spriteBake(spr, MutableQuadView.BAKE_LOCK_UV);
                emitter.color(-1, -1, -1, -1);
                emitter.cullFace(dir);
                emitter.emit();
            }
        // }

        mesh = builder.build();
        
        return this;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }
    
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos,
            Supplier<Random> randomSupplier, RenderContext context) {
        mesh.outputTo(context.getEmitter());
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        mesh.outputTo(context.getEmitter());
    }

}
