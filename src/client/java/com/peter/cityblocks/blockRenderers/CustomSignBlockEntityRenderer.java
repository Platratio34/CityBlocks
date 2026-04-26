package com.peter.cityblocks.blockRenderers;

import org.joml.Vector2f;
import org.joml.Vector3d;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peter.cityblocks.CityBlocksClient;
import com.peter.cityblocks.blocks.signs.CustomSignBlockEntity;
import com.peter.cityblocks.blocks.signs.TextLineInfo;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class CustomSignBlockEntityRenderer implements BlockEntityRenderer<CustomSignBlockEntity> {

    private final Font textRenderer;

    public CustomSignBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        textRenderer = context.getFont();
    }

    @Override
    public void render(CustomSignBlockEntity entity, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light, int overlay, Vec3 cameraPos) {
        matrices.pushPose();
                
        Direction facing = entity.getFacing();
        matrices.translate(0.5, 0.5, 0.5);
        switch (facing) {
            case Direction.NORTH:
                break;
            case Direction.EAST:
                matrices.mulPose(Axis.YP.rotationDegrees(270));
                break;
            case Direction.SOUTH:
                matrices.mulPose(Axis.YP.rotationDegrees(180));
                break;
            case Direction.WEST:
                matrices.mulPose(Axis.YP.rotationDegrees(90));
                break;

            default:
                break;
        }
        
        matrices.translate(-0.5, -0.5, -0.5);
        if(!entity.isTextOnly()) drawTexture(vertexConsumers, matrices, entity, light);
        if (entity.getMaxTextLines() > 0) {
            String[] lines = entity.getText();
            TextLineInfo[] info = entity.getTextInfo();
            for (int l = 0; l < info.length; l++) {
                TextLineInfo lineInfo = info[l];
                renderText(vertexConsumers, matrices, light, lines[lineInfo.lineN], lineInfo);
            }
        }
        matrices.popPose();
    }

    private static void drawTexture(MultiBufferSource vertexConsumerProvider, PoseStack matrices, CustomSignBlockEntity entity, int light) {

        PoseStack.Pose matrix = matrices.last();
        Material spriteId = CityBlocksClient.blockTexture(entity.getTexture());
        VertexConsumer vertexConsumer = spriteId.buffer(vertexConsumerProvider, RenderType::entityCutout);
        TextureAtlasSprite sprite = spriteId.sprite();

        Vector2f textureUVSize = entity.getTextureUVSize();
        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float uSize = maxU - minU;
        maxU = minU + (uSize * textureUVSize.x);

        float minV = sprite.getV0();
        float maxV = sprite.getV1();
        float vSize = maxV - minV;
        maxV = minV + (vSize * textureUVSize.y);

        Vector3d pos = entity.getTexturePosition().div(16d);
        Vector3d size = entity.getTextureSize().div(16d);

        addVertex(vertexConsumer, matrix, pos.x, pos.y, pos.z, minU, maxV, light);
        addVertex(vertexConsumer, matrix, pos.x + size.x, pos.y, pos.z, maxU, maxV, light);
        addVertex(vertexConsumer, matrix, pos.x + size.x, pos.y + size.y, pos.z, maxU, minV, light);
        addVertex(vertexConsumer, matrix, pos.x, pos.y + size.y, pos.z, minU, minV, light);
    }

    private static void addVertex(VertexConsumer vertices, PoseStack.Pose matrix, double x, double y, double z,
            float u, float v, int light) {
        vertices.addVertex(matrix, (float)x, (float)y, (float)z).setColor(-1, -1, -1, -1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }

    public void renderText(MultiBufferSource vertexConsumerProvider, PoseStack matrices, int light, String text, TextLineInfo info) {
        matrices.pushPose();

        if (info.rotation != 0) {
            matrices.translate(0.5, 0.5, 0.5);
            matrices.mulPose(Axis.YP.rotationDegrees(info.rotation));
            matrices.translate(-0.5, -0.5, -0.5);
        }
        float textHeight = this.textRenderer.lineHeight;
        float y = 0;
        // if (info.maxWidth > 0) System.out.println();
        matrices.translate(info.position.x/16f, info.position.y/16f, info.position.z/16f);
        float textScale = info.scale * 0.015625f;
        if (info.maxWidth > 0) {
            float sF = (info.maxWidth / 16f) / this.textRenderer.width(text);
            if (sF < textScale) {
                float diff = sF - textScale;
                textScale = sF;
                matrices.translate(0, (textHeight * diff * 0.5f), 0);
            }
        }
        matrices.scale(textScale, -textScale, textScale);

        float x = 0;
        if (info.centered) {
            x = (float) (-this.textRenderer.width(text) / 2f);
        }
        textRenderer.drawInBatch(text, x, y, info.color, false, matrices.last().pose(), vertexConsumerProvider,
                DisplayMode.POLYGON_OFFSET, 0, light);

        matrices.popPose();
    }

}
