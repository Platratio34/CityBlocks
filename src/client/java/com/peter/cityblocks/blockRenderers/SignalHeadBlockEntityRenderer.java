package com.peter.cityblocks.blockRenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peter.cityblocks.CityBlocksClient;
import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SignalHeadBlockEntityRenderer implements BlockEntityRenderer<SignalHeadBlockEntity> {

    private static final Material[][] LAMP_SPRITE_IDS = new Material[][] {
            new Material[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.RED)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.RED))
            },
            new Material[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.AMBER)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.AMBER))
            },
            new Material[] {
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.OFF, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.SOLID_FLASH, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.LEFT_FLASH, LampColor.GREEN)),
                    CityBlocksClient.blockTexture(LampState.getTexture(LampState.RIGHT_FLASH, LampColor.GREEN))
            }
    };

    public SignalHeadBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(SignalHeadBlockEntity entity, float tickProgress, PoseStack matrices,
            MultiBufferSource vertexProvider, int light, int overlay, Vec3 cameraPos) {
        matrices.pushPose();
        
        BlockState blockState = entity.getBlockState();
        Direction facing = blockState.getValue(SignalHeadBlock.FACING);
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

        int nLamps = entity.getLampCount();

        Material[] sprites = new Material[nLamps];
        boolean[] lampOn = new boolean[nLamps];
        for (int i = 0; i < nLamps; i++) {
            LampState lampState = entity.getState(i);
            if (lampState.flash) {
                boolean half = (System.currentTimeMillis() % 1000) < 500;
                if (blockState.getValue(SignalHeadBlock.FACING) == Direction.NORTH
                        || blockState.getValue(SignalHeadBlock.FACING) == Direction.SOUTH) {
                    if (half) {
                        lampState = LampState.OFF;
                    }
                } else {
                    if (!half) {
                        lampState = LampState.OFF;
                    }
                }
            }
            lampOn[i] = lampState != LampState.OFF;
            sprites[i] = LAMP_SPRITE_IDS[entity.getColor(i).code][lampState.code];

        }

        if (nLamps == 3) {
            drawLamp(vertexProvider, matrices, sprites[0], 6.5f, 12.0f, 3.0F,
                3, 3, 0, facing);
        
            drawLamp(vertexProvider, matrices, sprites[1], 6.5f, 6.5f, 3.0f,
                3, 3, 0, facing);
        
            drawLamp(vertexProvider, matrices, sprites[2], 6.5f, 1.0f, 3.0f,
                3, 3, 0, facing);
        } else if (nLamps == 1) {
            drawLamp(vertexProvider, matrices, sprites[0], 6.5f, 6.5f, 3.0F,
                3, 3, 0, facing);
        }

        matrices.popPose();
    }

    private static void drawLamp(MultiBufferSource vertexConsumerProvider, PoseStack matrices, Material spriteId, float x, float y, float z,
            float sX, float sY, float sZ, Direction facing) {
        PoseStack.Pose matrix = matrices.last();
        x /= 16f;
        y /= 16f;
        z /= 16f;
        sX /= 16f;
        sY /= 16f;
        sZ /= 16f;

        VertexConsumer vertexConsumer = spriteId.buffer(vertexConsumerProvider, RenderType::entitySolid);
        
        TextureAtlasSprite sprite = spriteId.sprite();
        float fullUV = 7f / 16f;
        float uSize = sprite.getU1() - sprite.getU0();
        float vSize = sprite.getV1() - sprite.getV0();
        float minU = sprite.getU0();
        float maxU = minU + (fullUV * uSize);
        float minV = sprite.getV0();
        float maxV = minV + (fullUV * vSize);

        addVertex(vertexConsumer, matrix, x, y, z, minU, maxV);
        addVertex(vertexConsumer, matrix, x + sX, y, z, maxU, maxV);
        addVertex(vertexConsumer, matrix, x + sX, y + sY, z, maxU, minV);
        addVertex(vertexConsumer, matrix, x, y + sY, z, minU, minV);
    }

    private static void addVertex(VertexConsumer vertices, PoseStack.Pose matrix, float x, float y, float z,
            float u, float v) {
        vertices.addVertex(matrix, x, (float) y, z).setColor(-1, -1, -1, -1).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880).setNormal(matrix, 0.0F, 1.0F, 0.0F);
    }
}
