package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.gui.VariantSwitcherScreenHandler;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VariantSwitcher extends Item implements ExtendedScreenHandlerFactory<VariantSwitcherScreenPacket> {

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
    
    private BlockPos lastHit = null;
    private boolean use(PlayerEntity player, BlockState state, World world, BlockPos pos, ItemStack stack) {
        if (!player.shouldCancelInteraction()) {
            NamedScreenHandlerFactory screenHandlerFactory = this;
            lastHit = pos;

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
            return true;
        }
        world.setBlockState(pos, ((IVariantBlock)state.getBlock()).cycle(world, pos, state, false));
        return true;
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Variant Switcher");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new VariantSwitcherScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayerEntity)player));
    }

    @Override
    public VariantSwitcherScreenPacket getScreenOpeningData(ServerPlayerEntity player) {
        return new VariantSwitcherScreenPacket(player.getWorld().getRegistryKey(), lastHit, -1);
    }

}
