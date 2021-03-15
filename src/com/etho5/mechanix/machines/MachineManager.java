package com.etho5.mechanix.machines;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.multiblocks.*;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MachineManager {

    public static List<Multiblock> multiblocks = new ArrayList<Multiblock>(){{
        add(new Crusher());
        add(new Drill());
        add(new FumeHood());
        add(new LumberMill());
        add(new Oven());
        add(new Refinery());
        add(new Refrigerator());
        add(new Sieve());
        add(new Workstation());
        add(new XPBottler());
    }};

    public static Machine getMachineFromLocation(final Location location) {
        AtomicReference<Machine> retVal = new AtomicReference<>(null);
        Machine.machines.forEach((k, v) -> {
            if(k.equals(location)) retVal.set(v);
        });
        return retVal.get();
    }

    public static boolean isLocationAMachine(final Location location) {
        AtomicBoolean retVal = new AtomicBoolean(false);
        Machine.machines.keySet().forEach(k -> {
            if(k.equals(location)) retVal.set(true);
        });
        return retVal.get();
    }
}
