package com.peter.cityblocks.gui;

import net.minecraft.world.inventory.MenuType;

public class CityBlocksScreenHandlers {

    public static final MenuType<CustomSignScreenHandler> CUSTOM_SIGN_SCREEN_HANDLER = CustomSignScreenHandler.TYPE;
    public static final MenuType<SignalHeadScreenHandler> SIGNAL_HEAD_SCREEN_HANDLER = SignalHeadScreenHandler.TYPE;
    public static final MenuType<SignalControllerScreenHandler> SIGNAL_CONTROLLER_SCREEN_HANDLER = SignalControllerScreenHandler.TYPE;
    public static final MenuType<VariantSwitcherScreenHandler> VARIAN_SWITCHER_SCREEN_HANDLER = VariantSwitcherScreenHandler.TYPE;

    public static void register() {
    }
}
