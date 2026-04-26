package com.peter.cityblocks.gui;

import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import com.peter.cityblocks.networking.CityBlocksClientNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;

public class SignalControllerScreen extends AbstractContainerScreen<SignalControllerScreenHandler> {

    private final SignalControllerScreenHandler handler;

    public SignalControllerScreen(SignalControllerScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelY -= 20;
        inventoryLabelY = 2000;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {

        context.drawString(font, String.format("Mode: %s", handler.controller.getCycleModeName()), leftPos, topPos, CommonColors.GREEN, true);
        if (handler.controller.getCycleMode() >= 0) {
            context.drawString(font, String.format("Phase: %d / %d", handler.controller.getCyclePhase(),
                    handler.controller.getCyclePhaseMax()), leftPos, topPos + 10, CommonColors.GREEN, true);
            context.drawString(font,
                    String.format("Time: %d / %d", handler.controller.getCycleTime(),
                            handler.controller.getCycleTimeMax()),
                    leftPos, topPos + 20, CommonColors.GREEN, true);
        }

        for (int i = 0; i < SignalControllerBlockEntity.MAX_HEADS; i++) {
            LampState[] state = handler.controller.getStates(i);
            context.drawString(font,
                    String.format("%02d: %d, %d, %d", i, state[0].code, state[1].code, state[2].code), leftPos + 200,
                    10 + (i * 10), CommonColors.WHITE, true);
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX -= leftPos;
        mouseY -= topPos;
        // System.out.println(String.format("Click: %f,%f", mouseX, mouseY));

        if (mouseX > 0 && mouseX < 100 && mouseY > 0 && mouseY < 8) {
            int cycle = handler.controller.getCycleMode();
            if (button == 0) {
                cycle++;
                if (cycle > handler.controller.getCycleModeMax()) {
                    cycle = -1;
                }
            } else {
                cycle--;
                if (cycle < -1) {
                    cycle = handler.controller.getCycleModeMax();
                }
            }
            CityBlocksClientNetworking.sendControllerCycleModeUpdate(handler.controller, cycle);
        }

        return true;
    }

}
