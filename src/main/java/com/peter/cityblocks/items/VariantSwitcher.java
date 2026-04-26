package com.peter.cityblocks.items;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.gui.VariantSwitcherScreenHandler;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class VariantSwitcher extends Item implements ExtendedScreenHandlerFactory<VariantSwitcherScreenPacket> {

    public static final ResourceLocation ID = CityBlocks.identifier("variant_switcher");
    public static final Item ITEM = Registry.register(BuiltInRegistries.ITEM, ID, new VariantSwitcher(new Item.Properties().setId(Items.irk(ID))));

    public VariantSwitcher(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player playerEntity = context.getPlayer();
        Level world = context.getLevel();
        if (!world.isClientSide && playerEntity != null) {
            BlockPos blockPos = context.getClickedPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (!(blockState.getBlock() instanceof IVariantBlock)) {
                return InteractionResult.PASS;
            }
            if (!this.use(playerEntity, blockState, world, blockPos, context.getItemInHand())) {
                return InteractionResult.FAIL;
            }
        }

        return world.isClientSide ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }
    
    private BlockPos lastHit = null;
    private boolean use(Player player, BlockState state, Level world, BlockPos pos, ItemStack stack) {
        if (!player.isSecondaryUseActive()) {
            MenuProvider screenHandlerFactory = this;
            lastHit = pos;

            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
            return true;
        }
        world.setBlockAndUpdate(pos, ((IVariantBlock)state.getBlock()).cycle(world, pos, state, false));
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Variant Switcher");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new VariantSwitcherScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayer)player));
    }

    @Override
    public VariantSwitcherScreenPacket getScreenOpeningData(ServerPlayer player) {
        return new VariantSwitcherScreenPacket(player.level().dimension(), lastHit, -1);
    }

}
