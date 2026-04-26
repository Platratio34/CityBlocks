package com.peter.cityblocks.blocks;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

public interface TooltipedItem {

    /**
     * Add a tooltip to the item/block
     * @param itemStack Stack the tooltip will be added to
     * @param tooltipContext Tooltip context
     * @param tooltipType Tooltip type
     * @param list Tooltip text
     */
    public default void addTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipFlag tooltipType,
            List<Component> list) {

    }
    
    /**
     * Add a tooltip to the item/block (Called after <code>addTooltip</code>)
     * @param itemStack Stack the tooltip will be added to
     * @param tooltipContext Tooltip context
     * @param tooltipType Tooltip type
     * @param list Tooltip text
     * @param entityData Block entity data (if present) the item stores
     */
    public default void addTooltipEntity(ItemStack itemStack, TooltipContext tooltipContext, TooltipFlag tooltipType, List<Component> list, CompoundTag entityData) {

    }
}
