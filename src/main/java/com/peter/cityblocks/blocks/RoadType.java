package com.peter.cityblocks.blocks;

import net.minecraft.util.Identifier;

public enum RoadType {

    BLACKSTONE(Identifier.ofVanilla("block/blackstone_top"), Identifier.ofVanilla("block/blackstone"), ""),
    ANDESITE(Identifier.ofVanilla("block/polished_andesite"), Identifier.ofVanilla("block/polished_andesite"), "_andesite");

    public final Identifier topTexture;
    public final Identifier baseTexture;
    public final String extension;

    private RoadType(Identifier topTexture, Identifier baseTexture, String extension) {
        this.topTexture = topTexture;
        this.baseTexture = baseTexture;
        this.extension = extension;
    }
}
