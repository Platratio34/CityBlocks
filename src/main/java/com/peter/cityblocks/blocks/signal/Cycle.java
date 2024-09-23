package com.peter.cityblocks.blocks.signal;

public class Cycle {

    public static final int MAX_CYCLE = 16;

    public CycleState[] cycleStates;
    public int maxPhase;
    public String name;

    public Cycle(String name) {
        cycleStates = new CycleState[MAX_CYCLE];
        for (int i = 0; i < MAX_CYCLE; i++) {
            cycleStates[i] = new CycleState();
        }
        this.name = name;
    }

    public static Cycle basicSwitchCycle(int mainTime, int amberTime, int waitTime) {
        Cycle cycle = new Cycle("Basic Switch");
        cycle.maxPhase = 5;

        cycle.cycleStates[0].set(0, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[0].time = mainTime;
        cycle.cycleStates[1].set(0, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[1].time = amberTime;
        cycle.cycleStates[2].set(0, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[2].time = waitTime;

        cycle.cycleStates[3].set(1, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[3].time = mainTime;
        cycle.cycleStates[4].set(1, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[4].time = amberTime;
        cycle.cycleStates[5].set(1, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[5].time = waitTime;

        return cycle;
    }

    public static Cycle basic3WayCycle(int mainTime, int amberTime, int waitTime) {
        Cycle cycle = new Cycle("Basic 3 Way");
        cycle.maxPhase = 8;

        cycle.cycleStates[0].set(0, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[0].time = mainTime;
        cycle.cycleStates[1].set(0, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[1].time = amberTime;
        cycle.cycleStates[2].set(0, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[2].time = waitTime;
        
        cycle.cycleStates[3].set(1, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[3].time = mainTime;
        cycle.cycleStates[4].set(1, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[4].time = amberTime;
        cycle.cycleStates[5].set(1, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[5].time = waitTime;

        cycle.cycleStates[6].set(2, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[6].time = mainTime;
        cycle.cycleStates[7].set(2, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[7].time = amberTime;
        cycle.cycleStates[8].set(2, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[8].time = waitTime;

        return cycle;
    }

    public static Cycle basic4WayCycle(int mainTime, int amberTime, int waitTime) {
        Cycle cycle = new Cycle("Basic 4 Way");
        cycle.maxPhase = 11;

        cycle.cycleStates[0].set(0, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[0].time = mainTime;
        cycle.cycleStates[1].set(0, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[1].time = amberTime;
        cycle.cycleStates[2].set(0, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[2].time = waitTime;
        
        cycle.cycleStates[3].set(1, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[3].time = mainTime;
        cycle.cycleStates[4].set(1, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[4].time = amberTime;
        cycle.cycleStates[5].set(1, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[5].time = waitTime;

        cycle.cycleStates[6].set(2, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[6].time = mainTime;
        cycle.cycleStates[7].set(2, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[7].time = amberTime;
        cycle.cycleStates[8].set(2, new LampState[] { LampState.SOLID, LampState.OFF, LampState.OFF });
        cycle.cycleStates[8].time = waitTime;
        
        cycle.cycleStates[9].set(3, new LampState[] { LampState.OFF, LampState.OFF, LampState.SOLID });
        cycle.cycleStates[9].time = mainTime;
        cycle.cycleStates[10].set(3, new LampState[] { LampState.OFF, LampState.SOLID, LampState.OFF });
        cycle.cycleStates[10].time = amberTime;
        cycle.cycleStates[11].set(3, new LampState[] { LampState.OFF, LampState.OFF, LampState.OFF });
        cycle.cycleStates[11].time = waitTime;

        return cycle;
    }
}
