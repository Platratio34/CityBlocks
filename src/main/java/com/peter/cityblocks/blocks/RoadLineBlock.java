package com.peter.cityblocks.blocks;

import net.minecraft.block.MapColor;

public class RoadLineBlock extends VariantBlock {

    public RoadLineModel[] models;

    public boolean andesite = false;

    public RoadLineBlock(String name, MapColor color, RoadLineModel[] models) {
        super(new VariantSettings().setVariants(models.length).mapColor(color).solid(), name);
        this.models = models;
    }

    public RoadLineBlock andesite() {
        andesite = true;
        return this;
    }

    public static record RoadLineModel(String model, String overlay) {

    }

}
