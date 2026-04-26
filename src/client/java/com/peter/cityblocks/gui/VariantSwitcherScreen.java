package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class VariantSwitcherScreen extends AbstractContainerScreen<VariantSwitcherScreenHandler> {

    private final VariantSwitcherScreenHandler handler;
    private final Level world;

    public VariantSwitcherScreen(VariantSwitcherScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.handler = handler;
        titleLabelY -= 10;
        world = inventory.player.level();
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 2000;
        CityBlocks.debug("Creating variant switcher screen");
    }

    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {

        BlockState state = world.getBlockState(handler.blockPos);
        IVariantBlock vBlock = (IVariantBlock) state.getBlock();
        if (vBlock == null) {
            onClose();
            return;
        }
        context.drawString(font, "Variants:", leftPos - 25, topPos + 5, CommonColors.WHITE, true);
        String[] vars = vBlock.getVariants(state);
        for (int i = 0; i < vars.length; i++) {
            context.drawString(font, vars[i], leftPos - 25, topPos + 15 + (i * 10),
                    (vBlock.getVariant(state) == i) ? CommonColors.YELLOW : CommonColors.GREEN, true);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX -= leftPos;
        mouseY -= topPos;

        BlockState state = world.getBlockState(handler.blockPos);
        IVariantBlock vBlock = (IVariantBlock) state.getBlock();
        if (vBlock == null) {
            onClose();
            return true;
        }
        String[] vars = vBlock.getVariants(state);

        if (mouseX > -25 && mouseY > 15) {
            int i = (int) ((mouseY - 15) / 10d);
            if (i < vars.length && mouseX < font.width(vars[i]) - 25) {
                ClientPlayNetworking.send(new VariantSwitcherScreenPacket(world.dimension(), handler.blockPos, i));
                return true;
            }
        }
        return false;
    }
}
