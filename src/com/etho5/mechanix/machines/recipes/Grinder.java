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

public class Grinder extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.GRINDER;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineOneSlot(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{new ItemStack(Material.STONE)});
        input.add(new ItemStack[]{new ItemStack(Material.IRON_ORE)});
        input.add(new ItemStack[]{IngredientList.PYROLUSITE.getItem()});
        input.add(new ItemStack[]{IngredientList.PITCHBLENDE.getItem()});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{IngredientList.SHINY_DUST.getItem()});
        input.add(new ItemStack[]{IngredientList.IRON_DUST.getItem()});
        input.add(new ItemStack[]{IngredientList.MANGANESE_OXIDE.getItem()});
        input.add(new ItemStack[]{IngredientList.URANIUM.getItem()});

        return input;
    }
}
