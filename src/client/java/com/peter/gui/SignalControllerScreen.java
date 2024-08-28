package com.peter.gui;

import com.peter.blocks.signal.LampState;
import com.peter.blocks.signal.SignalControllerBlockEntity;
import com.peter.networking.CityBlocksClientNetworking;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class SignalControllerScreen extends HandledScreen<SignalControllerScreenHandler> {

    private final SignalControllerScreenHandler handler;

    public SignalControllerScreen(SignalControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void init() {
        super.init();
        titleY -= 20;
        playerInventoryTitleY = 2000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        context.drawText(textRenderer, String.format("Mode: %s", handler.controller.getCycleModeName()), x, y, Colors.GREEN, true);
        if (handler.controller.getCycleMode() >= 0) {
            context.drawText(textRenderer, String.format("Phase: %d / %d", handler.controller.getCyclePhase(),
                    handler.controller.getCyclePhaseMax()), x, y + 10, Colors.GREEN, true);
            context.drawText(textRenderer,
                    String.format("Time: %d / %d", handler.controller.getCycleTime(),
                            handler.controller.getCycleTimeMax()),
                    x, y + 20, Colors.GREEN, true);
        }

        for (int i = 0; i < SignalControllerBlockEntity.MAX_HEADS; i++) {
            LampState[] state = handler.controller.getStates(i);
            context.drawText(textRenderer,
                    String.format("%02d: %d, %d, %d", i, state[0].code, state[1].code, state[2].code), x + 200,
                    10 + (i * 10), Colors.WHITE, true);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX -= x;
        mouseY -= y;
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
