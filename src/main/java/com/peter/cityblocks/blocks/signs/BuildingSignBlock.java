package com.peter.cityblocks.blocks.signs;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.items.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class BuildingSignBlock extends CustomSignBlock {

    public static final String NAME = "building_sign";
    public static final Identifier ID = CityBlocks.identifier(NAME);

    public static final IntProperty MODEL = IntProperty.of("model", 0, 2);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public static final MapCodec<BuildingSignBlock> CODEC = createCodec(BuildingSignBlock::new);

    public static final CustomSignBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new BuildingSignBlock(Settings.create().nonOpaque()));
    public static final CustomSignBlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new CustomSignBlockItem(BLOCK, new Item.Settings()));
    public static final BlockEntityType<CustomSignBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
            BlockEntityType.Builder.create(BuildingSignBlock::createSignBlockEntity, BLOCK).build());

    protected BuildingSignBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING, MODEL);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public static CustomSignBlockEntity createSignBlockEntity(BlockPos pos, BlockState state) {
        CustomSignBlockEntity entity = new CustomSignBlockEntity(BLOCK_ENTITY_TYPE, pos, state,
                Text.translatable(ID.toTranslationKey("block")));
        return entity;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return createSignBlockEntity(pos, state);
    }

    @Override
    public String getTexture(BlockState state, int texture) {
        return "";
    }

    @Override
    public int getMaxVariant(BlockState state) {
        int model = state.get(MODEL);
        if (model == 0) {
            return 1;
        } else if (model == 1 || model == 2) {
            return 2;
        }
        return 0;
    }

    @Override
    public int getMaxTextLines(BlockState state, int texture) {
        int model = state.get(MODEL);
        if (model == 0) {
            return 2;
        } else if (model == 1 || model == 2) {
            return (texture == 0 ? 1 : 0) + (texture == 1 ? 2 : 0);
        }
        return 0;
    }

    @Override
    public TextLineInfo[] getTextInfo(BlockState state, int texture) {
        int model = state.get(MODEL);
        if (model == 0) {
            return new TextLineInfo[] {
                    new TextLineInfo(0, 8f, 10.0f, 1.1f, 1.5f).maxWidth(10f),
                    new TextLineInfo(1, 8f, 7.0f, 1.1f, 0.75f).maxWidth(10f)
            };
        } else if (model == 1) {
            if (texture == 0)
                return new TextLineInfo[] { new TextLineInfo(5f, 9.0f, 1.1f, 1f).color(Colors.WHITE).maxWidth(7f) };
            else if (texture == 1)
                return new TextLineInfo[] {
                        new TextLineInfo(0, 5f, 9.0f, 1.1f, 0.5f).color(Colors.WHITE).maxWidth(7f),
                        new TextLineInfo(1, 5f, 8.0f, 1.1f, 0.5f).color(Colors.WHITE).maxWidth(7f)
                };
        } else if (model == 2) {
            if (texture == 0)
                return new TextLineInfo[] { new TextLineInfo(16-5f, 9.0f, 1.1f, 1f).color(Colors.WHITE).maxWidth(7f) };
            else if (texture == 1)
                return new TextLineInfo[] {
                        new TextLineInfo(0, 16-5f, 9.0f, 1.1f, 0.5f).color(Colors.WHITE).maxWidth(7f),
                        new TextLineInfo(1, 16-5f, 8.0f, 1.1f, 0.5f).color(Colors.WHITE).maxWidth(7f)
                };
        }
        return new TextLineInfo[0];
    }

    @Override
    public Vector3d getTexturePosition(BlockState state, int texture) {
        return new Vector3d();
    }

    @Override
    public Vector3d getTextureSize(BlockState state, int texture) {
        return new Vector3d(1);
    }

    @Override
    public Vector2f getTextureUVSize(BlockState state, int texture) {
        return new Vector2f(1);
    }

    @Override
    protected VoxelShape getShape(BlockState state) {
        int model = state.get(MODEL);
        Direction facing = state.get(FACING);
        switch (model) {
            case 0:
                return VariantPartialBlock.cube(2, 4, 0, 12, 8, 1, facing);
            case 1:
                return VariantPartialBlock.cube(1, 6, 0, 8, 4, 1, facing);
            case 2:
                return VariantPartialBlock.cube(7, 6, 0, 8, 4, 1, facing);
            
            default:
                return VariantPartialBlock.cube(1, 1, 0, 14, 14, 1, facing);
        }
    }

    @Override
    public String[] getVariantNames(BlockState state) {
        int model = state.get(MODEL);
        switch (model) {
            case 0:
                return new String[] { "Address" };
            case 1:
                return new String[] { "1 Line", "2 Line" };
            case 2:
                return new String[] { "1 Line", "2 Line" };
        
            default:
                return new String[] { "Unknown" };
        }
    }

    @Override
    public boolean isTextOnly(BlockState cachedState, int texture) {
        return true;
    }

    @Override
    public void getTooltip(List<Text> tooltip, NbtCompound nbt, int texture) {
        if (nbt.contains("model")) {
            int model = nbt.getInt("model");
            switch (model) {
                case 0:
                    tooltip.add(Text.of("Address Sign"));
                    break;
                case 1:
                    tooltip.add(Text.of("Room Sign (left)"));
                    if (texture == 0)
                        tooltip.add(Text.of("- 1 Line"));
                    if (texture == 1)
                        tooltip.add(Text.of("- 2 Lines"));
                    break;
                case 2:
                    tooltip.add(Text.of("Room Sign (right)"));
                    if (texture == 0)
                        tooltip.add(Text.of("- 1 Line"));
                    if (texture == 1)
                        tooltip.add(Text.of("- 2 Lines"));
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public NbtCompound getNbt(BlockState state, int texture) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("model", state.get(MODEL));
        return nbt;
    }

    @Override
    public BlockState getBlockStateFromNbt(BlockState state, NbtCompound nbt) {
        int model = 0;
        if (nbt.contains("model")) {
            model = nbt.getInt("model");
        }
        return state.with(MODEL, model);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.VARIANT_SWITCHER_ITEM)) {
            int model = state.get(MODEL) + 1;
            if (model >= 3) {
                model = 0;
            }
            ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
            world.setBlockState(pos, state.with(MODEL, model));
            return ItemActionResult.CONSUME;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState cycle(World world, BlockPos pos, BlockState state, boolean inverse) {
        int model = IVariantBlock.cycleInt(state.get(MODEL), 2, inverse);
        ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
        return state.with(MODEL, model);
    }

    @Override
    public int getVariant(BlockState state) {
        return state.get(MODEL);
    }

}
