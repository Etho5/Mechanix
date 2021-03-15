package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.*;
import com.etho5.mechanix.machines.cargo.CargoManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;

public class ACheck extends BukkitRunnable {

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

        List<Machine> machs = new ArrayList<Machine>(){{
            this.addAll(Machine.machines.values());
        }};

        for (int i = machs.size(); i > 0; i--) {
            Machine from = machs.get(i - 1);
            if (from.getMachineItem().getType() == MachineType.GENERATOR) {

                int machEPS = from.getMachineItem().energyPerSecond;

                // if generator doesn't have enough energy, stop
                if(from.getEnergy() - machEPS < 0) continue;

                List<Machine> near = from.getAdjacentMachines();
                if (near != null) {

                    List<Machine> nearbyWires = new ArrayList<>();
                    for (Machine to : near) {

                        if(to.getEnergy() >= to.getMachineItem().maxEnergy) continue;

                        if (to.getMachineItem().getType() == MachineType.WIRE) nearbyWires.add(to);

                        // no negatives!
                        if(to.getEnergy() < 0) {
                            to.setEnergy(0);
                        }

                        if(to.getMachineItem().getType() == MachineType.MACHINE) {
                            if(to.getEnergy() + machEPS <= to.getMachineItem().maxEnergy) {
                                to.setEnergy(to.getEnergy() + machEPS);
                                from.setEnergy(from.getEnergy() - machEPS);
                            }
                        }
                    }

                    if(!nearbyWires.isEmpty()) {
                        int energyToEach = machEPS / nearbyWires.size();
                        for (Machine wire : nearbyWires) {

                            if(wire.getEnergy() > wire.getMachineItem().maxEnergy) {
                                wire.setEnergy(wire.getMachineItem().maxEnergy);
                            }
                            else if(wire.getEnergy() < wire.getMachineItem().maxEnergy) {
                                wire.setEnergy(Math.min(wire.getEnergy() + energyToEach, wire.getMachineItem().maxEnergy));
                                wire.setCheckedStatus(2);
                            }
                        }
                        from.setEnergy(from.getEnergy() - machEPS);
                    }
                }
            } else if (from.getMachineItem() == MachineItem.OUTPUT_PIPE) {
                CargoManager.checkOutput(from, from.getAdjacentBlocks());
            }
        }
    }
}
