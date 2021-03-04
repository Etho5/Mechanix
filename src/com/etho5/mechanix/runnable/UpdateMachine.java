package com.etho5.mechanix.runnable;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateMachine extends BukkitRunnable {

    private final Machine m;

    public UpdateMachine(Machine m) {
        this.m = m;
    }

    @Override
    public void run() {
        System.out.println("Machine updating begun");

        MachineRecipe recipe = MachineRecipe.getMachineRecipe(m);
        if (recipe != null) {
            recipe.handleClick(m.getMachineInventory().getInventory(), m);
        } else {
            System.out.println("Recipe is null");
        }

        System.out.println("Machine updated");
    }
}
