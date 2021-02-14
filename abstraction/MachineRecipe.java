package com.etho5.mechanix.abstraction;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.recipes.AutoAnvil;
import com.etho5.mechanix.machines.recipes.CombustionEngine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MachineRecipe {

    public static MachineRecipe getMachineRecipe(Machine machine) {
        switch(machine.getMachineItem()) {
            case AUTO_ANVIL:
                return new AutoAnvil();
            case COMBUSTION_ENGINE:
                return new CombustionEngine();
            default:
                return null;
        }
    }

    public static void updateMachine(Inventory inv, MachineRecipe recipe, Machine machine) {
        MachineInventory machInv = machine.getMachineInventory();
        MachineType type = machInv.getType();

        final int[] inputSlots = type.getInputOrNetwork();
        final int[] outputSlots = type.getOutputOrWhitelist();
        if(inv.getContents() != null) {

            ItemStack[] contents = inv.getContents();
            ItemStack input0 = contents[inputSlots[0]];
            ItemStack input1 = contents[inputSlots[1]];

            int count = 0;
            for (Material[] r : recipe.validInputs()) {
                List<Material> mat = Arrays.asList(r);

                if (machine.getEnergy() < machine.getMachineItem().energyPerSecond) return;

                if (mat.contains(input0.getType()) && mat.contains(input1.getType())) {

                    for (int a : outputSlots) {

                        if (inv.getItem(a) == null) {
                            input0.setAmount(input0.getAmount() - 1);
                            input1.setAmount(input1.getAmount() - 1);

                            machine.setEnergy(machine.getEnergy() - machine.getMachineItem().energyPerSecond);
                            machInv.setInventoryEnergy(inv, machine.getEnergy());

                            inv.setItem(a, new ItemStack(recipe.validOutputs().get(count)[0]));

                            inv.setItem(inputSlots[0], input0);
                            inv.setItem(inputSlots[1], input1);

                            machInv.setInventory(inv);
                        }
                    }
                }
                count++;
            }
        }
    }

    public static void updateGenerator(Inventory inv, MachineRecipe recipe, Machine generator) {

        if(generator.getEnergy() >= generator.getMachineItem().maxEnergy) return;

        MachineInventory genInv = generator.getMachineInventory();

        Bukkit.broadcastMessage(inv.toString());
        Bukkit.broadcastMessage(inv.getContents().length + "");
        MachineType type = generator.getMachineItem().getType();

        for(Material[] r : recipe.validInputs()) {
            List<Material> mat = Arrays.asList(r);

            if(inv.getContents() != null) {

                final int[] inputSlots = type.getInputOrNetwork();
                List<ItemStack> items = new ArrayList<>();
                for(int b : inputSlots) {
                    items.add(inv.getItem(b));
                }
                System.out.println(items.toString());

                for (int i = 0; i < items.size(); i++) {
                    ItemStack a = items.get(i);

                    if (a != null && mat.contains(a.getType())) {

                        int amt = a.getAmount();
                        a.setAmount(0);
                        generator.setEnergy(Math.min(generator.getEnergy() + (generator.getMachineItem().energyPerSecond * amt), generator.getMachineItem().maxEnergy));

                        inv.setItem(inputSlots[i], a);

                        genInv.setInventoryEnergy(inv, generator.getEnergy());

                        genInv.setInventory(inv);
                    }
                }
            }
        }
    }

    public abstract void handleClick(Inventory inv, Machine machine);

    public abstract List<Material[]> validInputs();

    public abstract List<Material[]> validOutputs();
}
