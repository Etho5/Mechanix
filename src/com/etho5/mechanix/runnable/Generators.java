package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Charge;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.Direction;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Generators extends BukkitRunnable {

    private final Machine machine;

    public Generators(Machine m) {
        this.machine = m;
    }

    @Override
    public void run() {
        if(machine.getMachineInventory().getType() == MachineType.GENERATOR) {
            System.out.println("Generator found: " + machine.getLocation().getX() + "x " + machine.getLocation().getZ() + "z");
            List<Block> near = machine.getAdjacentBlocks();
            for(Block b : near) {
                if(MachineManager.isLocationAMachine(b.getLocation())) {
                    Machine nearby = MachineManager.getMachineFromLocation(b.getLocation());
                    System.out.println("Adjacent machine found: " + nearby.getLocation().getX() + "x " + nearby.getLocation().getZ() + "z");
                    MachineType type = nearby.getMachineInventory().getType();
                    if(machine.getChargeMagnitude() >= nearby.getMachineItem().current) {

                        if (type == MachineType.WIRE) {
                            System.out.println("Adjacent machine is wire");
                            nearby.setCharge(new Charge(nearby.getChargeMagnitude() + machine.getMachineItem().current, Direction.UNSET));
                        } else if (type == MachineType.JUNCTION_WIRE) {
                            System.out.println("Adjacent machine is  junction wire");
                            if(MachineManager.isLocationAMachine(Direction.getBlockFrom(machine.getLocation().getBlock(), machine.getMachineInventory().getDirection(), 1).getLocation())) {
                                Machine pastJun = MachineManager.getMachineFromLocation(Direction.getBlockFrom(machine.getLocation().getBlock(), machine.getMachineInventory().getDirection(), 1).getLocation());
                                if(pastJun.getMachineInventory().getType() == MachineType.WIRE)
                                    pastJun.setCharge(new Charge(pastJun.getChargeMagnitude() + machine.getMachineItem().current, Direction.UNSET));
                                else if(pastJun.getMachineInventory().getType() == MachineType.MACHINE) {
                                    pastJun.setChargeMagnitude(pastJun.getChargeMagnitude() + machine.getMachineItem().current);
                                }
                            }
                        } else if (type == MachineType.MACHINE) {
                            System.out.println("Adjacent machine is other machine");
                            nearby.setChargeMagnitude(nearby.getChargeMagnitude() + machine.getMachineItem().current);
                        }
                    }
                }
            }
        }
    }
}
