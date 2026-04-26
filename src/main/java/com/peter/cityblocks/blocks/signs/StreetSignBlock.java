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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StreetSignBlock extends CustomSignBlock {

    public static final String NAME = "street_sign";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);

    private static final String[] TEXTURE_PREFIXES = {
            "street_sign_1/street_sign_1_",
            "street_sign_1_b/street_sign_1_b_",
            "street_sign_1_b/street_sign_1_b_",
            "street_sign_1_c/street_sign_1_c_",
            "street_sign_2/street_sign_2_",
            "street_sign_2_b/street_sign_2_b_",
            "street_sign_2_b/street_sign_2_b_",
            "street_sign_2_b/street_sign_2_b_",
            "street_sign_2_b/street_sign_2_b_",
            "street_sign_3/street_sign_3_",
            "street_sign_3_b/street_sign_3_b_",
            "street_sign_4a/street_sign_4a"
    };
    private static final String[][] TEXTURES = {
            { "no_entry", "no_left", "no_right", "no_parking" },
            { "spd", "", "" },
            { "left_only", "straight_only", "right_only", "straight_left", "straight_right", "one_way_left",
                    "one_way_right" },
            { "interstate", "hospital" },
            { "spd_advisory" },
            { "left", "right", "left_curve", "right_curve", "left_reverse", "right_reverse", "", "", "", "" },
            { "cross", "t", "t_left", "t_right" },
            { "lane_end_left", "lane_end_right", "merge_from_right", "add_from_right", "divided_ahead", "two_way" },
            { "low_clearance", "signal_ahead", "stop_ahead" },
            { "" },
            { "" },
            { "", "" }
    };
    private static final String[] MODEL_NAMES = {
            "Prohibitive",
            "Speed / Text",
            "Direction",
            "Information",
            "Advisory",
            "Turn / Advisory Text",
            "Intersection Ahead",
            "Lane Add/End",
            "Clearance / Control Ahead",
            "Street Name 2 Direction",
            "Street Name",
            "Distance Marker"
    };

    public static final IntegerProperty MODEL = IntegerProperty.create("model", 0, TEXTURE_PREFIXES.length-1);

    public static final MapCodec<StreetSignBlock> CODEC = simpleCodec(StreetSignBlock::new);

    public static final CustomSignBlock BLOCK = Registry.register(BuiltInRegistries.BLOCK, ID,
            new StreetSignBlock(Properties.of().noOcclusion().setId(Blocks.brk(ID))));
    public static final CustomSignBlockItem ITEM = Registry.register(BuiltInRegistries.ITEM, ID,
            new CustomSignBlockItem(BLOCK, new Item.Properties().setId(Blocks.irk(ID))));
    public static final BlockEntityType<CustomSignBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(StreetSignBlock::createSignBlockEntity, StreetSignBlock.BLOCK).build());

    protected StreetSignBlock(Properties settings) {
        super(settings);

        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(MODEL, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return createSignBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, MODEL);
    }

    public static CustomSignBlockEntity createSignBlockEntity(BlockPos pos, BlockState state) {
        CustomSignBlockEntity entity = new CustomSignBlockEntity(BLOCK_ENTITY_TYPE, pos, state,
                Component.translatable(ID.toLanguageKey("block")));
        return entity;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public String getTexture(BlockState state, int texture) {
        int modelVariant = state.getValue(MODEL);
        return TEXTURE_PREFIXES[modelVariant] + TEXTURES[modelVariant][texture];
    }

    @Override
    public int getMaxVariant(BlockState state) {
        return TEXTURES[state.getValue(MODEL)].length - 1;
    }

    @Override
    public int getMaxTextLines(BlockState state, int texture) {
        return getMaxTextLines(state.getValue(MODEL), texture);
    }

    private int getMaxTextLines(int model, int texture) {
        if (model == 1) {
            return (texture == 0 ? 1 : 0) + (texture == 1 ? 4 : 0) + (texture == 2 ? 3 : 0);
        } else if (model == 3) {
            return (texture == 0 ? 2 : 0);
        } else if (model == 4) {
            return (texture == 0 ? 1 : 0);
        } else if (model == 5) {
            return (texture == 6 ? 4 : 0) + (texture == 7 ? 3 : 0) + (texture == 8 ? 2 : 0) + (texture == 9 ? 1 : 0);
        } else if (model == 8) {
            return (texture == 0 ? 1 : 0);
        } else if (model == 9) {
            return 2;
        } else if (model == 10) {
            return 1;
        } else if (model == 11) {
            return (texture == 0 ? 4 : 0) + (texture == 1 ? 3 : 0);
        }
        return 0;
    }

    @Override
    public TextLineInfo[] getTextInfo(BlockState state, int texture) {
        int modelVariant = state.getValue(MODEL);
        if (modelVariant == 1) {
            if (texture == 0) {
                return new TextLineInfo[] { new TextLineInfo(8, 12, 1.2f, 2.3f).maxWidth(10f) };
            } else if (texture == 1) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 13.7f, 1.2f, 1.1f).maxWidth(10f),
                        new TextLineInfo(1, 8, 10.7f, 1.2f, 1.1f).maxWidth(10f),
                        new TextLineInfo(2, 8, 7.7f, 1.2f, 1.1f).maxWidth(10f),
                        new TextLineInfo(3, 8, 4.7f, 1.2f, 1.1f).maxWidth(10f)
                };
            } else if (texture == 2) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 12.5f, 1.2f, 1.1f).maxWidth(10f),
                        new TextLineInfo(1, 8, 9.5f, 1.2f, 1.1f).maxWidth(10f),
                        new TextLineInfo(2, 8, 6.5f, 1.2f, 1.1f).maxWidth(10f)
                };
            }
        } else if (modelVariant == 3) {
            if (texture == 0) {
                return new TextLineInfo[] {
                    new TextLineInfo(0, 8.3f, 11, 1.2f, 2.6f).color(CommonColors.WHITE).maxWidth(12f),
                        new TextLineInfo(1, 8.3f, 5.5f, 1.2f, 1.75f).color(CommonColors.WHITE).maxWidth(6f)
                    };
            }
        } else if (modelVariant == 4) {
            if (texture == 0) {
                return new TextLineInfo[] { new TextLineInfo(8, 13, 1.2f, 2.75f).maxWidth(12f) };
            }
        } else if (modelVariant == 5) {
            if (texture == 6) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 13.0f, 1.2f, 1.2f).maxWidth(8f),
                        new TextLineInfo(1, 8, 10.5f, 1.2f, 1.2f).maxWidth(13f),
                        new TextLineInfo(2, 8, 8.0f, 1.2f, 1.2f).maxWidth(13f),
                        new TextLineInfo(3, 8, 5.5f, 1.2f, 1.2f).maxWidth(8f)
                };
            } else if (texture == 7) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 12.0f, 1.2f, 1.3f).maxWidth(10f),
                        new TextLineInfo(1, 8, 9.25f, 1.2f, 1.3f).maxWidth(15f),
                        new TextLineInfo(2, 8, 6.5f, 1.2f, 1.3f).maxWidth(10f)
                };
            } else if (texture == 8) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 10.6f, 1.2f, 1.4f).maxWidth(13f),
                        new TextLineInfo(1, 8, 7.9f, 1.2f, 1.4f).maxWidth(13f),
                };
            } else if (texture == 9) {
                return new TextLineInfo[] {
                        new TextLineInfo(8, 9.5f, 1.2f, 1.6f).maxWidth(15f)
                };
            }
        } else if (modelVariant == 8) {
            if (texture == 0) {
                return new TextLineInfo[] { new TextLineInfo(8, 10.25f, 1.2f, 2.25f).maxWidth(14.5f) };
            }
        } else if (modelVariant == 9) {
            if (texture == 0) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 3.75f, 8.6f, 1.2f).color(CommonColors.WHITE).maxWidth(16f),
                        new TextLineInfo(0, 8, 3.75f, 8.6f, 1.2f).color(CommonColors.WHITE).rotation(180).maxWidth(16f),
                        new TextLineInfo(1, 8, 8.75f, 8.6f, 1.2f).color(CommonColors.WHITE).rotation(90).maxWidth(16f),
                        new TextLineInfo(1, 8, 8.75f, 8.6f, 1.2f).color(CommonColors.WHITE).rotation(270).maxWidth(16f)
                };
            }
        } else if (modelVariant == 10) {
            if (texture == 0) {
                return new TextLineInfo[] { new TextLineInfo(8, 10.5f, 1.2f, 2.5f).color(CommonColors.WHITE).maxWidth(33f) };
            }
        } else if (modelVariant == 11) {
            if (texture == 0) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 14.5f, 8.6f, 1.5f).color(CommonColors.WHITE).defaultText("KM").maxWidth(4.5f),
                        new TextLineInfo(1, 8, 11.5f, 8.6f, 1.75f).color(CommonColors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(2, 8, 8f, 8.6f, 1.75f).color(CommonColors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(3, 8, 4.5f, 8.6f, 1.75f).color(CommonColors.WHITE).maxWidth(4.5f)
                };
            } else if (texture == 1) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 14.5f, 8.6f, 1.5f).color(CommonColors.WHITE).defaultText("KM").maxWidth(4.5f),
                        new TextLineInfo(1, 8, 10.0f, 8.6f, 1.75f).color(CommonColors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(2, 8, 6.5f, 8.6f, 1.75f).color(CommonColors.WHITE).maxWidth(4.5f)
                };
            }
        }
        return new TextLineInfo[0];
    }

    @Override
    public Vector3d getTexturePosition(BlockState state, int texture) {
        return new Vector3d(1, 1, 1.1d);
    }

    @Override
    public Vector3d getTextureSize(BlockState state, int texture) {
        return new Vector3d(14, 14, 0);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.is(Items.VARIANT_SWITCHER_ITEM)) {
            int model = state.getValue(MODEL) + 1;
            if (model >= TEXTURE_PREFIXES.length) {
                model = 0;
            }
            ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
            world.setBlockAndUpdate(pos, state.setValue(MODEL, model));
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public Vector2f getTextureUVSize(BlockState state, int texture) {
        return new Vector2f(28f / 32f, 28f / 32f);
    }

    protected VoxelShape getShape(BlockState state) {
        int model = state.getValue(MODEL);
        Direction facing = state.getValue(FACING);
        if (model == 9) {
            return Shapes.or(VariantPartialBlock.cube(-1, 0, 7.5, 18, 5, 1, facing),
                    VariantPartialBlock.cube(7.5, 5, -1, 1, 5, 18, facing));
        } else if (model == 10) {
            return VariantPartialBlock.cube(-10, 3, 0, 36, 10, 1, facing);
        } else if (model == 11) {
            return VariantPartialBlock.cube(5, 0, 7.5f, 6, 16, 1, facing);
        }
        return VariantPartialBlock.cube(1, 1, 0, 14, 14, 1, facing);
    }

    @Override
    public String[] getVariantNames(BlockState state) {
        return TEXTURES[state.getValue(MODEL)];
    }

    @Override
    public boolean isTextOnly(BlockState state, int texture) {
        return TEXTURES[state.getValue(MODEL)][texture].length() == 0;
    }

    @Override
    public CompoundTag getNbt(BlockState state, int texture) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("model", state.getValue(MODEL));
        return nbt;
    }

    @Override
    public BlockState getBlockStateFromNbt(BlockState state, CompoundTag nbt) {
        int model = nbt.getInt("model").get();
        return state.setValue(MODEL, model);
    }

    @Override
    public void getTooltip(List<Component> tooltip, CompoundTag nbt, int texture) {
        int model = nbt.getInt("model").get();
        tooltip.add(Component.nullToEmpty(MODEL_NAMES[model]));
        String textureName = TEXTURES[model][texture];
        if (textureName.length() > 0) {
            tooltip.add(Component.nullToEmpty("- " + textureName));
        } else {
            tooltip.add(Component.nullToEmpty(String.format("- Text Only (%d)", getMaxTextLines(model, texture))));
        }
    }

    @Override
    public BlockState cycle(Level world, BlockPos pos, BlockState state, boolean inverse) {
        int model = IVariantBlock.cycleInt(state.getValue(MODEL), TEXTURE_PREFIXES.length-1, inverse);
        ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
        return state.setValue(MODEL, model);
    }

    @Override
    public int getVariant(BlockState state) {
        return state.getValue(MODEL);
    }

    @Override
    public String[] getVariants(BlockState state) {
        return MODEL_NAMES;
    }

}
