package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineType;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EnergyCheckTask extends BukkitRunnable {

    private final Machine machine;

    public EnergyCheckTask(Machine machine) {
        this.machine = machine;
    }

    @Override
    public void run() {

        System.out.println("Energy check begun");

        Machine mach = this.machine;

        List<Machine> near = mach.getAdjacentMachines(1);
        if (near != null) {

            for(Machine m : near) {

                int mMax = m.getMachineItem().maxEnergy;
                int mPer = m.getMachineItem().energyPerSecond;

                if(mach.getEnergy() - mPer < 0) return;

                if(m.hasBeenChecked()) continue;

                if(m.getLocation().equals(mach.getLocation())) continue;

                if(m.getEnergy() >= mMax) continue;

                int send;
                if(m.getEnergy() + mPer > mMax) {
                    send = mMax - m.getEnergy();
                } else {
                    send = mPer;
                }

                m.setEnergy(m.getEnergy() + send);
                m.getMachineInventory().setInventoryEnergy(m.getMachineInventory().getInventory(), m.getEnergy());
                mach.setEnergy(mach.getEnergy() - send);
                m.setHasBeenChecked(!m.hasBeenChecked());
                Bukkit.broadcastMessage("Machine on side given energy");
            }

        }
        Bukkit.broadcastMessage("Energy check run");
    }
}
