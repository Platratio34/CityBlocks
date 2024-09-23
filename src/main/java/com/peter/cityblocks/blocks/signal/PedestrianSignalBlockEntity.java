package com.peter.cityblocks.blocks.signal;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.BlockPosScreenPacket;
import com.peter.cityblocks.gui.SignalHeadScreenHandler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PedestrianSignalBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "pedestrian_signal_entity";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<PedestrianSignalBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
            BlockEntityType.Builder.create(PedestrianSignalBlockEntity::new, PedestrianSignalBlock.BLOCK).build());

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
    }

    @Override
    protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        
        nbt.putInt(NBT_STATE, state);
        nbt.putInt(NBT_HEAD_ID, headId);

        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains(NBT_STATE)) {
            state = nbt.getInt(NBT_STATE);
        }
        if (nbt.contains(NBT_HEAD_ID)) {
            headId = nbt.getInt(NBT_HEAD_ID);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        super.markDirty();
    }

    public void setState(int state) {
        if (world.isClient) {
            return;
        }
        this.state = state;
        markDirty();
    }

    public int getState() {
        return state;
    }

    public void setHeadId(int id) {
        if (world.isClient) {
            return;
        }
        headId = id;
        markDirty();
    }

    public int getHeadId() {
        return headId;
    }

    @Override
    public Text getDisplayName() {
        return CityBlocks.translatableText("block", PedestrianSignalBlock.NAME);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SignalHeadScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayerEntity)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosScreenPacket(pos);
    }
}
