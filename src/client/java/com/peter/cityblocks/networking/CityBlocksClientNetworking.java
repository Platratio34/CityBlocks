package com.peter.cityblocks.networking;

import org.apache.commons.lang3.StringUtils;

import com.peter.cityblocks.blocks.signal.LampColor;
import com.peter.cityblocks.blocks.signal.LampState;
import com.peter.cityblocks.blocks.signal.PedestrianSignalBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalControllerBlockEntity;
import com.peter.cityblocks.blocks.signal.SignalHeadBlockEntity;
import com.peter.cityblocks.blocks.signs.CustomSignBlockEntity;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CityBlocksClientNetworking {

    public static void sendHeadUpdate(SignalHeadBlockEntity entity, int lamp, LampState newState, LampColor newColor,
            int newHeadId) {
            ClientPlayNetworking.send(new SignalHeadUpdatePayload(entity.getWorld().getRegistryKey(), entity.getPos(), lamp,
                newState.code, newColor.code, newHeadId));
    }
    public static void sendHeadStateUpdate(SignalHeadBlockEntity entity, int lamp, LampState newState) {
        sendHeadUpdate(entity, lamp, newState, LampColor.NULL, -1);
    }

    public static void sendHeadColorUpdate(SignalHeadBlockEntity entity, int lamp, LampColor newColor) {
        sendHeadUpdate(entity, lamp, LampState.NULL, newColor, -1);
    }

    public static void sendHeadIDUpdate(SignalHeadBlockEntity entity, int newHeadId) {
        sendHeadUpdate(entity, -1, LampState.NULL, LampColor.NULL, newHeadId);
    }

    public static void sendControllerCycleModeUpdate(SignalControllerBlockEntity entity, int newCycleMode) {
        ClientPlayNetworking.send(
                new SignalControllerUpdatePayload(entity.getWorld().getRegistryKey(), entity.getPos(), newCycleMode));
    }
    

    public static void sendCustomSignVariantUpdate(CustomSignBlockEntity signEntity, int variant) {
        sendCustomSignUpdate(signEntity, variant, false, null);
    }

    public static void sendCustomSignTextUpdate(CustomSignBlockEntity signEntity, String[] text) {
        sendCustomSignUpdate(signEntity, -1, true, text);
    }

    public static void sendCustomSignTextUpdate(CustomSignBlockEntity signEntity, int lineN, String line) {
        String[] text = signEntity.getText();
        text[lineN] = line;
        sendCustomSignTextUpdate(signEntity, text);
    }

    public static void sendCustomSignUpdate(CustomSignBlockEntity signEntity, int variant, boolean updateText,
            String[] text) {
        ClientPlayNetworking.send(new CustomSignUpdatePayload(signEntity.getWorld().getRegistryKey(),
                signEntity.getPos(), variant, updateText, updateText ? StringUtils.join(text, "\n") : ""));
    }
    
    
    public static void sendPedestrianUpdate(PedestrianSignalBlockEntity entity, int headId, int state) {
        ClientPlayNetworking.send(
                new PedestrianSignalUpdatePayload(entity.getWorld().getRegistryKey(), entity.getPos(), headId, state));
    }

    public static void sendPedestrianHeadIdUpdate(PedestrianSignalBlockEntity entity, int headId) {
        sendPedestrianUpdate(entity, headId, -1);
    }
    public static void sendPedestrianStateUpdate(PedestrianSignalBlockEntity entity, int state) {
        sendPedestrianUpdate(entity, -1, state);
    }
}
