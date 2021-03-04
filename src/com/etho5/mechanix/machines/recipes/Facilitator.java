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

public class Facilitator extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.FACILITATOR;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineBothSlots(inv, this, machine, false);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{IngredientList.LIQUID_AIR.getItem(4), new ItemStack(Material.REDSTONE)});
        input.add(new ItemStack[]{IngredientList.SODA_ASH.getItem(4), new ItemStack(Material.REDSTONE)});
        input.add(new ItemStack[]{new ItemStack(Material.COAL, 2), new ItemStack(Material.CHARCOAL, 2)});
        input.add(new ItemStack[]{IngredientList.CARBON.getItem(4), new ItemStack(Material.SAND)});
        input.add(new ItemStack[]{IngredientList.SALT.getItem(4), new ItemStack(Material.WATER_BUCKET)});
        input.add(new ItemStack[]{IngredientList.SODIUM_CARBONATE.getItem(), IngredientList.CARBON.getItem()});
        input.add(new ItemStack[]{IngredientList.SODIUM.getItem(2), new ItemStack(Material.WATER_BUCKET)});
        input.add(new ItemStack[]{IngredientList.HYDROGEN.getItem(), IngredientList.CHLORINE.getItem()});
        input.add(new ItemStack[]{IngredientList.SODIUM.getItem(), IngredientList.CHLORINE.getItem()});
        input.add(new ItemStack[]{IngredientList.ZINC.getItem(2), IngredientList.HYDROCHLORIC_ACID.getItem()});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> output = new ArrayList<>();

        output.add(new ItemStack[]{IngredientList.HYDROGEN.getItem(4)});
        output.add(new ItemStack[]{IngredientList.SODIUM_CARBONATE.getItem(2)});
        output.add(new ItemStack[]{IngredientList.CARBON.getItem()});
        output.add(new ItemStack[]{IngredientList.SILICA_SAND.getItem()});
        output.add(new ItemStack[]{IngredientList.BRINE.getItem()});
        output.add(new ItemStack[]{IngredientList.SODIUM.getItem()});
        output.add(new ItemStack[]{IngredientList.SODIUM_HYDROXIDE.getItem()});
        output.add(new ItemStack[]{IngredientList.HYDROCHLORIC_ACID.getItem()});
        output.add(new ItemStack[]{IngredientList.SALT.getItem()});
        output.add(new ItemStack[]{IngredientList.ZINC_CHLORIDE.getItem()});

        return output;
    }
}
