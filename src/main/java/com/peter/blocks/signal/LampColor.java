package com.peter.blocks.signal;

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

    private LampColor(int code, String color) {
        this.code = code;
        this.color = color;
    }

    public static LampColor fromCode(int code) {
        return COLORS[code];
    }
}
