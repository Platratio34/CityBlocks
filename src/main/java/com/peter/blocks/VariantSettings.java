package com.peter.blocks;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.state.property.IntProperty;

public class VariantSettings extends Settings {

    public int variants;
    public IntProperty variantProperty;

    public VariantSettings() {
        super();
    }

    public VariantSettings(int variants) {
        super();
        setVariants(variants);
    }

    public VariantSettings setVariants(int variants) {
        this.variants = variants;
        this.variantProperty = IntProperty.of("variant", 0, variants - 1);
        return this;
    }
}
