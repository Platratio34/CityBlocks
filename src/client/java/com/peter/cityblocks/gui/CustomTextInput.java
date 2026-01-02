package com.peter.cityblocks.gui;

import org.lwjgl.glfw.GLFW;

import com.peter.cityblocks.CityBlocks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class CustomTextInput extends ClickableWidget {

    protected String text = "";
    protected int maxLength = 16;
    protected boolean numberOnly = false;
    protected int num = 0;
    protected int maxNum = 999;
    protected boolean changed = false;

    protected TextRenderer textRenderer;

    public CustomTextInput(int x, int y, int maxLength) {
        this(x, y, maxLength, false);
    }
    
    public CustomTextInput(int x, int y, int maxLength, boolean numberOnly) {
        super(x, y, maxLength * 8, 10, Text.of(""));
        this.maxLength = maxLength;
        this.numberOnly = numberOnly;
        textRenderer = MinecraftClient.getInstance().textRenderer;
        CityBlocks.debug("Creating custom text input");
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    float t = 0;
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int x = getX();
        int y = getY();
        context.drawText(textRenderer, text, x, y, Colors.WHITE, true);
        context.drawBorder(x - 2, y - 2, getWidth() + 4, getHeight() + 4, Colors.ALTERNATE_WHITE);

        t += deltaTicks;
        if (isFocused()) {
            if (t % 20 <= 10) {
                context.drawText(textRenderer, "_", x + (textRenderer.getWidth(text)) + 1, y, Colors.LIGHT_GRAY, true);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY) && isFocused()) {
            setFocused(false);
            playClickSound(MinecraftClient.getInstance().getSoundManager());
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public void onClick(double mouseX, double mouseY) {
        if (!isFocused()) {
            setFocused(true);
            CityBlocks.LOGGER.info("focusing");
        }
        CityBlocks.LOGGER.info("clicked");
    }

    @Override
    protected boolean isValidClickButton(int button) {
        return true;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        changed = false;
        if (isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    if (numberOnly) {
                        num /= 10;
                    }
                }
                return true;
            } else if (keyCode == GLFW.GLFW_KEY_ENTER) {
                if (numberOnly && num > maxNum) {
                    num = maxNum;
                    text = num + "";
                }
                changed = true;
                setFocused(false);
                return true;
            } else if (keyCode == GLFW.GLFW_KEY_UP) {
                if (numberOnly) {
                    if (num < maxNum) {
                        num++;
                        text = num + "";
                    }
                    return true;
                }
            } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
                if (numberOnly) {
                    if (num > 0) {
                        num--;
                        text = num + "";
                    }
                    return true;
                }
            } else if (keyCode == GLFW.GLFW_KEY_E) {
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        changed = false;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        changed = false;
        if (isFocused()) {
            if (text.length() < maxLength) {
                if (numberOnly) {
                    if ('0' <= chr && chr <= '9') {
                        num *= 10;
                        num += (chr - '0');
                        text = num+"";
                    }
                } else {
                    text += chr;
                }
            }
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    public int getNumber() {
        return num;
    }

    public void setNumber(int number) {
        if (!numberOnly)
            return;
        num = number;
        text = num + "";
    }

    public void setText(String text) {
        if (numberOnly)
            return;
        this.text = text;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void setMaxNum(int newMax) {
        maxNum = newMax;
        if (!numberOnly)
            return;
        if (num > newMax) {
            num = newMax;
            text = num + "";
        }
    }

}
