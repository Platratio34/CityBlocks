package com.peter.cityblocks.ccextended;

import java.util.ArrayList;

import com.peter.cityblocks.CityBlocks;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CardReaderPeripheral implements IPeripheral {

    protected final CardReaderBlockEntity reader;

    public CardReaderPeripheral(CardReaderBlockEntity reader) {
        this.reader = reader;
        CityBlocks.debug("Creating new CardReaderPeripheral");
    }

    @Override
    public Object getTarget() {
        return reader;
    }

    @Override
    public String getType() {
        return "card_reader";
    }

    @Override
    public boolean equals(IPeripheral other) {
        if (other == null)
            return false;
        if (!other.getType().equals(getType()))
            return false;
        return ((CardReaderPeripheral) other).reader.getPos().equals(reader.getPos());
    }

    private final ArrayList<IComputerAccess> computers = new ArrayList<>();
    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }

    private ItemData lastItem = null;
    public static final String CARD_READER_INSERT_EVENT = "card_reader_insert";
    public void onUse(ItemStack stack) {
        lastItem = new ItemData(stack);
        for (IComputerAccess computer : computers) {
            Object[] args = new Object[] {
                computer.getAttachmentName(),
                lastItem
            };
            computer.queueEvent(CARD_READER_INSERT_EVENT, args);
        }
    }

    @LuaFunction
    public final ItemData getLastItem() {
        return lastItem;
    }

    public static class ItemData {

        private final ItemStack stack;

        public ItemData(ItemStack stack) {
            this.stack = stack.copy();
        }

        @LuaFunction
        public final String getId() {
            return stack.getRegistryEntry().getIdAsString();
        }

        @LuaFunction
        public final String getName() {
            Text cName = stack.getCustomName();
            if(cName == null) {
                return "";
            }
            return cName.getString();
        }

        @LuaFunction
        public final Object getComponent(String id) {
            ComponentMap map = stack.getComponents();
            ComponentType<?> type = Registries.DATA_COMPONENT_TYPE.get(Identifier.tryParse(id));
            return map.get(type);
        }

    }

}
