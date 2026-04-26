package com.peter.cityblocks.blocks.signs;

import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import com.peter.cityblocks.blocks.TooltipedItem;

public class CustomSignBlockItem extends BlockItem implements TooltipedItem {

    public CustomSignBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public void addTooltipEntity(ItemStack itemStack, TooltipContext tooltipContext, TooltipFlag tooltipType, List<Component> list,
            CompoundTag entityData) {
        
        CustomSignBlock signBlock = (CustomSignBlock) getBlock();
        if (entityData != null) {
            int variant = entityData.getInt(CustomSignBlockEntity.NBT_VARIANT).get();
            Optional<CompoundTag> opt = entityData.getCompound(CustomSignBlockEntity.NBT_BLOCK_STATE);
            if(opt.isPresent()) {
                signBlock.getTooltip(list, opt.get(), variant); //error
                if (entityData.contains(CustomSignBlockEntity.NBT_TEXT)) {
                    ListTag textList = (ListTag) entityData.get(CustomSignBlockEntity.NBT_TEXT);
                    for (int i = 0; i < textList.size(); i++) {
                        list.add(Component.nullToEmpty(String.format("Line %d: \"%s\"", i, textList.getString(i).get())));
                    }
                }
            }
        }
    }

}
