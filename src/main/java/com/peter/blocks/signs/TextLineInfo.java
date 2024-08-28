package com.peter.blocks.signs;

import org.joml.Vector3f;

import net.minecraft.util.Colors;

public class TextLineInfo {
    
    public final int lineN;
    public final Vector3f position;
    public final float scale;
    public int color = Colors.BLACK;
    public float rotation = 0;
    public String defaultText = null;
    public float maxWidth = -1;
    public boolean centered = true;

    public TextLineInfo(int lineN, Vector3f position, float scale) {
        this.lineN = lineN;
        this.position = position;
        this.scale = scale;
    }

    public TextLineInfo(Vector3f position, float scale) {
        this(0, position, scale);
    }

    public TextLineInfo(float x, float y, float z, float scale) {
        this(0, new Vector3f(x, y, z), scale);
    }

    public TextLineInfo(int lineN, float x, float y, float z, float scale) {
        this(lineN, new Vector3f(x, y, z), scale);
    }

    public TextLineInfo(int lineN, Vector3f position, float scale, boolean centered, int color, float rotation, float maxWidth) {
        this.lineN = lineN;
        this.position = position;
        this.scale = scale;
        centered(centered);
        color(color);
        rotation(rotation);
        maxWidth(maxWidth);
    }


    public TextLineInfo defaultText(String defaultText) {
        this.defaultText = defaultText;
        return this;
    }

    public TextLineInfo maxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public TextLineInfo color(int color) {
        this.color = color;
        return this;
    }

    public TextLineInfo rotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    public TextLineInfo centered(boolean centered) {
        this.centered = centered;
        return this;
    }

}
