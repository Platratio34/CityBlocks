package com.peter.cityblocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VariantPartialBlock extends VariantBlock {

    public VariantPartialBlock(Properties settings, String name, ResourceLocation[] modelVariants) {
        super(settings, name, modelVariants);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return getShape(state);
    }

    protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state) {
        return getShape(getVariant(state), state.getValue(FACING));
    }

    public VoxelShape getShape(int variant, Direction direction) {
        return Shapes.block();
    }

    public static VoxelShape cube(double x, double y, double z, double sizeX, double sizeY, double sizeZ) {
        if (sizeX < 0) {
            x += sizeX;
            sizeX *= -1;
        }
        if (sizeY < 0) {
            y += sizeY;
            sizeY *= -1;
        }
        if (sizeZ < 0) {
            z += sizeZ;
            sizeZ *= -1;
        }
        return Block.box(x, y, z, x + sizeX, y + sizeY, z + sizeZ);
    }

    public static VoxelShape cube(double x, double y, double z, double sizeX, double sizeY, double sizeZ, Direction r) {
        switch (r) {
            case Direction.NORTH:
                return cube(x, y, z, sizeX, sizeY, sizeZ);
            case Direction.EAST:
                return cube(-(z - 8) + 8, y, x, -sizeZ, sizeY, sizeX);
            case Direction.SOUTH:
                return cube(-(x - 8) + 8, y, -(z - 8) + 8, -sizeX, sizeY, -sizeZ);
            case Direction.WEST:
                return cube(z, y, -(x - 8) + 8, sizeZ, sizeY, -sizeX);

            default:
                break;
        }
        return cube(x, y, z, sizeX, sizeY, sizeZ);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    protected int getLightBlock(BlockState state) {
        return 0;
    }

    // @Override
    // protected boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
    //     return false;
    // }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1f;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
