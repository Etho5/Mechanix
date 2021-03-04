package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.CargoManager;
import com.etho5.mechanix.machines.cargo.ItemPackage;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BCheck extends BukkitRunnable {

    private final Machine machine;
    public BCheck(Machine machine) {
        this.machine = machine;
    }

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
        System.out.println("B-check begun");

        Machine from = this.machine;
        List<Machine> near = from.getAdjacentMachines();

        if(from.getMachineItem().getType() == MachineType.CARGO) {

            for(Machine to : near) {
                MachineInventory mInv = to.getMachineInventory();

                if(from.getMachineItem() == MachineItem.OUTPUT_PIPE) {
                    if (to.getMachineItem() == MachineItem.DIRECTOR_PIPE) {
                        if (CargoManager.isOKToTransfer(from, to, ItemPackage.getPackageFromMachine(from))) {
                            ItemPackage.getPackageFromMachine(from).setDirection(mInv.getDirection()).delete().setPipe(to);
                        }
                    }
                }

                else if(to.getMachineItem() == MachineItem.GATEWAY_PIPE && from.getMachineInventory().getNetwork() == to.getMachineInventory().getNetwork()) {
                    CargoManager.checkGateway(to, ItemPackage.getPackageFromMachine(from));
                }

                else if(to.getMachineItem() != MachineItem.OUTPUT_PIPE){
                    if (CargoManager.isOKToTransfer(from, to, ItemPackage.getPackageFromMachine(from))) {

                        if(to.getMachineItem() == MachineItem.DIRECTOR_PIPE) {
                            ItemPackage.getPackageFromMachine(from).setDirection(mInv.getDirection()).delete().setPipe(to);
                        }
                        else ItemPackage.getPackageFromMachine(from).delete().setPipe(to);
                    }
                }
            }
            System.out.println("B-check run");
            return;
        }

        if(from.getMachineItem().getType() != MachineType.WIRE || from.getCheckedStatus() != 2) return;

        int machEPS = from.getMachineItem().energyPerSecond;

        if(near != null){

            for(Machine m : near) {

                if(m.getMachineItem().getType() == MachineType.CARGO || m.getMachineItem().getType() == MachineType.GENERATOR) continue;

                if(m.getMachineItem().getType() == MachineType.WIRE) {
                    if(m.getCheckedStatus() == 0) {
                        if(m.getEnergy() + machEPS <= m.getMachineItem().maxEnergy) m.setEnergy(m.getEnergy() + machEPS);
                        from.setEnergy(from.getEnergy() - machEPS);
                        m.setCheckedStatus(2);
                    }
                }

                else if(m.getMachineItem().getType() == MachineType.MACHINE) {
                    if(m.getEnergy() + machEPS <= m.getMachineItem().maxEnergy) m.setEnergy(m.getEnergy() + machEPS);
                    from.setEnergy(from.getEnergy() - machEPS);
                }

            }

            from.setCheckedStatus(1);

        }
        System.out.println("B-check run");
    }
}
