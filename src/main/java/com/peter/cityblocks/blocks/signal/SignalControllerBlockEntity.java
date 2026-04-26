package com.peter.cityblocks.blocks.signal;

import com.mojang.serialization.Codec;
import com.peter.cityblocks.CityBlocks;
import com.peter.cityblocks.networking.BlockPosScreenPacket;
import com.peter.cityblocks.gui.SignalControllerScreenHandler;

import dan200.computercraft.api.peripheral.PeripheralLookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class SignalControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosScreenPacket> {

    public static final String NAME = "signal_controller_entity";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<SignalControllerBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(SignalControllerBlockEntity::new, SignalControllerBlock.BLOCK).build());

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
        CityBlocks.debug("Creating new SignalControllerBlockEntity");
        heads = new ArrayList<BlockPos>();
        pedestrians = new ArrayList<BlockPos>();
        cycles[0] = Cycle.basicSwitchCycle(10*20, 3*20, 2*20);
        cycles[1] = Cycle.basic3WayCycle(10*20, 3*20, 2*20);
        cycles[2] = Cycle.basic4WayCycle(10*20, 3*20, 2*20);
        for (int i = 0; i < tempState.length; i++) {
            tempState[i] = new LampState[] { LampState.SOLID_FLASH, LampState.OFF, LampState.OFF };
        }
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, SignalControllerBlockEntity blockEntity) {
        
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state,
            SignalControllerBlockEntity blockEntity) {
        blockEntity.serverTick(world, pos, state);
    }

    private void serverTick(Level world, BlockPos pos, BlockState state) {
        if (cycleMode == -1) {
            foreachHead((head) -> {
                head.setStates(new LampState[] { LampState.SOLID_FLASH, LampState.OFF, LampState.OFF });
            });
            foreachPedestrian((signal) -> {
                signal.setState(PedestrianSignalBlockEntity.STOP_STATE);
            });
        } else if (cycleMode >= 0) {
            cTime++;
            setChanged();
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
            setChanged();
        }
    }

    public void cycle(Player player) {
        cycle();
        player.displayClientMessage(CityBlocks.translatableText("chat", "signal_controller.cycle",cPhase), false);
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
        setChanged();
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
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SignalHeadBlockEntity)
                runnable.run((SignalHeadBlockEntity)blockEntity);
            else if (!level.getBlockState(pos).is(SignalHeadBlock.BLOCK)) {
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
                        String.format("Could not find signal head at %d,%d,%d, removing from list", worldPosition.getX(),
                                worldPosition.getY(), worldPosition.getZ()));
            }
            setChanged();
        }
    }
    private void foreachPedestrian(PedestrianSignalForeach runnable) {
        ArrayList<BlockPos> toRemove = new ArrayList<BlockPos>();
        for (BlockPos pos : pedestrians) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PedestrianSignalBlockEntity)
                runnable.run((PedestrianSignalBlockEntity)blockEntity);
            else if (!level.getBlockState(pos).is(PedestrianSignalBlock.BLOCK)) {
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
                        String.format("Could not find pedestrian signal at %d,%d,%d, removing from list", worldPosition.getX(),
                                worldPosition.getY(), worldPosition.getZ()));
            }
            setChanged();
        }
    }

    private interface SignalHeadForeach {
        public void run(SignalHeadBlockEntity entity);
    }
    private interface PedestrianSignalForeach {
        public void run(PedestrianSignalBlockEntity entity);
    }

    @Override
    public Component getDisplayName() {
        return CityBlocks.translatableText("block", SignalControllerBlock.NAME);
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new SignalControllerScreenHandler(syncId, playerInventory, getScreenOpeningData((ServerPlayer)player));
    }

    @Override
    public BlockPosScreenPacket getScreenOpeningData(ServerPlayer player) {
        return new BlockPosScreenPacket(worldPosition);
    }

    public boolean link(BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.is(SignalHeadBlock.BLOCK)) {
            if (heads.contains(pos)) {
                heads.remove(pos);
                setChanged();
                return false;
            }
            heads.add(pos);
            setChanged();

            SignalHeadBlockEntity head = (SignalHeadBlockEntity) level.getBlockEntity(pos);
            if (head != null) {
                head.setStates(tempState[head.headId]);
            }
        } else if (blockState.is(PedestrianSignalBlock.BLOCK)) {
            if (pedestrians.contains(pos)) {
                pedestrians.remove(pos);
                setChanged();
                return false;
            }
            pedestrians.add(pos);
            setChanged();

            PedestrianSignalBlockEntity signal = (PedestrianSignalBlockEntity) level.getBlockEntity(pos);
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
        if (level.isClientSide) {
            return;
        }
        cycleMode = mode;
        cPhase = 0;
        if (cycleMode <= -1) {
            cycleMode = -1;
        } else if (cycleMode >= cycles.length) {
            cycleMode = cycles.length - 1;
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
        setChanged();
    }

    private static final String NBT_CYCLE_MODE = "cycleMode";
    private static final String NBT_C_PHASE = "cPhase";
    private static final String NBT_C_TIME = "cTime";
    private static final String NBT_HEADS = "heads";
    private static final String NBT_PEDESTRIAN = "pedestrian";
    private static final String NBT_TEMP_STATE = "tempState";

    private static final Codec<List<List<Integer>>> INT_ARR_ARR_CODEC = Codec.list(Codec.list(Codec.INT));

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);

        view.putInt(NBT_CYCLE_MODE, cycleMode);
        view.putInt(NBT_C_PHASE, cPhase);
        view.putInt(NBT_C_TIME, cTime);
        
        List<List<Integer>> headsList = new ArrayList<>();
        for (BlockPos head : heads) {
            headsList.add(List.of(head.getX(), head.getY(), head.getZ()));
        }
        view.store(NBT_HEADS, INT_ARR_ARR_CODEC, headsList);

        List<List<Integer>> pedestriansList = new ArrayList<>();
        for (BlockPos signal : pedestrians) {
            pedestriansList.add(List.of(signal.getX(), signal.getY(), signal.getZ()));
        }
        view.store(NBT_PEDESTRIAN, INT_ARR_ARR_CODEC, pedestriansList);

        List<List<Integer>> tempStateList = new ArrayList<>();
        for (int i = 0; i < tempState.length; i++) {
            tempStateList.add(LampState.toIntList(tempState[i]));
        }
        view.store(NBT_TEMP_STATE, INT_ARR_ARR_CODEC, tempStateList);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);

        Optional<Integer> optMode = view.getInt(NBT_CYCLE_MODE);
        if (optMode.isPresent()) {
            cycleMode = optMode.get();
            if (cycleMode >= cycles.length) {
                cycleMode = cycles.length - 1;
            } else if (cycleMode < -1) {
                cycleMode = -1;
            }
        }
        Optional<Integer> optPhase = view.getInt(NBT_C_PHASE);
        if (optPhase.isPresent()) {
            cPhase = optPhase.get();
            if (cPhase < 0) {
                cPhase = 0;
            }
        }
        Optional<Integer> optTime = view.getInt(NBT_C_TIME);
        if (optTime.isPresent()) {
            cTime = optTime.get();
        }
        
        Optional<List<List<Integer>>> optHeads = view.read(NBT_HEADS, INT_ARR_ARR_CODEC);
        if (optHeads.isPresent()) {
            List<List<Integer>> list = optHeads.get();
            for (int i = 0; i < list.size(); i++) {
                List<Integer> pos = list.get(i);
                heads.add(new BlockPos(pos.get(0), pos.get(1), pos.get(2)));
            }
        }
        Optional<List<List<Integer>>> optPed = view.read(NBT_PEDESTRIAN, INT_ARR_ARR_CODEC);
        if (optPed.isPresent()) {
            List<List<Integer>> list = optPed.get();
            for (int i = 0; i < list.size(); i++) {
                List<Integer> pos = list.get(i);
                pedestrians.add(new BlockPos(pos.get(0), pos.get(1), pos.get(2)));
            }
        }

        Optional<List<List<Integer>>> optState = view.read(NBT_TEMP_STATE, INT_ARR_ARR_CODEC);
        if (optState.isPresent()) {
            List<List<Integer>> list = optState.get();
            for(int i = 0; i < list.size(); i++) {
                tempState[i] = LampState.fromIntList(list.get(i));
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
        if (level.isClientSide) {
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
