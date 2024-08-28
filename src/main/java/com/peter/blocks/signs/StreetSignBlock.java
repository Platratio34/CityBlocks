package com.peter.blocks.signs;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3d;

import com.mojang.serialization.MapCodec;
import com.peter.CityBlocks;
import com.peter.blocks.IVariantBlock;
import com.peter.blocks.VariantPartialBlock;
import com.peter.items.Items;

import net.minecraft.block.Block;
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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class StreetSignBlock extends CustomSignBlock {

    public static final String NAME = "street_sign";
    public static final Identifier ID = CityBlocks.identifier(NAME);

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

    public static final IntProperty MODEL = IntProperty.of("model", 0, TEXTURE_PREFIXES.length-1);

    public static final MapCodec<StreetSignBlock> CODEC = createCodec(StreetSignBlock::new);

    public static final CustomSignBlock BLOCK = Registry.register(Registries.BLOCK, ID,
            new StreetSignBlock(Settings.create().nonOpaque()));
    public static final CustomSignBlockItem ITEM = Registry.register(Registries.ITEM, ID,
            new CustomSignBlockItem(BLOCK, new Item.Settings()));
    public static final BlockEntityType<CustomSignBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
            BlockEntityType.Builder.create(StreetSignBlock::createSignBlockEntity, StreetSignBlock.BLOCK).build());

    protected StreetSignBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(MODEL, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return createSignBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, MODEL);
    }

    public static CustomSignBlockEntity createSignBlockEntity(BlockPos pos, BlockState state) {
        CustomSignBlockEntity entity = new CustomSignBlockEntity(BLOCK_ENTITY_TYPE, pos, state,
                Text.translatable(ID.toTranslationKey("block")));
        return entity;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public String getTexture(BlockState state, int texture) {
        int modelVariant = state.get(MODEL);
        return TEXTURE_PREFIXES[modelVariant] + TEXTURES[modelVariant][texture];
    }

    @Override
    public int getMaxVariant(BlockState state) {
        return TEXTURES[state.get(MODEL)].length - 1;
    }

    @Override
    public int getMaxTextLines(BlockState state, int texture) {
        return getMaxTextLines(state.get(MODEL), texture);
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
        int modelVariant = state.get(MODEL);
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
                    new TextLineInfo(0, 8.3f, 11, 1.2f, 2.6f).color(Colors.WHITE).maxWidth(12f),
                        new TextLineInfo(1, 8.3f, 5.5f, 1.2f, 1.75f).color(Colors.WHITE).maxWidth(6f)
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
                        new TextLineInfo(0, 8, 3.75f, 8.6f, 1.2f).color(Colors.WHITE).maxWidth(16f),
                        new TextLineInfo(0, 8, 3.75f, 8.6f, 1.2f).color(Colors.WHITE).rotation(180).maxWidth(16f),
                        new TextLineInfo(1, 8, 8.75f, 8.6f, 1.2f).color(Colors.WHITE).rotation(90).maxWidth(16f),
                        new TextLineInfo(1, 8, 8.75f, 8.6f, 1.2f).color(Colors.WHITE).rotation(270).maxWidth(16f)
                };
            }
        } else if (modelVariant == 10) {
            if (texture == 0) {
                return new TextLineInfo[] { new TextLineInfo(8, 10.5f, 1.2f, 2.5f).color(Colors.WHITE).maxWidth(33f) };
            }
        } else if (modelVariant == 11) {
            if (texture == 0) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 14.5f, 8.6f, 1.5f).color(Colors.WHITE).defaultText("KM").maxWidth(4.5f),
                        new TextLineInfo(1, 8, 11.5f, 8.6f, 1.75f).color(Colors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(2, 8, 8f, 8.6f, 1.75f).color(Colors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(3, 8, 4.5f, 8.6f, 1.75f).color(Colors.WHITE).maxWidth(4.5f)
                };
            } else if (texture == 1) {
                return new TextLineInfo[] {
                        new TextLineInfo(0, 8, 14.5f, 8.6f, 1.5f).color(Colors.WHITE).defaultText("KM").maxWidth(4.5f),
                        new TextLineInfo(1, 8, 10.0f, 8.6f, 1.75f).color(Colors.WHITE).maxWidth(4.5f),
                        new TextLineInfo(2, 8, 6.5f, 8.6f, 1.75f).color(Colors.WHITE).maxWidth(4.5f)
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
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.VARIANT_SWITCHER_ITEM)) {
            int model = state.get(MODEL) + 1;
            if (model >= TEXTURE_PREFIXES.length) {
                model = 0;
            }
            ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
            world.setBlockState(pos, state.with(MODEL, model));
            return ItemActionResult.CONSUME;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public Vector2f getTextureUVSize(BlockState state, int texture) {
        return new Vector2f(28f / 32f, 28f / 32f);
    }

    protected VoxelShape getShape(BlockState state) {
        int model = state.get(MODEL);
        Direction facing = state.get(FACING);
        if (model == 9) {
            return VoxelShapes.union(VariantPartialBlock.cube(-1, 0, 7.5, 18, 5, 1, facing),
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
        return TEXTURES[state.get(MODEL)];
    }

    @Override
    public boolean isTextOnly(BlockState state, int texture) {
        return TEXTURES[state.get(MODEL)][texture].length() == 0;
    }

    @Override
    public NbtCompound getNbt(BlockState state, int texture) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("model", state.get(MODEL));
        return nbt;
    }

    @Override
    public BlockState getBlockStateFromNbt(BlockState state, NbtCompound nbt) {
        int model = nbt.getInt("model");
        return state.with(MODEL, model);
    }

    @Override
    public void getTooltip(List<Text> tooltip, NbtCompound nbt, int texture) {
        int model = nbt.getInt("model");
        tooltip.add(Text.of(MODEL_NAMES[model]));
        String textureName = TEXTURES[model][texture];
        if (textureName.length() > 0) {
            tooltip.add(Text.of("- " + textureName));
        } else {
            tooltip.add(Text.of(String.format("- Text Only (%d)", getMaxTextLines(model, texture))));
        }
    }

    @Override
    public BlockState cycle(World world, BlockPos pos, BlockState state, boolean inverse) {
        int model = IVariantBlock.cycleInt(state.get(MODEL), TEXTURE_PREFIXES.length-1, inverse);
        ((CustomSignBlockEntity) world.getBlockEntity(pos)).setVariant(0);
        return state.with(MODEL, model);
    }

    @Override
    public int getVariant(BlockState state) {
        return state.get(MODEL);
    }

}
