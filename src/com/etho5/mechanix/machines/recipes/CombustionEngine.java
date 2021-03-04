package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CombustionEngine extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.COMBUSTION_ENGINE;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateGenerator(inv,this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> inputs = new ArrayList<>();

        inputs.add(new ItemStack[]{
                new ItemStack(Material.COAL),
                new ItemStack(Material.CHARCOAL)
        });

        return inputs;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        return null;
    }
}
