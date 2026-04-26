package com.peter.cityblocks.blocks.elevator;

import java.util.ArrayList;

import com.peter.cityblocks.CityBlocks;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ElevatorControllerBlockEntity extends BlockEntity {

    public static final String NAME = "card_reader_entity";
    public static final ResourceLocation ID = CityBlocks.identifier(NAME);
    public static final BlockEntityType<ElevatorControllerBlockEntity> BLOCK_ENTITY_TYPE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, ID,
            FabricBlockEntityTypeBuilder.create(ElevatorControllerBlockEntity::new, ElevatorControllerBlock.BLOCK).build());

    public static void register() {
        PeripheralLookup.get().registerForBlockEntity((entity, direction) -> entity.getPeripheral(), BLOCK_ENTITY_TYPE);
    }

    protected final ElevatorControllerPeripheral peripheral;

    protected final ArrayList<ElevatorFloor> floors = new ArrayList<>();

    public ElevatorControllerBlockEntity(BlockPos pos, BlockState state) {
        super(BLOCK_ENTITY_TYPE, pos, state);
        peripheral = new ElevatorControllerPeripheral(this);
    }

    public ElevatorControllerPeripheral getPeripheral() {
        return peripheral;
    }

    public void addFloor(int height, String name) {
        for (final ElevatorFloor elevatorFloor : floors) {
            if (elevatorFloor.height == height) {
                elevatorFloor.name = name;
                return;
            }
        }
        floors.add(new ElevatorFloor(name, height));
        floors.sort(null);
    }
    
    public boolean removeFloor(int height) {
        return floors.removeIf(f -> {
            return f.height == height;
        });
    }

    public ElevatorFloor[] getFloors() {
        ElevatorFloor[] arr = new ElevatorFloor[floors.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = floors.get(i).clone();
        }
        return arr;
    }

    public void playerRequestFloor(ServerPlayer player, int fromIndex, int toIndex) {
        if (peripheral.accessControl) {
            if(!peripheral.canAccess(player, fromIndex, toIndex))
                return;
        }
        sendPlayerToFloor(player, toIndex);
        peripheral.onMove(fromIndex, toIndex);
    }

    protected void sendPlayerToFloor(ServerPlayer player, int index) {
        if(index < 0)
            throw new IndexOutOfBoundsException();
        if(index >= floors.size())
            index = floors.size() - 1;
        player.randomTeleport(player.getX(), floors.get(index).height, player.getZ(), false);
    }

    public static class ElevatorFloor implements Comparable<ElevatorFloor> {

        public String name;
        public int height;

        public ElevatorFloor(String name, int height) {
            this.name = name;
            this.height = height;
        }

        @Override
        public int compareTo(ElevatorFloor o) {
            return height - o.height;
        }

        @Override
        protected ElevatorFloor clone() {
            return new ElevatorFloor(name, height);
        }
    }

}
