package com.etho5.mechanix.machines.cargo;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemPackage {

    private static final Map<Machine, ItemPackage> machinePackages = new HashMap<>();
    private final ItemStack item;
    private Machine machine;
    private Direction direction;

    public ItemPackage(Machine machine, ItemStack item) {
        this.item = item;
        this.direction = machine.getMachineInventory().getDirection();
        if(machine.getMachineItem().getType() != MachineType.CARGO) this.machine = null;
        else this.machine = machine;
        machinePackages.put(machine, this);
    }

    public ItemStack getItem() {
        return item;
    }

    public Direction getDirection() {
        return direction;
    }

    public ItemPackage setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public void setPipe(Machine machine) {
        if(machine.getMachineItem().getType() == MachineType.CARGO) return;
        this.machine = machine;
        machinePackages.put(machine, this);
        System.out.println("Package transferred to " + machine.toString());
    }

    public static ItemPackage getPackageFromMachine(Machine machine) {
        return machinePackages.get(machine);
    }

    public static boolean hasPackage(Machine machine) {
        return machinePackages.containsKey(machine);
    }

    public ItemPackage delete() {
        return machinePackages.remove(this.machine);
    }
}
