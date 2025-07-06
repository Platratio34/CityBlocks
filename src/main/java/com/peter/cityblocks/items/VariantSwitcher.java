package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VariantSwitcher extends Item {

    public static final Identifier ID = CityBlocks.identifier("variant_switcher");
    public static final Item ITEM = Registry.register(Registries.ITEM, ID, new VariantSwitcher(new Item.Settings().registryKey(Items.irk(ID))));

    public VariantSwitcher(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        if (!world.isClient && playerEntity != null) {
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (!(blockState.getBlock() instanceof IVariantBlock)) {
                return ActionResult.PASS;
            }
            if (!this.use(playerEntity, blockState, world, blockPos, context.getStack())) {
                return ActionResult.FAIL;
            }
        }

        return world.isClient ? ActionResult.SUCCESS : ActionResult.FAIL;
    }
    
    private boolean use(PlayerEntity player, BlockState state, World world, BlockPos pos, ItemStack stack) {
        world.setBlockState(pos, ((IVariantBlock)state.getBlock()).cycle(world, pos, state, player.shouldCancelInteraction()));
        return true;
    }

}
