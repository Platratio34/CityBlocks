package com.peter.cityblocks.gui;

import net.minecraft.client.gui.screens.MenuScreens;

public class CityBlocksScreens {

    public static void register() {

        MenuScreens.register(SignalHeadScreenHandler.TYPE, SignalHeadScreen::new);

        MenuScreens.register(SignalControllerScreenHandler.TYPE, SignalControllerScreen::new);

        MenuScreens.register(CustomSignScreenHandler.TYPE, CustomSignScreen::new);
        
        MenuScreens.register(VariantSwitcherScreenHandler.TYPE, VariantSwitcherScreen::new);
        
    }
}
