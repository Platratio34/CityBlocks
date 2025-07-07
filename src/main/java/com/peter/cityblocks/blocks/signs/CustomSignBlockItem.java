package com.peter.cityblocks.blocks.signs;

import java.util.List;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

public class CustomSignBlockItem extends BlockItem {

    public CustomSignBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public void tooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, List<Text> list,
            NbtCompound nbt) {
        
        CustomSignBlock signBlock = (CustomSignBlock) getBlock();
        if (nbt != null) {
            // tooltip.add(Text.of("NTB: " + nbt));
            int variant = nbt.getInt(CustomSignBlockEntity.NBT_VARIANT).get();
            Optional<NbtCompound> opt = nbt.getCompound(CustomSignBlockEntity.NBT_BLOCK_STATE);
            if(opt.isPresent()) {
                signBlock.getTooltip(list, opt.get(), variant); //error
                if (nbt.contains(CustomSignBlockEntity.NBT_TEXT)) {
                    NbtList textList = (NbtList) nbt.get(CustomSignBlockEntity.NBT_TEXT);
                    for (int i = 0; i < textList.size(); i++) {
                        list.add(Text.of(String.format("Line %d: \"%s\"", i, textList.getString(i).get())));
                    }
                }
            }
        }
    }

}
