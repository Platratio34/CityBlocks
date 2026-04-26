package com.peter.cityblocks.ccextended;

import java.util.ArrayList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.items.components.ItemComponents;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.core.computer.ComputerSide;
import dan200.computercraft.core.redstone.RedstoneState;

public class CardReaderPeripheral implements IPeripheral {

    protected final CardReaderBlockEntity reader;
    protected final RedstoneState redstoneState;

    public CardReaderPeripheral(CardReaderBlockEntity reader, RedstoneState redstoneState) {
        this.reader = reader;
        this.redstoneState = redstoneState;
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
        return ((CardReaderPeripheral) other).reader.getBlockPos().equals(reader.getBlockPos());
    }

    private final AttachedComputerSet computers = new AttachedComputerSet();

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
    protected long nextId = 0;
    protected long lastId = 0;

    public void onUse(ItemStack stack) {
        lastItem = new ItemData(stack);
        CityBlocks.debug("Card reader used: {}, {}, [{}]", lastItem.getId(), lastItem.getName(),
                lastItem.getComponentIds());
        final long cardId = stack.has(ItemComponents.KEYCARD_ID_TYPE) ? stack.get(ItemComponents.KEYCARD_ID_TYPE)
                : 0;
        lastId = cardId;

        if (nextId != 0) {
            stack.set(ItemComponents.KEYCARD_ID_TYPE, nextId);
            nextId = 0;
        }
        // final long crypto = stack.contains(ItemComponents.KEYCARD_CRYPTO_TYPE)
        //         ? stack.get(ItemComponents.KEYCARD_CRYPTO_TYPE)
        //         : 0;
        computers.forEach((computer) -> {
            Object[] args = new Object[] {
                    computer.getAttachmentName(),
                    lastItem,
                    null,
                    null,
            };
            if (cardId != 0) {
                args[2] = cardId;
            }
            // if (crypto != 0) {
            //     args[3] = true;
            // }
            computer.queueEvent(CARD_READER_INSERT_EVENT, args);
        });
    }
    
    private String cardQuery(String input, long cardKey) {
        
        return "";
    }

    @LuaFunction
    public final ItemData getLastItem() {
        return lastItem;
    }

    @LuaFunction
    public final long getLastId() {
        return lastId;
    }

    @LuaFunction
    public final void clearLastItem() {
        lastItem = null;
        lastId = 0;
    }

    @LuaFunction
    public final void setOutput(String side, boolean output) {
        redstoneState.setOutput(ComputerSide.valueOfInsensitive(side), output ? 15 : 0);
    }

    @LuaFunction
    public final void setNextId(long nextId) {
        this.nextId = nextId;
    }

    public static class ItemData {

        private final ItemStack stack;

        public ItemData(ItemStack stack) {
            this.stack = stack.copy();
        }

        @LuaFunction
        public final String getId() {
            return stack.getItemHolder().getRegisteredName();
        }

        @LuaFunction
        public final String getName() {
            Component cName = stack.getCustomName();
            if(cName == null) {
                return "";
            }
            return cName.getString();
        }

        @LuaFunction
        public final String getComponent(String id) {
            DataComponentMap map = stack.getComponents();
            DataComponentType<?> type = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(ResourceLocation.tryParse(id));
            return map.get(type).toString();
        }

        @LuaFunction
        public final ArrayList<String> getComponentIds() {
            DataComponentMap map = stack.getComponents();
            ArrayList<String> ids = new ArrayList<>();
            map.forEach(comp -> {
                ids.add(BuiltInRegistries.DATA_COMPONENT_TYPE.wrapAsHolder(comp.type()).getRegisteredName());
            });
            return ids;
        }

        @LuaFunction
        public final String getComponentsHash() {
            DataComponentMap map = stack.getComponents();
            int hash = 0;
            for(TypedDataComponent<?> comp : map) {
                hash ^= comp.toString().hashCode();
            }
            return Integer.toHexString(hash);
        }

    }

}
