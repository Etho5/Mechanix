package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.utils.RepairableItems;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AutoAnvil extends MachineRecipe {

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachine(inv, this, machine);
    }

    @Override
    public List<Material[]> validInputs() {
        List<Material[]> l = new ArrayList<>();

        for(RepairableItems i : RepairableItems.values()) {
            l.add(new Material[]{ i.getRepairable(), i.getRepairMaterial() });
        }

        return l;
    }

    @Override
    public List<Material[]> validOutputs() {
        List<Material[]> l = new ArrayList<>();

        for(RepairableItems i : RepairableItems.values()) {
            l.add(new Material[]{});
        }

        return l;
    }

}
