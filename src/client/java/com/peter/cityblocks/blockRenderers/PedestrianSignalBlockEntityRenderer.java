package com.peter.cityblocks.blockRenderers;

import com.peter.cityblocks.CityBlocksClient;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;

import net.minecraft.block.BlockState;
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

public class PedestrianSignalBlockEntityRenderer implements BlockEntityRenderer<PedestrianSignalBlockEntity> {

    private static final SpriteIdentifier WALK_SPRITE = CityBlocksClient.blockTexture("pedestrian_signal_walk");
    private static final SpriteIdentifier STOP_SPRITE = CityBlocksClient.blockTexture("pedestrian_signal_hand");

    public PedestrianSignalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(PedestrianSignalBlockEntity entity, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexProvider, int light, int overlay) {
        matrices.push();
        
        BlockState blockState = entity.getCachedState();
        Direction facing = blockState.get(SignalHeadBlock.FACING);
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

        SpriteIdentifier sprite = null;
        boolean half = (System.currentTimeMillis() % 1000) < 500;
        if (facing == Direction.NORTH || facing == Direction.SOUTH) half = !half;
        switch (entity.getState()) {
            case PedestrianSignalBlockEntity.WALK_STATE:
                sprite = WALK_SPRITE;
                break;
            case PedestrianSignalBlockEntity.STOP_STATE:
                sprite = STOP_SPRITE;
                break;
            case PedestrianSignalBlockEntity.FLASH_STATE:
                if (half)
                    sprite = STOP_SPRITE;
                break;

            default:
                break;
        }
        
        if(sprite != null)
            drawLamp(vertexProvider, matrices, sprite, 4.5f, 4.5f, 2.1f, 7, 7, 0, facing);

        matrices.pop();
    }

    private static void drawLamp(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrices, SpriteIdentifier spriteId, float x, float y, float z,
            float sX, float sY, float sZ, Direction facing) {
        MatrixStack.Entry matrix = matrices.peek();
        x /= 16f;
        y /= 16f;
        z /= 16f;
        sX /= 16f;
        sY /= 16f;
        sZ /= 16f;

        VertexConsumer vertexConsumer = spriteId.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        
        Sprite sprite = spriteId.getSprite();
        float fullUV = 14f / 16f;
        float uSize = sprite.getMaxU() - sprite.getMinU();
        float vSize = sprite.getMaxV() - sprite.getMinV();
        float minU = sprite.getMinU();
        float maxU = minU + (fullUV * uSize);
        float minV = sprite.getMinV();
        float maxV = minV + (fullUV * vSize);

        addVertex(vertexConsumer, matrix, x, y, z, minU, maxV);
        addVertex(vertexConsumer, matrix, x + sX, y, z, maxU, maxV);
        addVertex(vertexConsumer, matrix, x + sX, y + sY, z, maxU, minV);
        addVertex(vertexConsumer, matrix, x, y + sY, z, minU, minV);
    }

    private static void addVertex(VertexConsumer vertices, MatrixStack.Entry matrix, float x, float y, float z,
            float u, float v) {
        vertices.vertex(matrix, x, (float) y, z).color(-1, -1, -1, -1).texture(u, v).overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880).normal(matrix, 0.0F, 1.0F, 0.0F);
    }

}
