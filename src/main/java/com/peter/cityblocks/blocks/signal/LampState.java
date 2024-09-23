package com.peter.cityblocks.blocks.signal;

public enum LampState {

    OFF(0, "lamp_off", false, "off"),
    SOLID(1, "lamp_%s", false, "solid"),
    LEFT(2, "lamp_%s_left", false, "left"),
    RIGHT(3, "lamp_%s_right", false, "right"),
    SOLID_FLASH(4, "lamp_%s", true, "solid_flash"),
    LEFT_FLASH(5, "lamp_%s_left", true, "left_flash"),
    RIGHT_FLASH(6, "lamp_%s_right", true, "right_flash"),
    NULL(-1,"",false,"null");

    private static LampState[] STATES = new LampState[] {
            OFF, SOLID, LEFT, RIGHT, SOLID_FLASH, LEFT_FLASH, RIGHT_FLASH
    };

    public int code;
    public String texture;
    public boolean flash;
    public String name;

    public static final int MAX_CODE = 6;

    private LampState(int code, String texture, boolean flash, String name) {
        this.code = code;
        this.texture = texture;
        this.flash = flash;
        this.name = name;
    }

    public static String getTexture(LampState state, LampColor color) {
        return String.format(state.texture, color.color);
    }

    public static LampState fromCode(int code) {
        if (code < 0 || code > STATES.length)
            return NULL;
        return STATES[code];
    }

    public static int[] toIntArray(LampState[] states) {
        int[] arr = new int[states.length];
        for (int i = 0; i < states.length; i++) {
            arr[i] = states[i].code;
        }
        return arr;
    }

    public static LampState[] fromIntArray(int[] states) {
        LampState[] arr = new LampState[states.length];
        for (int i = 0; i < states.length; i++) {
            arr[i] = fromCode(states[i]);
        }
        return arr;
    }
}
