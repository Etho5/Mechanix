package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GeothermalEngine extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.GEOTHERMAL_ENGINE;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateGenerator(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{
                new ItemStack(Material.LAVA)
        });

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        return null;
    }
}
