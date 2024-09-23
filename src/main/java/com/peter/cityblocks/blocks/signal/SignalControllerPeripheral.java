package com.peter.cityblocks.blocks.signal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.LuaTable;
import dan200.computercraft.api.peripheral.IPeripheral;

public class SignalControllerPeripheral implements IPeripheral {

    protected final SignalControllerBlockEntity controller;

    public SignalControllerPeripheral(SignalControllerBlockEntity controller) {
        this.controller = controller;
    }

    @Override
    public Object getTarget() {
        return controller;
    }

    @Override
    public String getType() {
        return "signal_controller";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        if (other == null)
            return false;
        if (!other.getType().equals(getType()))
            return false;
        if (!((SignalControllerPeripheral) other).controller.getPos().equals(controller.getPos()))
            return false;
        return true;
    }

    @LuaFunction(unsafe = true)
    public final void setHeadState(ILuaContext context, int headId, LuaTable<?, ?> stateTable) throws LuaException {
        if (headId < 0 || headId >= SignalControllerBlockEntity.MAX_HEADS) {
            throw new LuaException(
                    String.format("`headId` must be between 0 and %d inclusive",
                            SignalControllerBlockEntity.MAX_HEADS));
        }
        if (stateTable.length() != 3)
            throw new LuaException("`state` must be an array of length 3");
        int[] state = new int[3];
        for (int i = 0; i < state.length; i++) {
            try {
                state[i] = stateTable.getInt(i + 1);
            } catch (LuaException e) {
                throw new LuaException(String.format("`state` must be an array integers; index %d was a %s", i,
                        stateTable.get(i + 1).getClass().getSimpleName()));
            }
            if (state[i] < 0 || state[i] > LampState.MAX_CODE) {
                throw new LuaException(String.format("All values in `state` but be between 0-%d inclusive: index %d was %d", LampState.MAX_CODE, i, state[i]));
            }
        }
        controller.api_setHeadState(headId, state);
    }
    
    @LuaFunction
    public final void setErrorState() {
        for (int i = 0; i < SignalControllerBlockEntity.MAX_HEADS; i++) {
            controller.api_setHeadState(i, new int[] { 4, 0, 0 });
        }
    }

    @LuaFunction
    public final Map<String, Integer> getStateConstants(ILuaContext context) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (LampState state : LampState.values()) {
            map.put(state.name, state.code);
        }
        return map;
    }

    @LuaFunction
    public final Map<Integer, Integer> getState(int headId) throws LuaException {
        if (headId < 0 || headId >= SignalControllerBlockEntity.MAX_HEADS) {
            throw new LuaException(
                    String.format("`headId` must be between 0 and %d inclusive",
                            SignalControllerBlockEntity.MAX_HEADS));
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        LampState[] state = controller.getStates(headId);
        for (int i = 0; i < state.length; i++) {
            map.put(i, state[i].code);
        }
        return map;
    }

}
