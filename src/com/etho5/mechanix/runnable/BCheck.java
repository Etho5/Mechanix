package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.CargoManager;
import com.etho5.mechanix.machines.cargo.ItemPackage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BCheck extends BukkitRunnable {

    /*
    Each second, another check will run, this time for all wires set to a "check" status of 2 (need to
    check). This performs the same check as a generator, checking each BlockFace for technology. If
    it finds a machine, it will give energy to the machine and move on, if it hasn't run out of energy.
    If it finds a wire with a "check" status of 0 (not checked), it transfers the correct amount of
    energy and sets that wire's "check" status to 2 (need to check). When all checks are run, the
    "parent" wire is set to a "check" status of 1 (checked) and the process repeats.
    */

    @Override
    public void run() {

        List<Machine> machs = new ArrayList<Machine>(){{
            this.addAll(Machine.machines.values());
        }};

        for (int i = machs.size(); i > 0; i--) {
            Machine from = machs.get(i - 1);
            List<Machine> near = from.getAdjacentMachines();

            if (from.getMachineItem().getType() == MachineType.CARGO) {

                try {
                    ItemPackage.getPackageFromMachine(from);
                } catch (NullPointerException e) {
                    continue;
                }

                for (Machine to : near) {

                    MachineInventory mInv = to.getMachineInventory();

                    if (from.getMachineItem() == MachineItem.OUTPUT_PIPE) {
                        if (to.getMachineItem() == MachineItem.DIRECTOR_PIPE) {
//                            System.out.println("B-Check - from is output, to is director");
                            if (CargoManager.isOKToTransfer(from, to, ItemPackage.getPackageFromMachine(from))) {
//                                System.out.println("B-Check - package in output: " + ItemPackage.getPackageFromMachine(from).toString());
                                ItemPackage.getPackageFromMachine(from).setDirection(mInv.getDirection()).delete().setPipe(to);
                                break;
                            }
                        }
                    } else if (to.getMachineItem() == MachineItem.GATEWAY_PIPE && from.getMachineInventory().getNetwork() == to.getMachineInventory().getNetwork()) {
                        CargoManager.checkGateway(to, ItemPackage.getPackageFromMachine(from));
                    } else if (to.getMachineItem() != MachineItem.OUTPUT_PIPE) {
                        if (CargoManager.isOKToTransfer(from, to, ItemPackage.getPackageFromMachine(from))) {

                            if (to.getMachineItem() == MachineItem.DIRECTOR_PIPE) {
                                ItemPackage.getPackageFromMachine(from).setDirection(mInv.getDirection()).delete().setPipe(to);
                            } else {
                                ItemPackage.getPackageFromMachine(from).delete().setPipe(to);
                            }
                            break;
                        }
                    }
                }
            }

            else if (from.getMachineItem().getType() == MachineType.WIRE) {
                if (from.getCheckedStatus() != 2) continue;

                int machEPS = from.getMachineItem().energyPerSecond;

                if (near != null) {

                    for (Machine to : near) {

                        if (to.getMachineItem().getType() == MachineType.WIRE) {
                            if (to.getCheckedStatus() != 3) {
                                if (to.getEnergy() + machEPS <= to.getMachineItem().maxEnergy
                                        && from.getEnergy() - machEPS >= 0
                                        && to.getEnergy() + machEPS >= 0) {
                                    to.setEnergy(to.getEnergy() + machEPS);
                                    from.setEnergy(Math.abs(from.getEnergy()) - machEPS);
                                    to.setCheckedStatus(2);
                                }
                            }

                        } else if (to.getMachineItem().getType() == MachineType.MACHINE) {
                            if (to.getEnergy() + machEPS <= to.getMachineItem().maxEnergy) {
                                to.setEnergy(to.getEnergy() + machEPS);
                                from.setEnergy(from.getEnergy() - machEPS);
                            }
                        }
                    }
                    from.setCheckedStatus(1);
                }
            }
        }
    }
}
