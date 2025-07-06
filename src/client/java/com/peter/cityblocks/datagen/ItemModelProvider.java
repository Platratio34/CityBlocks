package com.peter.cityblocks.datagen;

import com.peter.cityblocks.items.Items;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ItemModelProvider extends FabricModelProvider {

    public ItemModelProvider(FabricDataOutput output) {
        super(output);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Items.VARIANT_SWITCHER_ITEM, Models.GENERATED);
        itemModelGenerator.register(Items.SIGNAL_LINKER_ITEM, Models.GENERATED);
        
        itemModelGenerator.register(Items.EXIT_SIGN_BLOCK_ITEM);
    }

}
