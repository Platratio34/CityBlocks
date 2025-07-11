package com.peter.cityblocks.blocks.signal;

import java.util.Optional;

import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.BlockPosScreenPacket;
import com.peter.cityblocks.gui.SignalHeadScreenHandler;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
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
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalHeadBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "signal_head_entity";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<SignalHeadBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
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
        CityBlocks.LOGGER.debug("Creating new SignalHeadBlockEntity");
    }

    @Override
    protected void writeData(WriteView view) {
        int[] stateIntArray = new int[states.length];
        int[] colorIntArray = new int[colors.length];
        for (int i = 0; i < SignalHeadBlock.MAX_LAMPS; i++) {
            stateIntArray[i] = states[i].code;
            colorIntArray[i] = colors[i].code;
        }
        view.putIntArray(NBT_STATES_ARRAY, stateIntArray);
        view.putIntArray(NBT_COLORS_ARRAY, colorIntArray);

        view.putInt(NBT_HEAD_ID, headId);

        view.putInt(NBT_NUM_LAMPS, world.getBlockState(pos).get(SignalHeadBlock.LAMP_COUNT));

        super.writeData(view);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);

        Optional<int[]> optState = view.getOptionalIntArray(NBT_STATES_ARRAY);
        if (optState.isPresent()) {
            int[] intArray = optState.get();
            for (int i = 0; i < intArray.length; i++) {
                states[i] = LampState.fromCode(intArray[i]);
            }
        }
        Optional<int[]> optColor = view.getOptionalIntArray(NBT_COLORS_ARRAY);
        if (optColor.isPresent()) {
            int[] intArray = optColor.get();
            for (int i = 0; i < intArray.length; i++) {
                colors[i] = LampColor.fromCode(intArray[i]);
            }
        }
        Optional<Integer> optId = view.getOptionalInt(NBT_HEAD_ID);
        if (optId.isPresent()) {
            headId = optId.get();
        }
        if (world != null && !world.isClient && world.isPosLoaded(pos)) {
            Optional<Integer> optLamps = view.getOptionalInt(NBT_NUM_LAMPS);
            if (optLamps.isPresent()) {
                BlockState state = world.getBlockState(pos);
                world.setBlockState(pos, state.with(SignalHeadBlock.LAMP_COUNT, optLamps.get()));
            }
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

    public void setState(int lamp, LampState state) {
        if (world.isClient) {
            return;
        }
        states[lamp] = state;
        markDirty();
    }

    public LampState getState(int lamp) {
        return states[lamp];
    }

    public void setStates(LampState[] lampStates) {
        for (int i = 0; i < lampStates.length; i++) {
            states[i] = lampStates[i];
        }
        markDirty();
    }

    public void setStates(int[] lampStates) {
        for (int i = 0; i < lampStates.length; i++) {
            states[i] = LampState.fromCode(lampStates[i]);
        }
        markDirty();
    }

    public void setColor(int lamp, LampColor color) {
        if (world.isClient) {
            return;
        }
        colors[lamp] = color;
        markDirty();
    }

    public LampColor getColor(int lamp) {
        return colors[lamp];
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

    public void setLampCount(int lampCount) {
        if (world != null && !world.isClient && world.isPosLoaded(pos)) {
            world.setBlockState(pos, getCachedState().with(SignalHeadBlock.LAMP_COUNT, lampCount));
            markDirty();
        }
    }

    public int getLampCount() {
        if (world == null)
            return -1;
        return getCachedState().get(SignalHeadBlock.LAMP_COUNT);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, SignalHeadBlockEntity blockEntity) {
        
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, SignalHeadBlockEntity blockEntity) {
        
    }

    @Override
    public Text getDisplayName() {
        return CityBlocks.translatableText("block", SignalHeadBlock.NAME);
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
