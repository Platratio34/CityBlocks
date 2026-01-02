package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.IVariantBlock;
import com.peter.cityblocks.networking.VariantSwitcherScreenPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.world.World;

public class VariantSwitcherScreen extends HandledScreen<VariantSwitcherScreenHandler> {

    private final VariantSwitcherScreenHandler handler;
    private final World world;

    public VariantSwitcherScreen(VariantSwitcherScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
        titleY -= 10;
        world = inventory.player.getWorld();
    }

    @Override
    protected void init() {
        super.init();
        playerInventoryTitleY = 2000;
        CityBlocks.debug("Creating variant switcher screen");
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {

        BlockState state = world.getBlockState(handler.blockPos);
        IVariantBlock vBlock = (IVariantBlock) state.getBlock();
        if (vBlock == null) {
            close();
            return;
        }
        context.drawText(textRenderer, "Variants:", x - 25, y + 5, Colors.WHITE, true);
        String[] vars = vBlock.getVariants(state);
        for (int i = 0; i < vars.length; i++) {
            context.drawText(textRenderer, vars[i], x - 25, y + 15 + (i * 10),
                    (vBlock.getVariant(state) == i) ? Colors.YELLOW : Colors.GREEN, true);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX -= x;
        mouseY -= y;

        BlockState state = world.getBlockState(handler.blockPos);
        IVariantBlock vBlock = (IVariantBlock) state.getBlock();
        if (vBlock == null) {
            close();
            return true;
        }
        String[] vars = vBlock.getVariants(state);

        if (mouseX > -25 && mouseY > 15) {
            int i = (int) ((mouseY - 15) / 10d);
            if (i < vars.length && mouseX < textRenderer.getWidth(vars[i]) - 25) {
                ClientPlayNetworking.send(new VariantSwitcherScreenPacket(world.getRegistryKey(), handler.blockPos, i));
                return true;
            }
        }
        return false;
    }
}
