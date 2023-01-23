package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Machine;
import org.bukkit.scheduler.BukkitRunnable;

public class ChargesMove extends BukkitRunnable {

    @Override
    public void run() {
        for(Machine m : Machine.machines) new ChargeMove(m);
    }
}
