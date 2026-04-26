package com.peter.cityblocks.blocks.signal;

import java.util.Optional;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.BlockPosScreenPacket;
import com.peter.cityblocks.gui.SignalHeadScreenHandler;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SignalHeadBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "signal_head_entity";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<SignalHeadBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(SignalHeadBlockEntity::new, SignalHeadBlock.BLOCK).build());

    protected LampState[] states = new LampState[] {
            LampState.SOLID_FLASH,
            LampState.OFF,
            LampState.OFF
    };
    protected LampColor[] colors = new LampColor[] {
            LampColor.RED,
            LampColor.AMBER,
            LampColor.GREEN
    };
    protected int headId = 0;

    private static final String NBT_STATES_ARRAY = "states";
    private static final String NBT_COLORS_ARRAY = "colors";
    private static final String NBT_HEAD_ID = "head_id";
    private static final String NBT_NUM_LAMPS = "num_lamps";

    public static void register() {

    }

    public SignalHeadBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
        CityBlocks.debug("Creating new SignalHeadBlockEntity");
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        int[] stateIntArray = new int[states.length];
        int[] colorIntArray = new int[colors.length];
        for (int i = 0; i < SignalHeadBlock.MAX_LAMPS; i++) {
            stateIntArray[i] = states[i].code;
            colorIntArray[i] = colors[i].code;
        }
        view.putIntArray(NBT_STATES_ARRAY, stateIntArray);
        view.putIntArray(NBT_COLORS_ARRAY, colorIntArray);

        view.putInt(NBT_HEAD_ID, headId);

        view.putInt(NBT_NUM_LAMPS, level.getBlockState(worldPosition).getValue(SignalHeadBlock.LAMP_COUNT));

        super.saveAdditional(view);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);

        Optional<int[]> optState = view.getIntArray(NBT_STATES_ARRAY);
        if (optState.isPresent()) {
            int[] intArray = optState.get();
            for (int i = 0; i < intArray.length; i++) {
                states[i] = LampState.fromCode(intArray[i]);
            }
        }
        Optional<int[]> optColor = view.getIntArray(NBT_COLORS_ARRAY);
        if (optColor.isPresent()) {
            int[] intArray = optColor.get();
            for (int i = 0; i < intArray.length; i++) {
                colors[i] = LampColor.fromCode(intArray[i]);
            }
        }
        Optional<Integer> optId = view.getInt(NBT_HEAD_ID);
        if (optId.isPresent()) {
            headId = optId.get();
        }
        if (level != null && !level.isClientSide && level.isLoaded(worldPosition)) {
            Optional<Integer> optLamps = view.getInt(NBT_NUM_LAMPS);
            if (optLamps.isPresent()) {
                BlockState state = level.getBlockState(worldPosition);
                level.setBlockAndUpdate(worldPosition, state.setValue(SignalHeadBlock.LAMP_COUNT, optLamps.get()));
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        super.setChanged();
    }

    public void setState(int lamp, LampState state) {
        if (level.isClientSide) {
            return;
        }
        states[lamp] = state;
        setChanged();
    }

    public LampState getState(int lamp) {
        return states[lamp];
    }

    public void setStates(LampState[] lampStates) {
        for (int i = 0; i < lampStates.length; i++) {
            states[i] = lampStates[i];
        }
        setChanged();
    }

    public void setStates(int[] lampStates) {
        for (int i = 0; i < lampStates.length; i++) {
            states[i] = LampState.fromCode(lampStates[i]);
        }
        setChanged();
    }

    public void setColor(int lamp, LampColor color) {
        if (level.isClientSide) {
            return;
        }
        colors[lamp] = color;
        setChanged();
    }

    public LampColor getColor(int lamp) {
        return colors[lamp];
    }

    public void setHeadId(int id) {
        if (level.isClientSide) {
            return;
        }
        headId = id;
        setChanged();
    }

    public int getHeadId() {
        return headId;
    }

    public void setLampCount(int lampCount) {
        if (level != null && !level.isClientSide && level.isLoaded(worldPosition)) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(SignalHeadBlock.LAMP_COUNT, lampCount));
            setChanged();
        }
    }

    public int getLampCount() {
        if (level == null)
            return -1;
        return getBlockState().getValue(SignalHeadBlock.LAMP_COUNT);
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, SignalHeadBlockEntity blockEntity) {
        
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, SignalHeadBlockEntity blockEntity) {
        
    }

    @Override
    public Component getDisplayName() {
        return CityBlocks.translatableText("block", SignalHeadBlock.NAME);
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new SignalHeadScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayer)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayer player) {
        return new BlockPosScreenPacket(worldPosition);
    }
}
