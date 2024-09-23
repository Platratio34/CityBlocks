package com.peter.cityblocks.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import com.peter.cityblocks.networking.CityBlocksClientNetworking;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class SignalHeadScreen extends HandledScreen<SignalHeadScreenHandler> {
    private static final Identifier TEXTURE = CityBlocks.identifier("textures/gui/signal_head_gui.png");

    private final SignalHeadScreenHandler handler;

    public SignalHeadScreen(SignalHeadScreenHandler handler, PlayerInventory inventory, Text title) {
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
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        context.drawTexture(TEXTURE, x, y, 0, 0, 150, 150, 150, 150);

        int headId = -1;
        if (handler.headEntity != null)
            headId = handler.headEntity.getHeadId();
        if (handler.pedestrianEntity != null)
            headId = handler.pedestrianEntity.getHeadId();
        context.drawText(textRenderer, "ID: " + headId, x + 130, y + 20, Colors.RED, true);

        if (handler.headEntity != null) {
            drawLampData(context, 0, 61, 25);
            drawLampData(context, 1, 61, 70);
            drawLampData(context, 2, 61, 115);
        }
        if (handler.pedestrianEntity != null) {
            int state = handler.pedestrianEntity.getState();
            String text = "";
            int color = Colors.WHITE;
            switch (state) {
                case PedestrianSignalBlockEntity.OFF_STATE:
                    text = "off";
                    color = Colors.WHITE;
                    break;
                case PedestrianSignalBlockEntity.STOP_STATE:
                    text = "stop";
                    color = Colors.RED;
                    break;
                case PedestrianSignalBlockEntity.FLASH_STATE:
                    text = "flash";
                    color = Colors.YELLOW;
                    break;
                case PedestrianSignalBlockEntity.WALK_STATE:
                    text = "walk";
                    color = Colors.GREEN;
                    break;

                default:
                    break;
            }
            context.drawText(textRenderer, text, x + 61, y + 25, color, true);
        }
    }

    private void drawLampData(DrawContext context, int lamp, int lX, int lY) {
        LampColor lampColor = handler.headEntity.getColor(lamp);
        LampState lampState = handler.headEntity.getState(lamp);
        int color = Colors.RED;
        switch (lampColor) {
            case LampColor.AMBER:
                color = Colors.YELLOW;
                break;
            case LampColor.GREEN:
                color = Colors.GREEN;
                break;

            default:
                break;
        }
        context.drawText(textRenderer, lampState.name, x + lX, y + lY, color, true);
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

        if (mouseX > 60 && mouseX < 90 && handler.headEntity != null) {
            if (mouseY > 15 && mouseY < 45) {
                int state = handler.headEntity.getState(0).code;
                if (button == 0) {
                    state++;
                } else {
                    state--;
                }
                if (state > LampState.MAX_CODE) {
                    state = 0;
                } else if (state < 0) {
                    state = LampState.MAX_CODE;
                }
                CityBlocksClientNetworking.sendHeadStateUpdate(handler.headEntity, 0, LampState.fromCode(state));
            } else if (mouseY > 60 && mouseY < 90) {
                int state = handler.headEntity.getState(1).code;
                if (button == 0) {
                    state++;
                } else {
                    state--;
                }
                if (state > LampState.MAX_CODE) {
                    state = 0;
                } else if (state < 0) {
                    state = LampState.MAX_CODE;
                }
                CityBlocksClientNetworking.sendHeadStateUpdate(handler.headEntity, 1, LampState.fromCode(state));
            } else if (mouseY > 105 && mouseY < 135) {
                int state = handler.headEntity.getState(2).code;
                if (button == 0) {
                    state++;
                } else {
                    state--;
                }
                if (state > LampState.MAX_CODE) {
                    state = 0;
                } else if (state < 0) {
                    state = LampState.MAX_CODE;
                }
                CityBlocksClientNetworking.sendHeadStateUpdate(handler.headEntity, 2, LampState.fromCode(state));
            }
        } else if (mouseX > 60 && mouseX < 90 && handler.pedestrianEntity != null) {
            if (mouseY > 15 && mouseY < 45) {
                int state = handler.pedestrianEntity.getState();
                if (button == 0) {
                    state++;
                } else {
                    state--;
                }
                if (state > 3) {
                    state = 0;
                } else if (state < 0) {
                    state = 3;
                }
                CityBlocksClientNetworking.sendPedestrianStateUpdate(handler.pedestrianEntity, state);
            }
        } else if (mouseX > 130 && mouseX < 170 && mouseY > 20 && mouseY < 28) {
            int headId = -1;
            if (handler.headEntity != null)
                headId = handler.headEntity.getHeadId();
            if (handler.pedestrianEntity != null)
                headId = handler.pedestrianEntity.getHeadId();
            if (button == 0) {
                headId++;
                if (headId >= SignalControllerBlockEntity.MAX_HEADS) {
                    headId = 0;
                }
            } else {
                headId--;
                if (headId < 0) {
                    headId = SignalControllerBlockEntity.MAX_HEADS - 1;
                }
            }
            if (handler.headEntity != null)
                CityBlocksClientNetworking.sendHeadIDUpdate(handler.headEntity, headId);
            if (handler.pedestrianEntity != null)
            CityBlocksClientNetworking.sendPedestrianHeadIdUpdate(handler.pedestrianEntity, headId);
        }
        return true;
    }

}
