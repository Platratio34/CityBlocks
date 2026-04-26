package com.peter.cityblocks.blocks;

import net.minecraft.resources.ResourceLocation;

public enum RoadType {

    BLACKSTONE(ResourceLocation.withDefaultNamespace("block/blackstone_top"), ResourceLocation.withDefaultNamespace("block/blackstone"), ""),
    ANDESITE(ResourceLocation.withDefaultNamespace("block/polished_andesite"), ResourceLocation.withDefaultNamespace("block/polished_andesite"), "_andesite");

    public final ResourceLocation topTexture;
    public final ResourceLocation baseTexture;
    public final String extension;

    private RoadType(ResourceLocation topTexture, ResourceLocation baseTexture, String extension) {
        this.topTexture = topTexture;
        this.baseTexture = baseTexture;
        this.extension = extension;
    }
}
