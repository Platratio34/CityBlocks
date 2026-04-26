package com.peter.cityblocks.blocks.signs;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.Blocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.blocks.VariantPartialBlock;
import com.peter.cityblocks.items.Items;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BuildingSignBlock extends CustomSignBlock {

    public static final String NAME = "building_sign";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    public static final IntegerProperty MODEL = IntegerProperty.create("model", 0, 2);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final MapCodec<BuildingSignBlock> CODEC = simpleCodec(BuildingSignBlock::new);

    public static final CustomSignBlock BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new BuildingSignBlock(Properties.of().noOcclusion().setId(Blocks.brk(ID))));
    public static final CustomSignBlockItem ITEM = Registry.register(BuiltInRegistries.ITEM, ID,
            new CustomSignBlockItem(BLOCK, new Item.Properties().setId(Blocks.irk(ID))));
    public static final BlockEntityType<CustomSignBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(BuildingSignBlock::createSignBlockEntity, BLOCK).build());

    protected BuildingSignBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING, MODEL);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection());
    }

    public static CustomSignBlockEntity createSignBlockEntity(BlockPos pos, BlockState state) {
        CustomSignBlockEntity entity = new CustomSignBlockEntity(BLOCK_ENTITY_TYPE, pos, state,
                Component.translatable(ID.toLanguageKey("block")));
        return entity;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return createSignBlockEntity(pos, state);
    }

    @Override
    public String getTexture(BlockState state, int texture) {
        return "";
    }

    @Override
    public int getMaxVariant(BlockState state) {
        int model = state.getValue(MODEL);
        if (model == 0) {
            return 1;
        } else if (model == 1 || model == 2) {
            return 2;
        }
        return 0;
    }

    @Override
    public int getMaxTextLines(BlockState state, int texture) {
        int model = state.getValue(MODEL);
        if (model == 0) {
            return 2;
        } else if (model == 1 || model == 2) {
            return (texture == 0 ? 1 : 0) + (texture == 1 ? 2 : 0);
        }
        return 0;
    }

    @Override
    public TextLineInfo[] getTextInfo(BlockState state, int texture) {
        int model = state.getValue(MODEL);
        if (model == 0) {
            return new TextLineInfo[] {
                    new TextLineInfo(0, 8f, 10.0f, 1.1f, 1.5f).maxWidth(10f),
                    new TextLineInfo(1, 8f, 7.0f, 1.1f, 0.75f).maxWidth(10f)
            };
        } else if (model == 1) {
            if (texture == 0)
                return new TextLineInfo[] { new TextLineInfo(5f, 9.0f, 1.1f, 1f).color(CommonColors.WHITE).maxWidth(7f) };
            else if (texture == 1)
                return new TextLineInfo[] {
                        new TextLineInfo(0, 5f, 9.0f, 1.1f, 0.5f).color(CommonColors.WHITE).maxWidth(7f),
                        new TextLineInfo(1, 5f, 8.0f, 1.1f, 0.5f).color(CommonColors.WHITE).maxWidth(7f)
                };
        } else if (model == 2) {
            if (texture == 0)
                return new TextLineInfo[] { new TextLineInfo(16-5f, 9.0f, 1.1f, 1f).color(CommonColors.WHITE).maxWidth(7f) };
            else if (texture == 1)
                return new TextLineInfo[] {
                        new TextLineInfo(0, 16-5f, 9.0f, 1.1f, 0.5f).color(CommonColors.WHITE).maxWidth(7f),
                        new TextLineInfo(1, 16-5f, 8.0f, 1.1f, 0.5f).color(CommonColors.WHITE).maxWidth(7f)
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
        int model = state.getValue(MODEL);
        Direction facing = state.getValue(FACING);
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
        int model = state.getValue(MODEL);
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
    public void getTooltip(List<Component> tooltip, CompoundTag nbt, int texture) {
        if (nbt.contains("model")) {
            int model = nbt.getInt("model").get();
            switch (model) {
                case 0:
                    tooltip.add(Component.nullToEmpty("Address Sign"));
                    break;
                case 1:
                    tooltip.add(Component.nullToEmpty("Room Sign (left)"));
                    if (texture == 0)
                        tooltip.add(Component.nullToEmpty("- 1 Line"));
                    if (texture == 1)
                        tooltip.add(Component.nullToEmpty("- 2 Lines"));
                    break;
                case 2:
                    tooltip.add(Component.nullToEmpty("Room Sign (right)"));
                    if (texture == 0)
                        tooltip.add(Component.nullToEmpty("- 1 Line"));
                    if (texture == 1)
                        tooltip.add(Component.nullToEmpty("- 2 Lines"));
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public CompoundTag getNbt(BlockState state, int texture) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("model", state.getValue(MODEL));
        return nbt;
    }

    @Override
    public BlockState getBlockStateFromNbt(BlockState state, CompoundTag nbt) {
        int model = 0;
        if (nbt.contains("model")) {
            model = nbt.getInt("model").get();
        }
        return state.setValue(MODEL, model);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.is(Items.VARIANT_SWITCHER_ITEM)) {
            int model = state.getValue(MODEL) + 1;
            if (model >= 3) {
                model = 0;
            }
            ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
            world.setBlockAndUpdate(pos, state.setValue(MODEL, model));
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState cycle(Level world, BlockPos pos, BlockState state, boolean inverse) {
        int model = IVariantBlock.cycleInt(state.getValue(MODEL), 2, inverse);
        ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
        return state.setValue(MODEL, model);
    }

    @Override
    public int getVariant(BlockState state) {
        return state.getValue(MODEL);
    }

    @Override
    public String[] getVariants(BlockState state) {
        return new String[] {
            "Address",
            "Room (left)",
            "Room (right)"
        };
    }

}
