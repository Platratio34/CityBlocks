package com.peter.blocks.signal;

import com.peter.CityBlocks;
import com.peter.gui.SignalControllerScreenHandler;
import com.peter.networking.BlockPosScreenPacket;

import dan200.computercraft.api.peripheral.PeripheralLookup;

import java.util.ArrayList;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SignalControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "signal_controller_entity";
    public static final Identifier ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<SignalControllerBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, ID,
            BlockEntityType.Builder.create(SignalControllerBlockEntity::new, SignalControllerBlock.BLOCK).build());

    public static void register() {
        PeripheralLookup.get().registerForBlockEntity(SignalControllerBlockEntity::getPeripheral, BLOCK_ENTITY_TYPE);
    }

    public static final int MAX_HEADS = 24;

    private Cycle[] cycles = new Cycle[3];
    private int cycleMode = 0;
    private int cPhase = 0;
    private int cTime = 0;

    private ArrayList<BlockPos> heads;
    private ArrayList<BlockPos> pedestrians;

    private LampState[][] tempState = new LampState[MAX_HEADS][];
    private boolean tempChanged = false;

    private final SignalControllerPeripheral peripheral = new SignalControllerPeripheral(this);

    public SignalControllerBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
        heads = new ArrayList<BlockPos>();
        pedestrians = new ArrayList<BlockPos>();
        cycles[0] = Cycle.basicSwitchCycle(10*20, 3*20, 2*20);
        cycles[1] = Cycle.basic3WayCycle(10*20, 3*20, 2*20);
        cycles[2] = Cycle.basic4WayCycle(10*20, 3*20, 2*20);
        for (int i = 0; i < tempState.length; i++) {
            tempState[i] = new LampState[] { LampState.SOLID_FLASH, LampState.OFF, LampState.OFF };
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, SignalControllerBlockEntity blockEntity) {
        
    }

    public static void serverTick(World world, BlockPos pos, BlockState state,
            SignalControllerBlockEntity blockEntity) {
        blockEntity.serverTick(world, pos, state);
    }

    private void serverTick(World world, BlockPos pos, BlockState state) {
        if (cycleMode == -1) {
            foreachHead((head) -> {
                head.setStates(new LampState[] { LampState.SOLID_FLASH, LampState.OFF, LampState.OFF });
            });
            foreachPedestrian((signal) -> {
                signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
            });
        } else if (cycleMode >= 0) {
            cTime++;
            markDirty();
            if (cycles[cycleMode].cycleStates[cPhase].time <= cTime) {
                cycle();
            }
        }
        if (tempChanged) {
            foreachHead((head) -> {
                head.setStates(tempState[head.headId]);
            });
            foreachPedestrian((signal) -> {
                LampState[] states = tempState[signal.headId];
                if(states[0] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
                } else if(states[1] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.FLASH_STATE);
                } else if(states[2] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.WALK_STATE);
                } else {
                    signal.setState(PedestrianSignalBlockEntity.OFF_STATE);
                }
            });
            tempChanged = false;
            markDirty();
        }
    }

    public void cycle(PlayerEntity player) {
        cycle();
        player.sendMessage(CityBlocks.translatableText("chat", "signal_controller.cycle",cPhase));
    }

    public void cycle() {
        if (cycleMode < 0) {
            return;
        }
        cPhase++;
        cTime = 0;
        if (cPhase > cycles[cycleMode].maxPhase) {
            cPhase = 0;
        }
        updateHeads();
        markDirty();
    }

    private void updateHeads() {
        if (cycleMode < 0)
            return;
        // System.out.println("Setting heads for cycle "+cState);
        foreachHead((head) -> {
            head.setStates(cycles[cycleMode].cycleStates[cPhase].getState(head.getHeadId()));
        });
        foreachPedestrian((signal) -> {
            LampState[] states = cycles[cycleMode].cycleStates[cPhase].getState(signal.getHeadId());
            if(states[0] != LampState.OFF) {
                signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
            } else if(states[1] != LampState.OFF) {
                signal.setState(PedestrianSignalBlockEntity.FLASH_STATE);
            } else if(states[2] != LampState.OFF) {
                signal.setState(PedestrianSignalBlockEntity.WALK_STATE);
            } else {
                signal.setState(PedestrianSignalBlockEntity.OFF_STATE);
            }
        });
        for (int i = 0; i < MAX_HEADS; i++) {
            tempState[i] = cycles[cycleMode].cycleStates[cPhase].getState(i);
        }
    }

    private void foreachHead(SignalHeadForeach runnable) {
        ArrayList<BlockPos> toRemove = new ArrayList<BlockPos>();
        for (BlockPos pos : heads) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SignalHeadBlockEntity)
                runnable.run((SignalHeadBlockEntity)blockEntity);
            else if (!world.getBlockState(pos).isOf(SignalHeadBlock.BLOCK)) {
                toRemove.add(pos);
            } else {
                CityBlocks.LOGGER.warn(
                        String.format("Could not find signal head at %d,%d,%d", pos.getX(), pos.getY(),
                                pos.getZ()));
            }
        }
        if (toRemove.size() > 0) {
            for (BlockPos blockPos : toRemove) {
                heads.remove(blockPos);
                CityBlocks.LOGGER.warn(
                        String.format("Could not find signal head at %d,%d,%d, removing from list", pos.getX(),
                                pos.getY(), pos.getZ()));
            }
            markDirty();
        }
    }
    private void foreachPedestrian(PedestrianSignalForeach runnable) {
        ArrayList<BlockPos> toRemove = new ArrayList<BlockPos>();
        for (BlockPos pos : pedestrians) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PedestrianSignalBlockEntity)
                runnable.run((PedestrianSignalBlockEntity)blockEntity);
            else if (!world.getBlockState(pos).isOf(PedestrianSignalBlock.BLOCK)) {
                toRemove.add(pos);
            } else {
                CityBlocks.LOGGER.warn(
                        String.format("Could not find pedestrian signal at %d,%d,%d", pos.getX(), pos.getY(),
                                pos.getZ()));
            }
        }
        if (toRemove.size() > 0) {
            for (BlockPos blockPos : toRemove) {
                pedestrians.remove(blockPos);
                CityBlocks.LOGGER.warn(
                        String.format("Could not find pedestrian signal at %d,%d,%d, removing from list", pos.getX(),
                                pos.getY(), pos.getZ()));
            }
            markDirty();
        }
    }

    private interface SignalHeadForeach {
        public void run(SignalHeadBlockEntity entity);
    }
    private interface PedestrianSignalForeach {
        public void run(PedestrianSignalBlockEntity entity);
    }

    @Override
    public Text getDisplayName() {
        return CityBlocks.translatableText("block", SignalControllerBlock.NAME);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SignalControllerScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayerEntity)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosScreenPacket(pos);
    }

    public boolean link(BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(SignalHeadBlock.BLOCK)) {
            if (heads.contains(pos)) {
                heads.remove(pos);
                markDirty();
                return false;
            }
            heads.add(pos);
            markDirty();

            SignalHeadBlockEntity head = (SignalHeadBlockEntity) world.getBlockEntity(pos);
            if (head != null) {
                head.setStates(tempState[head.headId]);
            }
        } else if (blockState.isOf(PedestrianSignalBlock.BLOCK)) {
            if (pedestrians.contains(pos)) {
                pedestrians.remove(pos);
                markDirty();
                return false;
            }
            pedestrians.add(pos);
            markDirty();

            PedestrianSignalBlockEntity signal = (PedestrianSignalBlockEntity) world.getBlockEntity(pos);
            if (signal != null) {
                LampState[] states = tempState[signal.headId];
                if(states[0] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
                } else if(states[1] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.FLASH_STATE);
                } else if(states[2] != LampState.OFF) {
                    signal.setState(PedestrianSignalBlockEntity.WALK_STATE);
                } else {
                    signal.setState(PedestrianSignalBlockEntity.OFF_STATE);
                }
            }
        } else {
            CityBlocks.LOGGER.error("Tried to link signal controller to unknown block: " + blockState.toString());
        }

        return true;
    }

    public int getCyclePhase() {
        return cPhase;
    }

    public int getCycleMode() {
        return cycleMode;
    }

    public int getCycleModeMax() {
        return cycles.length;
    }

    public String getCycleModeName() {
        if (cycleMode == -2) {
            return "Peripheral Control";
        } else if (cycleMode == -1) {
            return "Uncontrolled / Error";
        }
        return cycles[cycleMode].name;
    }

    public int getCyclePhaseMax() {
        if (cycleMode < 0)
            return -1;
        return cycles[cycleMode].maxPhase;
    }

    public int getCycleTime() {
        return cTime;
    }

    public int getCycleTimeMax() {
        if (cycleMode < 0)
            return -1;
        return cycles[cycleMode].cycleStates[cPhase].time;
    }

    public void setCycleMode(int mode) {
        if (world.isClient) {
            return;
        }
        cycleMode = mode;
        cPhase = 0;
        if (cycleMode <= -1) {
            cycleMode = -1;
        } else if (cycleMode > cycles.length) {
            cycleMode = cycles.length;
        }

        if (cycleMode == -1) {
            foreachHead((head) -> {
                head.setStates(new LampState[] { LampState.SOLID_FLASH, LampState.OFF, LampState.OFF });
            });
            foreachPedestrian((signal) -> {
                signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
            });
        } else if (cycleMode >= 0) {
            updateHeads();
        }
        markDirty();
    }

    private static final String NBT_CYCLE_MODE = "cycleMode";
    private static final String NBT_C_PHASE = "cPhase";
    private static final String NBT_C_TIME = "cTime";
    private static final String NBT_HEADS = "heads";
    private static final String NBT_PEDESTRIAN = "pedestrian";
    private static final String NBT_TEMP_STATE = "tempState";

    @Override
    protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        nbt.putInt(NBT_CYCLE_MODE, cycleMode);
        nbt.putInt(NBT_C_PHASE, cPhase);
        nbt.putInt(NBT_C_TIME, cTime);
        
        NbtList headsNbt = new NbtList();
        for (BlockPos head : heads) {
            headsNbt.add(new NbtIntArray(new int[] { head.getX(), head.getY(), head.getZ() }));
        }
        nbt.put(NBT_HEADS, headsNbt);

        NbtList pedestriansNbt = new NbtList();
        for (BlockPos signal : pedestrians) {
            pedestriansNbt.add(new NbtIntArray(new int[] { signal.getX(), signal.getY(), signal.getZ() }));
        }
        nbt.put(NBT_PEDESTRIAN, pedestriansNbt);

        NbtList tempStateNbt = new NbtList();
        for (int i = 0; i < tempState.length; i++) {
            tempStateNbt.add(new NbtIntArray(LampState.toIntArray(tempState[i])));
        }
        nbt.put(NBT_TEMP_STATE, tempStateNbt);

        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains(NBT_CYCLE_MODE)) {
            cycleMode = nbt.getInt(NBT_CYCLE_MODE);
        }
        if (nbt.contains(NBT_C_PHASE)) {
            cPhase = nbt.getInt(NBT_C_PHASE);
            if (cPhase < 0) {
                cPhase = 0;
            }
        }
        if (nbt.contains(NBT_C_TIME)) {
            cTime = nbt.getInt(NBT_C_TIME);
        }
        
        if (nbt.contains(NBT_HEADS)) {
            NbtList headsNbt = (NbtList) nbt.get(NBT_HEADS);
            for (int i = 0; i < headsNbt.size(); i++) {
                int[] pos = headsNbt.getIntArray(i);
                heads.add(new BlockPos(pos[0], pos[1], pos[2]));
            }
        }
        if (nbt.contains(NBT_PEDESTRIAN)) {
            NbtList pedestriansNbt = (NbtList) nbt.get(NBT_PEDESTRIAN);
            for (int i = 0; i < pedestriansNbt.size(); i++) {
                int[] pos = pedestriansNbt.getIntArray(i);
                pedestrians.add(new BlockPos(pos[0], pos[1], pos[2]));
            }
        }

        if (nbt.contains(NBT_TEMP_STATE)) {
            NbtList tempStateNbt = (NbtList) nbt.get(NBT_TEMP_STATE);
            for(int i = 0; i < tempStateNbt.size(); i++) {
                tempState[i] = LampState.fromIntArray(tempStateNbt.getIntArray(i));
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

    public LampState[] getStates(int head) {
        return tempState[head];
    }

    public void api_setHeadState(int headId, int[] state) {
        cycleMode = -2;
        cTime = 0;
        for (int i = 0; i < state.length; i++) {
            tempState[headId][i] = LampState.fromCode(state[i]);
        }
        tempChanged = true;
        if (world.isClient) {
            System.err.println("thing-ing");
        }
    }

    public SignalControllerPeripheral getPeripheral() {
        return peripheral;
    }

    public SignalControllerPeripheral getPeripheral(Direction direction) {
        return getPeripheral();
    }

}
