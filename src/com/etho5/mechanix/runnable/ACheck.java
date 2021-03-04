package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.*;
import com.etho5.mechanix.machines.cargo.CargoManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ACheck extends BukkitRunnable {

    private final Machine machine;

    public ACheck(Machine machine) {
        this.machine = machine;
    }

    /*
    A-Check

    Energy starts from a generator, which checks every second to see if it has enough energy to
    theoretically do anything(<= 50J). If it does, it checks each BlockFace to see if there is
    technology. Each wire that this finds gets energy has its "check" status set to 2 (need to check).
    (If there is a machine connected, it just gives energy directly to it.) This happens for each wire
    connected to the generator, or until the generator runs out.
     */

    @Override
    public void run() {

        System.out.println("A-check begun");

        Machine from = this.machine;

        if(from.getMachineItem().getType() == MachineType.GENERATOR) {

            int machEPS = from.getMachineItem().energyPerSecond;

            List<Machine> near = from.getAdjacentMachines();
            if (near != null) {

                for (Machine to : near) {

                    if (to.getMachineItem().getType() == MachineType.CARGO || to.getMachineItem().getType() == MachineType.GENERATOR)
                        continue;

                    // if generator doesn't have enough energy, stop checking
                    if (from.getEnergy() < machEPS) break;

                    // transfer energy to wire from machine
                    if (to.getEnergy() + machEPS <= to.getMachineItem().maxEnergy) to.setEnergy(to.getEnergy() + machEPS);
                    from.setEnergy(from.getEnergy() - machEPS);

                    // if the wire gets energy, set status to "need to check" and continue
                    if (to.getMachineItem().getType() == MachineType.WIRE) {
                        to.setCheckedStatus(2);
                    }

                }

            }
        }


        else if(from.getMachineItem().getType() == MachineType.CARGO) {
            CargoManager.checkInput(from, from.getAdjacentBlocks(), new MachineManager());
        }

        System.out.println("A-check run");
    }
}
