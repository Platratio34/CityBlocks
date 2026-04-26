package com.peter.cityblocks.blocks;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import com.mojang.serialization.MapCodec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.items.Items;

public class VariantBlock extends HorizontalDirectionalBlock implements IVariantBlock, TooltipedItem {

    public static final String VARIANT_PROPERTY_NAME = "variant";

    public final String name;
    public final ResourceLocation id;
    public final int variants;
    public final IntegerProperty variant;
    public final MapCodec<? extends VariantBlock> codec;

    public final BlockItem item;
    public final ResourceLocation[] modelVariants;

    public VariantBlock(Properties settings, String name, ResourceLocation[] modelVariants) {
        this((VariantSettings) settings, name, modelVariants);
    }

    public VariantBlock(VariantSettings settings, String name, ResourceLocation[] modelVariants) {
        super(settings.setId(Blocks.brk(CityBlocks.identifier(name))));
        this.variants = settings.variants;
        this.modelVariants = modelVariants;
        variant = settings.variantProperty;
        codec = simpleCodec(this::constructor);
        this.name = name;
        this.id = CityBlocks.identifier(name);

        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(variant, 0));

        Registry.register(BuiltInRegistries.BLOCK, id, this);

        item = Blocks.registerBlockItem(this, id, new Item.Properties());
    }

    private VariantBlock constructor(Properties settings) {
        VariantSettings vSettings = (VariantSettings) settings;
        vSettings.setVariants(variants);
        return new VariantBlock(vSettings, name, modelVariants);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.is(Items.VARIANT_SWITCHER_ITEM)) {
            int var = state.getValue(variant) + 1;
            if (var >= variants) {
                var = 0;
            }
            world.setBlockAndUpdate(pos, state.setValue(variant, var));
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(((VariantSettings)properties).variantProperty);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return codec;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(BlockStateProperties.HORIZONTAL_FACING,
                ctx.getHorizontalDirection());
    }

    @Override
    public void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipFlag tooltipType,
            List<Component> list) {
        list.add(CityBlocks.tooltip("item", name));
    }
    
    public int getVariant(BlockState state) {
        return state.getValue(variant);
    }
    
    public static int getDir(BlockState state) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
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
    
    public static int getDir(Direction dir) {
        switch (dir) {
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
    public BlockState cycle(Level world, BlockPos pos, BlockState state, boolean inverse) {
        return state.setValue(variant, IVariantBlock.cycleInt(state.getValue(variant), variants - 1, inverse));
    }

    public ResourceLocation modelVariant(int variant, Direction direction) {
        return modelVariants[variant];
    }

    @Override
    public String[] getVariants(BlockState state) {
        String[] arr = new String[modelVariants.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = modelVariants[i].toString();
        }
        return arr;
    }

}
