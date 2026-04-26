package com.peter.cityblocks.blocks.signs;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector2f;
import org.joml.Vector3d;

import com.peter.cityblocks.blocks.IVariantBlock;

public abstract class CustomSignBlock extends BaseEntityBlock implements IVariantBlock {

    public static final String VARIANT_PROPERTY_NAME = "variant";

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected CustomSignBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = (CustomSignBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
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
        return state.getValue(FACING);
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

    protected abstract VoxelShape getShape(BlockState state);

    public abstract String[] getVariantNames(BlockState state);

    public abstract boolean isTextOnly(BlockState cachedState, int texture);

    public CompoundTag getNbt(BlockState state, int texture) {
        return new CompoundTag();
    }

    public BlockState getBlockStateFromNbt(BlockState state, CompoundTag nbt) {
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        CompoundTag nbt = CustomSignBlockEntity.getBlockStateNbtFromStack(ctx.getItemInHand());
        if (nbt != null) {
            return getBlockStateFromNbt(super.getStateForPlacement(ctx), nbt);
        }
        return super.getStateForPlacement(ctx);
    }

    public abstract void getTooltip(List<Component> tooltip, CompoundTag nbt, int texture);

}
