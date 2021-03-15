package com.etho5.mechanix.runnable;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineType;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateMachines extends BukkitRunnable {

    @Override
    public void run() {
        for (Machine m : Machine.machines.values()) {
            if(m.getMachineItem().getType() == MachineType.MACHINE || m.getMachineItem().getType() == MachineType.GENERATOR) {
                new UpdateMachine(m).runTaskTimer(Main.getInstance(), 0L, m.getMachineItem().getDelay());
            }
        }
    }
}
