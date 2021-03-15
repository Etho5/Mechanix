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

public class OreProcessor extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.ORE_PROCESSOR;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineOneSlot(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{new ItemStack(Material.COAL_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.IRON_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.REDSTONE_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.LAPIS_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.GOLD_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.DIAMOND_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.EMERALD_ORE)});
        input.add(new ItemStack[]{new ItemStack(Material.NETHER_QUARTZ_ORE)});

        input.add(new ItemStack[]{new ItemStack(Material.ANDESITE)});
        input.add(new ItemStack[]{new ItemStack(Material.DIORITE)});
        input.add(new ItemStack[]{new ItemStack(Material.GRANITE)});
        input.add(new ItemStack[]{new ItemStack(Material.STONE)});
        input.add(new ItemStack[]{new ItemStack(Material.COBBLESTONE)});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> output = new ArrayList<>();

        output.add(new ItemStack[]{new ItemStack(Material.COAL)});
        output.add(new ItemStack[]{new ItemStack(Material.IRON_INGOT)});
        output.add(new ItemStack[]{new ItemStack(Material.REDSTONE, 4)});
        output.add(new ItemStack[]{new ItemStack(Material.LAPIS_LAZULI, 4)});
        output.add(new ItemStack[]{new ItemStack(Material.GOLD_INGOT)});
        output.add(new ItemStack[]{new ItemStack(Material.DIAMOND)});
        output.add(new ItemStack[]{new ItemStack(Material.EMERALD)});
        output.add(new ItemStack[]{new ItemStack(Material.QUARTZ)});

        output.add(new ItemStack[]{IngredientList.SODIUM.getItem()});
        output.add(new ItemStack[]{IngredientList.SILICON.getItem()});
        output.add(new ItemStack[]{new ItemStack(Material.QUARTZ)});
        output.add(new ItemStack[]{IngredientList.SHINY_DUST.getItem()});
        output.add(new ItemStack[]{IngredientList.SHINY_DUST.getItem()});

        return output;
    }
}
