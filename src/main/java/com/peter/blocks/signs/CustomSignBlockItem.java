package com.peter.blocks.signs;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        NbtCompound nbt = CustomSignBlockEntity.getNbtFromStack(stack);
        CustomSignBlock signBlock = (CustomSignBlock) getBlock();
        if (nbt != null) {
            // tooltip.add(Text.of("NTB: " + nbt));
            int variant = nbt.getInt(CustomSignBlockEntity.NBT_VARIANT);
            signBlock.getTooltip(tooltip, nbt.getCompound(CustomSignBlockEntity.NBT_BLOCK_STATE), variant);
            if (nbt.contains(CustomSignBlockEntity.NBT_TEXT)) {
                NbtList textList = (NbtList) nbt.get(CustomSignBlockEntity.NBT_TEXT);
                for (int i = 0; i < textList.size(); i++) {
                    tooltip.add(Text.of(String.format("Line %d: \"%s\"", i, textList.getString(i))));
                }
            }
        }
    }

}
