package com.peter.cityblocks.blocks.signal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;

public class CycleState {

    public LampState[][] states;

    public int time;

    public CycleState() {
        states = new LampState[SignalControllerBlockEntity.MAX_HEADS][];
        for (int i = 0; i < states.length; i++) {
            states[i] = new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF };
        }
        time = -1;
    }

    public CycleState(int time) {
        this();
        this.time = time;
    }

    public CycleState(int time, LampState[] defaultState) {
        states = new LampState[SignalControllerBlockEntity.MAX_HEADS][];
        for (int i = 0; i < states.length; i++) {
            states[i] = defaultState.clone();
        }
        this.time = time;
    }

    public void set(int head, LampState[] lampStates) {
        this.states[head] = lampStates.clone();
    }

    public LampState[] getState(int head) {
        return states[head];
    }

    private static final String NBT_TIME = "time";
    private static final String NBT_STATES = "states";

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(NBT_TIME, time);

        ListTag statesNbt = new ListTag();
        nbt.put(NBT_STATES, statesNbt);
        for (int i = 0; i < states.length; i++) {
            statesNbt.add(new IntArrayTag(LampState.toIntArray(states[i])));
        }

        return nbt;
    }

    public static CycleState fromNbt(CompoundTag nbt) {
        CycleState state = new CycleState();
        if (nbt.contains(NBT_TIME)) {
            state.time = nbt.getInt(NBT_TIME).get();
        }
        if (nbt.contains(NBT_STATES)) {
            ListTag stateList = (ListTag)nbt.get(NBT_STATES);
            for (int i = 0; i < state.states.length; i++) {
                state.states[i] = LampState.fromIntArray(stateList.getIntArray(i).get());
            }
        }
        return state;
    }

}
