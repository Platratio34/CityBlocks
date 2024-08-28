package com.peter.networking;

import com.peter.blocks.signal.SignalNetworking;

public class CityBlocksNetworking {

    public static void registerServer() {
        SignalNetworking.registerServer();
        CustomSignUpdatePayload.registerServer();
        PedestrianSignalUpdatePayload.registerServer();
    }
    
}
