package com.peter.cityblocks.blocks.signs;

import java.util.List;
import java.util.Optional;

import com.peter.cityblocks.blocks.TooltipedItem;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

public class CustomSignBlockItem extends BlockItem implements TooltipedItem {

    public CustomSignBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void addTooltipEntity(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> list,
            NbtCompound entityData) {
        
        CustomSignBlock signBlock = (CustomSignBlock) getBlock();
        if (entityData != null) {
            int variant = entityData.getInt(CustomSignBlockEntity.NBT_VARIANT).get();
            Optional<NbtCompound> opt = entityData.getCompound(CustomSignBlockEntity.NBT_BLOCK_STATE);
            if(opt.isPresent()) {
                signBlock.getTooltip(list, opt.get(), variant); //error
                if (entityData.contains(CustomSignBlockEntity.NBT_TEXT)) {
                    NbtList textList = (NbtList) entityData.get(CustomSignBlockEntity.NBT_TEXT);
                    for (int i = 0; i < textList.size(); i++) {
                        list.add(Text.of(String.format("Line %d: \"%s\"", i, textList.getString(i).get())));
                    }
                }
            }
        }
    }

}
