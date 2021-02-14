package com.etho5.mechanix.machines;

import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MachineManager {

    public final Map<UUID, List<Machine>> machinesCache = new HashMap<>();


    public Machine getMachineFromLocation(final Location location) {
        AtomicReference<Machine> retVal = new AtomicReference<>(null);
        machinesCache.values().forEach(machines -> machines.forEach(machine -> {
            if (machine.getLocation().equals(location))
                retVal.set(machine);
        }));
        return retVal.get();
    }

    public List<Machine> getAllMachinesOwnedByUUID(final UUID uuid) {
        List<Machine> machList = new ArrayList<>();
        machinesCache.values().forEach(machines -> machines.forEach(machine -> {
            if (machine.getOwner().equals(uuid))
                machList.add(machine);
        }));
        return machList;
    }

    public boolean isLocationAMachine(final Location location) {
        return getMachineFromLocation(location) != null;
    }
}
