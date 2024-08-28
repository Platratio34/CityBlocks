package com.peter.blockRenderers;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.peter.CityBlocksClient;
import com.peter.blocks.signs.CustomSignBlockEntity;
import com.peter.blocks.signs.TextLineInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class CustomSignBlockEntityRenderer implements BlockEntityRenderer<CustomSignBlockEntity> {

    private final TextRenderer textRenderer;

    public CustomSignBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        textRenderer = context.getTextRenderer();
    }

    @Override
    public void render(CustomSignBlockEntity entity, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
                
        Direction facing = entity.getFacing();
        matrices.translate(0.5, 0.5, 0.5);
        switch (facing) {
            case Direction.NORTH:
                break;
            case Direction.EAST:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                break;
            case Direction.SOUTH:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                break;
            case Direction.WEST:
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
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
        matrices.pop();
    }

    private static void drawTexture(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrices, CustomSignBlockEntity entity, int light) {

        MatrixStack.Entry matrix = matrices.peek();
        SpriteIdentifier spriteId = CityBlocksClient.blockTexture(entity.getTexture());
        VertexConsumer vertexConsumer = spriteId.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);
        Sprite sprite = spriteId.getSprite();

        Vector2f textureUVSize = entity.getTextureUVSize();
        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float uSize = maxU - minU;
        maxU = minU + (uSize * textureUVSize.x);

        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();
        float vSize = maxV - minV;
        maxV = minV + (vSize * textureUVSize.y);

        Vector3d pos = entity.getTexturePosition().div(16d);
        Vector3d size = entity.getTextureSize().div(16d);

        addVertex(vertexConsumer, matrix, pos.x, pos.y, pos.z, minU, maxV, light);
        addVertex(vertexConsumer, matrix, pos.x + size.x, pos.y, pos.z, maxU, maxV, light);
        addVertex(vertexConsumer, matrix, pos.x + size.x, pos.y + size.y, pos.z, maxU, minV, light);
        addVertex(vertexConsumer, matrix, pos.x, pos.y + size.y, pos.z, minU, minV, light);
    }

    private static void addVertex(VertexConsumer vertices, MatrixStack.Entry matrix, double x, double y, double z,
            float u, float v, int light) {
        vertices.vertex(matrix, (float)x, (float)y, (float)z).color(-1, -1, -1, -1).texture(u, v).overlay(OverlayTexture.DEFAULT_UV)
                .light(light).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

    public void renderText(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrices, int light, String text, TextLineInfo info) {
        matrices.push();

        if (info.rotation != 0) {
            matrices.translate(0.5, 0.5, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(info.rotation));
            matrices.translate(-0.5, -0.5, -0.5);
        }
        float textHeight = this.textRenderer.fontHeight;
        float y = 0;
        // if (info.maxWidth > 0) System.out.println();
        matrices.translate(info.position.x/16f, info.position.y/16f, info.position.z/16f);
        float textScale = info.scale * 0.015625f;
        if (info.maxWidth > 0) {
            float sF = (info.maxWidth / 16f) / this.textRenderer.getWidth(text);
            if (sF < textScale) {
                float diff = sF - textScale;
                textScale = sF;
                matrices.translate(0, (textHeight * diff * 0.5f), 0);
            }
        }
        matrices.scale(textScale, -textScale, textScale);

        float x = 0;
        if (info.centered) {
            x = (float) (-this.textRenderer.getWidth(text) / 2f);
        }
        textRenderer.draw(text, x, y, info.color, false, matrices.peek().getPositionMatrix(), vertexConsumerProvider,
                TextLayerType.POLYGON_OFFSET, 0, light);

        matrices.pop();
    }

}
