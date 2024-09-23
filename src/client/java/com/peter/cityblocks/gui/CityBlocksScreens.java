package com.peter.cityblocks.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class CityBlocksScreens {

    public static void register() {

        HandledScreens.register(SignalHeadScreenHandler.TYPE, SignalHeadScreen::new);

        HandledScreens.register(SignalControllerScreenHandler.TYPE, SignalControllerScreen::new);

        HandledScreens.register(CustomSignScreenHandler.TYPE, CustomSignScreen::new);
        
    }
}
