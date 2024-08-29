package com.peter.blockRenderers;

import com.peter.CityBlocksClient;
import com.peter.blocks.signal.LampColor;
import com.peter.blocks.signal.LampState;
import com.peter.blocks.signal.SignalHeadBlock;
import com.peter.blocks.signal.SignalHeadBlockEntity;

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

public class SignalHeadBlockEntityRenderer implements BlockEntityRenderer<SignalHeadBlockEntity> {

    private static final SpriteIdentifier[][] LAMP_SPRITE_IDS = new SpriteIdentifier[][] {
            new SpriteIdentifier[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.RED))
            },
            new SpriteIdentifier[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.AMBER))
            },
            new SpriteIdentifier[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.GREEN))
            }
    };

    public SignalHeadBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(SignalHeadBlockEntity entity, float tickDelta, MatrixStack matrices,
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

        int nLamps = blockState.get(SignalHeadBlock.LAMP_COUNT);

        SpriteIdentifier[] sprites = new SpriteIdentifier[nLamps];
        for (int i = 0; i < nLamps; i++) {
            LampState lampState = entity.getState(i);
            if (lampState.flash) {
                boolean half = (System.currentTimeMillis() % 1000) < 500;
                if (blockState.get(SignalHeadBlock.FACING) == Direction.NORTH
                        || blockState.get(SignalHeadBlock.FACING) == Direction.SOUTH) {
                    if (half) {
                        lampState = LampState.OFF;
                    }
                } else {
                    if (!half) {
                        lampState = LampState.OFF;
                    }
                }
            }
            sprites[i] = LAMP_SPRITE_IDS[entity.getColor(i).code][lampState.code];

        }

        if (nLamps == 3) {
            drawLamp(vertexProvider, matrices, sprites[0], 6.5f, 12.0f, 3.0F,
                    3, 3, 0, facing);

            drawLamp(vertexProvider, matrices, sprites[1], 6.5f, 6.5f, 3.0f,
                    3, 3, 0, facing);

            drawLamp(vertexProvider, matrices, sprites[2], 6.5f, 1.0f, 3.0f,
                    3, 3, 0, facing);
        }

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
        float fullUV = 7f / 16f;
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
