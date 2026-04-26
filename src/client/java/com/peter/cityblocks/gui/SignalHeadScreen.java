package com.peter.cityblocks.gui;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.networking.CityBlocksClientNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;

public class SignalHeadScreen extends AbstractContainerScreen<SignalHeadScreenHandler> {
    private static final ResourceLocation TEXTURE = CityBlocks.identifier("textures/gui/signal_head_gui.png");

    private final SignalHeadScreenHandler handler;

    protected CustomTextInput idInput = null;

    public SignalHeadScreen(SignalHeadScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.handler = handler;
        // addSelectableChild(idInput);
        titleLabelY -= 20;
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 2000;
        CityBlocks.debug("Creating signal head screen");
        if (idInput == null) {
            idInput = addRenderableWidget(new CustomTextInput(leftPos + 146, topPos + 19, 3, true));
            idInput.setMaxNum(63);
        }
        if(handler.headEntity != null)
            idInput.setNumber(handler.headEntity.getHeadId());
        else if(handler.pedestrianEntity != null)
            idInput.setNumber(handler.pedestrianEntity.getHeadId());
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        // TODO put this back?
        // RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        // RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        // RenderSystem.setShaderTexture(0, TEXTURE);

        context.blit(RenderPipelines.BLOCK_SCREEN_EFFECT, TEXTURE, leftPos, topPos, 0, 0, 150, 150, 150, 150);

        context.drawString(font, "ID: ", leftPos + 130, topPos + 20, CommonColors.RED, true);
        

        if (handler.headEntity != null) {
            int numLamps = handler.headEntity.getLampCount();

            drawLampData(context, 0, 61, 25);
            if(numLamps >= 2)
                drawLampData(context, 1, 61, 70);
            if(numLamps >= 3)
                drawLampData(context, 2, 61, 115);
            
            context.drawString(font, numLamps+"", leftPos + 20, topPos + 20, CommonColors.WHITE, true);
        }
        if (handler.pedestrianEntity != null) {
            int state = handler.pedestrianEntity.getState();
            String text = "";
            int color = CommonColors.WHITE;
            switch (state) {
                case PedestrianSignalBlockEntity.OFF_STATE:
                    text = "off";
                    color = CommonColors.WHITE;
                    break;
                case PedestrianSignalBlockEntity.STOP_STATE:
                    text = "stop";
                    color = CommonColors.RED;
                    break;
                case PedestrianSignalBlockEntity.FLASH_STATE:
                    text = "flash";
                    color = CommonColors.YELLOW;
                    break;
                case PedestrianSignalBlockEntity.WALK_STATE:
                    text = "walk";
                    color = CommonColors.GREEN;
                    break;

                default:
                    break;
            }
            context.drawString(font, text, leftPos + 61, topPos + 25, color, true);
        }
    }

    private void drawLampData(GuiGraphics context, int lamp, int lX, int lY) {
        LampColor lampColor = handler.headEntity.getColor(lamp);
        LampState lampState = handler.headEntity.getState(lamp);
        int color = CommonColors.RED;
        switch (lampColor) {
            case LampColor.AMBER:
                color = CommonColors.YELLOW;
                break;
            case LampColor.GREEN:
                color = CommonColors.GREEN;
                break;

            default:
                break;
        }
        context.drawString(font, lampState.name, leftPos + lX, topPos + lY, color, true);
        context.drawString(font, lampColor.name(), leftPos + lX, topPos + lY + 12, color, true);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (idInput.keyPressed(keyCode, scanCode, modifiers)) {
            if (idInput.hasChanged()) {
                CityBlocksClientNetworking.sendHeadIDUpdate(handler.headEntity, idInput.getNumber());
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        idInput.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (idInput.charTyped(chr, modifiers))
            return true;
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (idInput.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        mouseX -= leftPos;
        mouseY -= topPos;
        // System.out.println(String.format("Click: %f,%f", mouseX, mouseY));

        if (mouseX > 60 && mouseX < 90 && handler.headEntity != null) {
            int numLamps = handler.headEntity.getLampCount();
            if (mouseY > 15 && mouseY < 45 && numLamps >= 1) {
                if (mouseY < 35) {
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
                } else {
                    int state = handler.headEntity.getColor(0).code;
                    if (button == 0) {
                        state++;
                    } else {
                        state--;
                    }
                    if (state > LampColor.MAX_CODE) {
                        state = 0;
                    } else if (state < 0) {
                        state = LampColor.MAX_CODE;
                    }
                    CityBlocksClientNetworking.sendHeadColorUpdate(handler.headEntity, 0, LampColor.fromCode(state));
                }
            } else if (mouseY > 60 && mouseY < 90 && numLamps >= 2) {
                if (mouseY < 80) {
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
                } else {
                    int state = handler.headEntity.getColor(1).code;
                    if (button == 0) {
                        state++;
                    } else {
                        state--;
                    }
                    if (state > LampColor.MAX_CODE) {
                        state = 0;
                    } else if (state < 0) {
                        state = LampColor.MAX_CODE;
                    }
                    CityBlocksClientNetworking.sendHeadColorUpdate(handler.headEntity, 1, LampColor.fromCode(state));
                }
            } else if (mouseY > 105 && mouseY < 135 && numLamps >= 3) {
                if (mouseY < 125) {
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
                } else {
                    int state = handler.headEntity.getColor(2).code;
                    if (button == 0) {
                        state++;
                    } else {
                        state--;
                    }
                    if (state > LampColor.MAX_CODE) {
                        state = 0;
                    } else if (state < 0) {
                        state = LampColor.MAX_CODE;
                    }
                    CityBlocksClientNetworking.sendHeadColorUpdate(handler.headEntity, 2, LampColor.fromCode(state));
                }
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
        } else if (mouseX > 10 && mouseX < 50 && mouseY > 15 && mouseY < 45 && handler.headEntity != null) { // change numLamps
            int numLamps = handler.headEntity.getLampCount();
            if (numLamps == 1) { // TODO change this if 2 lamp head is added
                numLamps = 3;
            } else {
                numLamps = 1;
            }
            // if (button == 0) {
            //     numLamps++;
            // } else {
            //     numLamps--;
            // }
            // if (numLamps < 1) {
            //     numLamps = 1;
            // } else if (numLamps > SignalHeadBlock.MAX_LAMPS) {
            //     numLamps = 3;
            // }
            CityBlocksClientNetworking.sendHeadLampCountUpdate(handler.headEntity, numLamps);
        } 
        return true;
    }

}
