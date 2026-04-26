package com.peter.cityblocks.blocks.elevator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.server.network.ServerPlayerEntity;

public class ElevatorControllerPeripheral implements IPeripheral {

    public static final String TYPE = "elevator_controller";

    public final ElevatorControllerBlockEntity entity;

    public boolean accessControl = false;
    private final AttachedComputerSet computers = new AttachedComputerSet();

    public static final String EVENT_ON_MOVE = "elevator_move";

    public HashSet<Integer> allowedDest = new HashSet<>();
    public HashSet<Integer> allowedSrc = new HashSet<>();
    public HashMap<Integer, HashSet<Integer>> allowedTrips = new HashMap<>();

    public ElevatorControllerPeripheral(ElevatorControllerBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean equals(IPeripheral other) {
        if(other == this)
            return true;
        if (other instanceof ElevatorControllerPeripheral ecp)
            return ecp.entity == entity;
        return false;
    }

    public boolean canAccess(ServerPlayerEntity player, int fromIndex, int toIndex) {
        if (!accessControl)
            return true;
        else if (allowedSrc.contains(fromIndex))
            return true;
        else if (allowedDest.contains(toIndex))
            return true;
        else if (allowedTrips.containsKey(fromIndex) && allowedTrips.get(fromIndex).contains(toIndex))
            return true;
        else
            return false;
    }

    protected void triggerEvent(String event, Object... args) {
        computers.forEach(c -> {
            Object[] a2 = new Object[args.length + 1];
            a2[0] = c.getAttachmentName();
            System.arraycopy(args, 0, a2, 1, args.length);
            c.queueEvent(event, a2);
        });
    }

    public void onMove(int fromIndex, int toIndex) {
        triggerEvent(EVENT_ON_MOVE, fromIndex, toIndex);
    }

    @LuaFunction
    public final void setAccessControl(boolean active) {
        accessControl = active;
    }

    @LuaFunction
    public final void addAllowedSrc(int floorIndex) {
        allowedSrc.add(floorIndex);
    }
    @LuaFunction
    public final void removeAllowedSrc(int floorIndex) {
        allowedSrc.remove(floorIndex);
    }
    @LuaFunction
    public final int[] getAllowedSrc() {
        int[] arr = new int[allowedSrc.size()];
        int i = 0;
        for (Integer f : allowedSrc) {
            arr[i++] = f;
        }
        return arr;
    }
    
    @LuaFunction
    public final void addAllowedDest(int floorIndex) {
        allowedDest.add(floorIndex);
    }
    @LuaFunction
    public final void removeAllowedDest(int floorIndex) {
        allowedDest.remove(floorIndex);
    }
    @LuaFunction
    public final int[] getAllowedDest() {
        int[] arr = new int[allowedDest.size()];
        int i = 0;
        for (Integer f : allowedDest) {
            arr[i++] = f;
        }
        return arr;
    }
    
    @LuaFunction
    public final void addAllowedTrip(int startFloor, int endFloor) {
        if (!allowedTrips.containsKey(startFloor))
            allowedTrips.put(startFloor, new HashSet<>());
        allowedTrips.get(startFloor).add(endFloor);
    }
    @LuaFunction
    public final void removeAllowedTrip(int startFloor, int endFloor) {
        if (!allowedTrips.containsKey(startFloor))
            return;
        HashSet<Integer> s = allowedTrips.get(startFloor);
        s.remove(endFloor);
        if(s.size() == 0)
            allowedTrips.remove(startFloor);
    }
    @LuaFunction
    public final void clearAllowedTrips(int startFloor) {
        allowedTrips.remove(startFloor);
    }
    @LuaFunction
    public final void clearAllowedTripDest(int destFloor) {
        for (HashSet<Integer> s : allowedTrips.values()) {
            s.remove(destFloor);
        }
    }
    @LuaFunction
    public final HashMap<Integer, int[]> getAllowedTrips() {
        HashMap<Integer, int[]> m = new HashMap<>();
        for (Entry<Integer, HashSet<Integer>> entry : allowedTrips.entrySet()) {
            int[] arr = new int[entry.getValue().size()];
            int i = 0;
            for (Integer f : entry.getValue()) {
                arr[i++] = f;
            }
            m.put(entry.getKey(), arr);
        }
        return m;
    }

    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }
}
