package com.peter.cityblocks.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class VariantSettings extends Properties {

    public int variants;
    public IntegerProperty variantProperty;

    public VariantSettings() {
        super();
    }

    public VariantSettings(int variants) {
        super();
        setVariants(variants);
    }

    public VariantSettings setVariants(int variants) {
        this.variants = variants;
        this.variantProperty = IntegerProperty.create("variant", 0, variants - 1);
        return this;
    }
}
