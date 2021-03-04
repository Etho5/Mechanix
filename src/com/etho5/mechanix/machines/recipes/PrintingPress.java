package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PrintingPress extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.PRINTING_PRESS;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineBothSlots(inv, this, machine, true);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> input = new ArrayList<>();

        input.add(new ItemStack[]{new ItemStack(Material.BOOK), new ItemStack(Material.EXPERIENCE_BOTTLE, 8)});

        return input;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> output = new ArrayList<>();

        for(Enchantment e : Enchantment.values()) {
            output.add(new ItemStack[]{new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchantment(e, 1).build()});
        }

        return output;
    }
}
