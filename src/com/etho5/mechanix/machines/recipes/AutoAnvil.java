package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.utils.RepairableItems;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AutoAnvil extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.AUTO_ANVIL;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineBothSlots(inv, this, machine, false);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> l = new ArrayList<>();

        for(RepairableItems i : RepairableItems.values()) {
            l.add(new ItemStack[]{ new ItemStack(i.getRepairable()), new ItemStack(i.getRepairMaterial()) });
        }

        return l;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> l = new ArrayList<>();

        for(RepairableItems i : RepairableItems.values()) {
            l.add(new ItemStack[]{new ItemStack(i.getRepairable())});
        }

        return l;
    }

}
