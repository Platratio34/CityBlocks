package com.peter.cityblocks.gui;

import net.minecraft.screen.ScreenHandlerType;

public class CityBlocksScreenHandlers {

    public static final ScreenHandlerType<CustomSignScreenHandler> CUSTOM_SIGN_SCREEN_HANDLER = CustomSignScreenHandler.TYPE;
    public static final ScreenHandlerType<SignalHeadScreenHandler> SIGNAL_HEAD_SCREEN_HANDLER = SignalHeadScreenHandler.TYPE;
    public static final ScreenHandlerType<SignalControllerScreenHandler> SIGNAL_CONTROLLER_SCREEN_HANDLER = SignalControllerScreenHandler.TYPE;

    public static void register() {
    }
}
