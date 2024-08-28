package com.peter.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class VariantPartialBlock extends VariantBlock {

    private final VoxelShape[][] shapes;

    public VariantPartialBlock(Settings settings, String name, VoxelShape[][] shapes) {
        super(settings, name);
        this.shapes = shapes;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return getShape(state);
    }

    public VoxelShape getShape(BlockState state) {
        return shapes[getDir(state)][getVariant(state)];
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
        return Block.createCuboidShape(x, y, z, x + sizeX, y + sizeY, z + sizeZ);
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

}
