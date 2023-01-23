package com.etho5.mechanix.runnable;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.Machine;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateMachines extends BukkitRunnable {

    @Override
    public void run() {
        System.out.println(Machine.machines);
        for (Machine m : Machine.machines) {
            if(m != null && m.getMachineItem() != null)
                new UpdateMachine(m).runTaskTimer(Main.getInstance(), 0L, m.getMachineItem().getDelay());
        }
    }
}
