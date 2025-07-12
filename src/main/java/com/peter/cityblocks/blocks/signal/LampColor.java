package com.peter.cityblocks.blocks.signal;

public enum LampColor {

    RED(0, "red"),
    AMBER(1, "amber"),
    GREEN(2, "green"),
    NULL(-1, "");

    private static LampColor[] COLORS = new LampColor[] {
        RED, AMBER, GREEN
    };

    public int code;
    public String color;

    public static final int MAX_CODE = 2;

    private LampColor(int code, String color) {
        this.code = code;
        this.color = color;
    }

    public static LampColor fromCode(int code) {
        if (code < 0 || code > MAX_CODE)
            return NULL;
        return COLORS[code];
    }
}
