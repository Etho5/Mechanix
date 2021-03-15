package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.CargoManager;
import com.etho5.mechanix.machines.cargo.ItemPackage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CCheck extends BukkitRunnable {

    /*
    Another check runs after this, those of wires of "check" status 1 (checked) and 3 (inert). First,
    all wires of "check" status 3 are set to "check" status 0 (not checked). Then, it goes through to
    see if any wires of "check" status 2 are next to it. If there are any, set it to "check" status 3
    (inert); it's still important for the next C-check. If none are present, however, the wire's
    "check" status is set to 0 (not checked), since the wire currently has nothing to feed into it,
    and thus has no reason to be having "check" status 1.
     */

    @Override
    public void run() {

        List<Machine> machs = new ArrayList<Machine>(){{
            this.addAll(Machine.machines.values());
        }};

        for (int i = machs.size(); i > 0; i--) {
            Machine from = machs.get(i - 1);

            if (from.getMachineItem().getType() == MachineType.CARGO) {
                try {
                    ItemPackage.getPackageFromMachine(from);
                } catch(NullPointerException e) {
                    continue;
                }
                if(from.getMachineItem() == MachineItem.INPUT_PIPE) {
                    CargoManager.checkInput(from, from.getAdjacentBlocks());
                }
                else if(from.getMachineItem() == MachineItem.PIPE) {

                    for(Machine to : from.getAdjacentMachines()) {

                        if (CargoManager.isOKToTransfer(from, to, ItemPackage.getPackageFromMachine(from))) {
                            ItemPackage.getPackageFromMachine(from).delete().setPipe(to);
                            break;
                        }
                    }
                }
            }

            else if (from.getMachineItem().getType() == MachineType.WIRE) {

                if (from.getCheckedStatus() == 3) {
                    from.setCheckedStatus(0);
                }

                else if (from.getCheckedStatus() == 1) {

                    List<Machine> near = from.getAdjacentMachines();
                    if (near != null) {

                        boolean needsReset = true;
                        for (Machine to : near) {

                            if (to.getMachineItem().getType() != MachineType.WIRE) continue;

                            if (to.getCheckedStatus() == 2) {
                                from.setCheckedStatus(3);
                                needsReset = false;
                            }
                        }
                        if (needsReset) from.setCheckedStatus(0);

                        if(from.getEnergy() > 0) {
                            from.setCheckedStatus(2);
                        }
                    }
                }
            }
        }
    }
}
