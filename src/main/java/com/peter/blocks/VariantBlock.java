package com.peter.blocks;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.peter.CityBlocks;
import com.peter.items.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class VariantBlock extends HorizontalFacingBlock implements IVariantBlock {

    public static final String VARIANT_PROPERTY_NAME = "variant";

    public final String name;
    public final Identifier id;
    public final int variants;
    public final IntProperty variant;
    public final MapCodec<? extends VariantBlock> codec;

    public final BlockItem item;

    public VariantBlock(Settings settings, String name) {
        this((VariantSettings) settings, name);
    }

    public VariantBlock(VariantSettings settings, String name) {
        super(settings);
        this.variants = settings.variants;
        variant = settings.variantProperty;
        codec = createCodec(this::constructor);
        this.name = name;
        this.id = CityBlocks.identifier(name);

        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(variant, 0));

        Registry.register(Registries.BLOCK, id, this);

        item = Registry.register(Registries.ITEM, id, new BlockItem(this, new Item.Settings()));
    }

    private VariantBlock constructor(Settings settings) {
        VariantSettings vSettings = (VariantSettings) settings;
        vSettings.setVariants(variants);
        return new VariantBlock(vSettings, name);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.VARIANT_SWITCHER_ITEM)) {
            int var = state.get(variant) + 1;
            if (var >= variants) {
                var = 0;
            }
            world.setBlockState(pos, state.with(variant, var));
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(((VariantSettings)settings).variantProperty);
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return codec;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING,
                ctx.getHorizontalPlayerFacing());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(CityBlocks.tooltip("block", name));
        super.appendTooltip(stack, context, tooltip, options);
    }
    
    public int getVariant(BlockState state) {
        return state.get(variant);
    }
    
    public static int getDir(BlockState state) {
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            case Direction.NORTH:
                return 0;
            case Direction.EAST:
                return 1;
            case Direction.SOUTH:
                return 2;
            case Direction.WEST:
                return 3;
        
            default:
                return 0;
        }
    }

    @Override
    public BlockState cycle(World world, BlockPos pos, BlockState state, boolean inverse) {
        return state.with(variant, IVariantBlock.cycleInt(state.get(variant), variants-1, inverse));
    }

}
