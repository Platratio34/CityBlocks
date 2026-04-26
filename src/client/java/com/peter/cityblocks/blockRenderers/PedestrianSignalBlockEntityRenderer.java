package com.peter.cityblocks.blockRenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peter.cityblocks.CityBlocksClient;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlock;
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

public class PedestrianSignalBlockEntityRenderer implements BlockEntityRenderer<PedestrianSignalBlockEntity> {

    private static final Material WALK_SPRITE = CityBlocksClient.blockTexture("pedestrian_signal_walk");
    private static final Material STOP_SPRITE = CityBlocksClient.blockTexture("pedestrian_signal_hand");

    public PedestrianSignalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PedestrianSignalBlockEntity entity, float tickDelta, PoseStack matrices,
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

        Material sprite = null;
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
        float fullUV = 14f / 16f;
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
