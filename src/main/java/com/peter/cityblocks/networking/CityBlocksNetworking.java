package com.peter.cityblocks.networking;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.blocks.signal.SignalNetworking;

public class CityBlocksNetworking {

    public static void registerServer() {
        SignalNetworking.registerServer();
        CustomSignUpdatePayload.registerServer();
        PedestrianSignalUpdatePayload.registerServer();
        CityBlocks.LOGGER.info("City Block networking receivers registered");
    }
    
}
