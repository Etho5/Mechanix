package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Sifter extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.SIFTER;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineOneSlot(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{IngredientList.SHINY_DUST.getItem()});
        input.add(new ItemStack[]{new ItemStack(Material.SAND)});
        input.add(new ItemStack[]{new ItemStack(Material.GRAVEL)});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> output = new ArrayList<>();

        output.add(new ItemStack[]{
                Utils.getRandomItem(new ItemStack[]{
                        IngredientList.IRON_DUST.getItem(),
                        IngredientList.SULFUR.getItem(),
                        IngredientList.COPPER.getItem(),
                        IngredientList.ALUMINIUM.getItem(),
                        IngredientList.LEAD.getItem(),
                        IngredientList.ZINC.getItem(),
                        IngredientList.VANADIUM.getItem(),
                        IngredientList.CHROMIUM.getItem()
                })
        });

        output.add(new ItemStack[]{
                Utils.getRandomItem(new ItemStack[]{
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SALT.getItem(),
                        IngredientList.SODA_ASH.getItem(),
                })
        });

        output.add(new ItemStack[]{
                Utils.getRandomItem(new ItemStack[]{
                        IngredientList.SHINY_DUST.getItem(),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        new ItemStack(Material.FLINT),
                        IngredientList.PYROLUSITE.getItem(),
                        IngredientList.PITCHBLENDE.getItem()
                })
        });

        return output;
    }
}
