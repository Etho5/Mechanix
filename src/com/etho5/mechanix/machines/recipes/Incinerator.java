package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Incinerator extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.INCINERATOR;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateGenerator(inv, this, machine);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> recipes = new ArrayList<>();

        recipes.add(new ItemStack[]{
                new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_WOOD),
                new ItemStack(Material.DARK_OAK_LOG),
                new ItemStack(Material.DARK_OAK_WOOD),
                new ItemStack(Material.SPRUCE_LOG),
                new ItemStack(Material.SPRUCE_WOOD),
                new ItemStack(Material.BIRCH_LOG),
                new ItemStack(Material.BIRCH_WOOD),
                new ItemStack(Material.ACACIA_LOG),
                new ItemStack(Material.ACACIA_WOOD),
                new ItemStack(Material.JUNGLE_LOG),
                new ItemStack(Material.JUNGLE_WOOD),
                new ItemStack(Material.OAK_PLANKS, 3),
                new ItemStack(Material.DARK_OAK_PLANKS, 3),
                new ItemStack(Material.SPRUCE_PLANKS, 3),
                new ItemStack(Material.BIRCH_PLANKS, 3),
                new ItemStack(Material.ACACIA_PLANKS, 3),
                new ItemStack(Material.JUNGLE_PLANKS, 3),
                new ItemStack(Material.OAK_LEAVES, 4),
                new ItemStack(Material.DARK_OAK_LEAVES, 4),
                new ItemStack(Material.SPRUCE_LEAVES, 4),
                new ItemStack(Material.BIRCH_LEAVES, 4),
                new ItemStack(Material.ACACIA_LEAVES, 4),
                new ItemStack(Material.JUNGLE_LEAVES, 4),
                new ItemStack(Material.STICK, 8)
        });

        return recipes;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        return null;
    }
}
