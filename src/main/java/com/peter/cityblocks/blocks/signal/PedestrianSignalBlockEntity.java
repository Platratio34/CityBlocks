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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class PedestrianSignalBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "pedestrian_signal_entity";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<PedestrianSignalBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(PedestrianSignalBlockEntity::new, PedestrianSignalBlock.BLOCK).build());

    public static final int OFF_STATE = 0;
    public static final int WALK_STATE = 1;
    public static final int FLASH_STATE = 2;
    public static final int STOP_STATE = 3;
    protected int state = 0;
    protected int headId = 0;

    private static final String NBT_STATE = "state";
    private static final String NBT_HEAD_ID = "head_id";

    public static void register() {

    }

    public PedestrianSignalBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
        CityBlocks.debug("Creating new PedestrianSignalBlockEntity");
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        
        view.putInt(NBT_STATE, state);
        view.putInt(NBT_HEAD_ID, headId);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);

        Optional<Integer> optState = view.getInt(NBT_STATE);
        if (optState.isPresent()) {
            state = optState.get();
        }
        Optional<Integer> optId = view.getInt(NBT_HEAD_ID);
        if (optId.isPresent()) {
            headId = optId.get();
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

    public void setState(int state) {
        if (level.isClientSide) {
            return;
        }
        this.state = state;
        setChanged();
    }

    public int getState() {
        return state;
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

    @Override
    public Component getDisplayName() {
        return CityBlocks.translatableText("block", PedestrianSignalBlock.NAME);
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
