package com.peter.cityblocks.blocks.signs;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class CustomSignBlockItem extends BlockItem {

    public CustomSignBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    // TODO fix tooltip
    // @Override
    // public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    //     super.appendTooltip(stack, context, tooltip, type);
    //     NbtCompound nbt = CustomSignBlockEntity.getNbtFromStack(stack);
    //     CustomSignBlock signBlock = (CustomSignBlock) getBlock();
    //     if (nbt != null) {
    //         // tooltip.add(Text.of("NTB: " + nbt));
    //         int variant = nbt.getInt(CustomSignBlockEntity.NBT_VARIANT).get();
    //         signBlock.getTooltip(tooltip, nbt.getCompound(CustomSignBlockEntity.NBT_BLOCK_STATE).get(), variant);
    //         if (nbt.contains(CustomSignBlockEntity.NBT_TEXT)) {
    //             NbtList textList = (NbtList) nbt.get(CustomSignBlockEntity.NBT_TEXT);
    //             for (int i = 0; i < textList.size(); i++) {
    //                 tooltip.add(Text.of(String.format("Line %d: \"%s\"", i, textList.getString(i))));
    //             }
    //         }
    //     }
    // }

}
