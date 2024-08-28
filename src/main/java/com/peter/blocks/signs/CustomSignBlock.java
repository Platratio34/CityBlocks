package com.peter.blocks.signs;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.peter.blocks.IVariantBlock;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class CustomSignBlock extends BlockWithEntity implements IVariantBlock {

    public static final String VARIANT_PROPERTY_NAME = "variant";

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    protected CustomSignBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (CustomSignBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public abstract String getTexture(BlockState state, int texture);

    public abstract int getMaxVariant(BlockState state);

    public abstract int getMaxTextLines(BlockState state, int texture);

    // @NotNull
    // public abstract Vector3f[] getTextPositions(BlockState state, int texture);
    // @NotNull
    // public abstract float[] getTextScales(BlockState state, int texture);
    // @NotNull
    // public abstract boolean[] getTextCentered(BlockState state, int texture);
    public abstract TextLineInfo[] getTextInfo(BlockState state, int texture);
    
    public abstract Vector3d getTexturePosition(BlockState state, int texture);

    public abstract Vector3d getTextureSize(BlockState state, int texture);

    public abstract Vector2f getTextureUVSize(BlockState state, int texture);

    public Direction getFacing(BlockState state) {
        return state.get(FACING);
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

    protected abstract VoxelShape getShape(BlockState state);

    public abstract String[] getVariantNames(BlockState state);

    public abstract boolean isTextOnly(BlockState cachedState, int texture);

    public NbtCompound getNbt(BlockState state, int texture) {
        return new NbtCompound();
    }

    public BlockState getBlockStateFromNbt(BlockState state, NbtCompound nbt) {
        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        NbtCompound nbt = CustomSignBlockEntity.getBlockStateNbtFromStack(ctx.getStack());
        if (nbt != null) {
            return getBlockStateFromNbt(super.getPlacementState(ctx), nbt);
        }
        return super.getPlacementState(ctx);
    }

    public abstract void getTooltip(List<Text> tooltip, NbtCompound nbt, int texture);

}
