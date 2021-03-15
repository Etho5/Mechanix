package com.etho5.mechanix.runnable;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateMachine extends BukkitRunnable {

    private final Machine m;
    protected UpdateMachine(Machine m) {
        this.m = m;
    }

    @Override
    public void run() {
        MachineRecipe recipe = MachineRecipe.getMachineRecipe(m);
        if (recipe != null) {
            recipe.handleClick(m.getMachineInventory().getInventory(), m);
        }
    }
}
