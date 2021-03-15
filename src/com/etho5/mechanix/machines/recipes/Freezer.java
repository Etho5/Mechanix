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

public class Freezer extends MachineRecipe {
    @Override
    public MachineItem machineItem() {
        return MachineItem.FREEZER;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineOneSlot(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{new ItemStack(Material.WATER_BUCKET)});
        input.add(new ItemStack[]{new ItemStack(Material.ICE)});
        input.add(new ItemStack[]{new ItemStack(Material.PACKED_ICE)});
        input.add(new ItemStack[]{new ItemStack(Material.GLASS_BOTTLE)});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> output = new ArrayList<>();

        output.add(new ItemStack[]{new ItemStack(Material.ICE)});
        output.add(new ItemStack[]{new ItemStack(Material.PACKED_ICE)});
        output.add(new ItemStack[]{new ItemStack(Material.BLUE_ICE)});
        output.add(new ItemStack[]{IngredientList.LIQUID_AIR.getItem()});

        return output;
    }
}
