package com.peter.cityblocks.gui;

import org.lwjgl.glfw.GLFW;

import com.peter.cityblocks.blocks.signs.CustomSignBlockEntity;
import com.peter.cityblocks.blocks.signs.TextLineInfo;
import com.peter.cityblocks.networking.CityBlocksClientNetworking;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class CustomSignScreen extends HandledScreen<CustomSignScreenHandler> {

    @SuppressWarnings("unused")
    private final CustomSignScreenHandler handler;
    private final CustomSignBlockEntity signEntity;

    private int selectedLineN = -1;
    private String selectedLine = "";

    public CustomSignScreen(CustomSignScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
        this.signEntity = handler.signEntity;
    }

    @Override
    protected void init() {
        super.init();
        playerInventoryTitleY = 2000;
        titleY -= 10;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        
        // if (!signEntity.isTextOnly()) {
            String[] varNames = signEntity.getVariantNames();
            int var = signEntity.getVariant();
            context.drawText(textRenderer, "Variants:", x - 25, y + 5, Colors.WHITE, true);
            for (int i = 0; i < varNames.length; i++) {
                String texture = varNames[i];
                if (texture.length() == 0) {
                    texture = "Text Only ("+signEntity.getMaxTextLines(i)+")";
                }
                context.drawText(textRenderer, texture, x - 25, y + 15 + (i * 10),
                        (var == i) ? Colors.YELLOW : Colors.GREEN, true);
            }
        // }
        
        int nLines = signEntity.getMaxTextLines();
        if (nLines > 0) {
            String[] lines = signEntity.getText();
            TextLineInfo[] lineInfos = signEntity.getTextInfo();
            context.drawText(textRenderer, "Text:", x + 75, y + 5, Colors.WHITE, true);
            for (int i = 0; i < nLines; i++) {
                int color = lineInfos[i].color;
                if (color == Colors.BLACK)
                    color = Colors.WHITE;
                if (i == selectedLineN)
                    color = Colors.YELLOW;
                
                context.drawText(textRenderer, i + ": \"" + lines[i] + "\"", x + 75, y + 15 + (i * 10), color, true);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX -= x;
        mouseY -= y;
        // System.out.println(String.format("Click: %f,%f", mouseX, mouseY));

        if (mouseX > -25 && mouseX < 60 && mouseY > 15) {
            int i = (int) ((mouseY - 15) / 10d);
            if (i <= signEntity.getMaxVariant()) {
                CityBlocksClientNetworking.sendCustomSignVariantUpdate(signEntity, i);
            }
        }
        if (mouseX > 75 && mouseX < 175 && mouseY > 15) {
            int nLines = signEntity.getMaxTextLines();
            if (nLines > 0) {
                int i = (int) ((mouseY - 15) / 10d);
                if (i >= 0 && i < nLines) {
                    selectedLineN = i;
                    selectedLine = signEntity.getLineText(i);
                }
            }
        } else {
            selectedLineN = -1;
        }

        return true;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (selectedLineN >= 0 && selectedLine.length() > 0) {
                selectedLine = selectedLine.substring(0, selectedLine.length() - 1);
                CityBlocksClientNetworking.sendCustomSignTextUpdate(signEntity, selectedLineN, selectedLine);
            }
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            if (selectedLineN < signEntity.getMaxTextLines() - 1) {
                selectedLineN++;
                selectedLine = signEntity.getLineText(selectedLineN);
            }
        } else if (keyCode == GLFW.GLFW_KEY_UP) {
            if (selectedLineN > -1) {
                selectedLineN--;
                selectedLine = signEntity.getLineText(selectedLineN);
            }
        }
        if (selectedLineN >= 0 && keyCode == GLFW.GLFW_KEY_E) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (selectedLineN >= 0) {
            selectedLine += chr;
            CityBlocksClientNetworking.sendCustomSignTextUpdate(signEntity, selectedLineN, selectedLine);
        }
        return super.charTyped(chr, modifiers);
    }

}
