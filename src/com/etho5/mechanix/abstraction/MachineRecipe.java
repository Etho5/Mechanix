package com.etho5.mechanix.abstraction;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.recipes.ArcFurnace;
import com.etho5.mechanix.machines.recipes.AutoAnvil;
import com.etho5.mechanix.machines.recipes.CombustionEngine;
import com.etho5.mechanix.machines.recipes.Freezer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MachineRecipe {

    public static List<MachineRecipe> recipes = new ArrayList<>();

    public MachineRecipe() {
        recipes.add(this);
    }

    public static MachineRecipe getMachineRecipe(Machine machine) {
        switch(machine.getMachineItem()) {
            case AUTO_ANVIL:
                return new AutoAnvil();
            case COMBUSTION_ENGINE:
                return new CombustionEngine();
            case FREEZER:
                return new Freezer();
            case ARC_FURNACE:
                return new ArcFurnace();
            default:
                return null;
        }
    }

    public static void updateMachineBothSlots(Inventory inv, MachineRecipe recipe, Machine machine, boolean random) {
        MachineInventory machInv = machine.getMachineInventory();
        MachineType type = machInv.getType();

        final int[] inputSlots = type.getInput();
        final int[] outputSlots = type.getOutputOrWhitelist();
        if(inv.getContents() != null) {

            ItemStack[] contents = inv.getContents();
            ItemStack input0 = contents[inputSlots[0]];
            ItemStack input1 = contents[inputSlots[1]];

            int count = 0;
            for (ItemStack[] r : recipe.validInputs()) {
                List<ItemStack> item = Arrays.asList(r);

                if (machine.getEnergy() < machine.getMachineItem().energyPerSecond) return;

                if (item.contains(input0) && item.contains(input1)) {

                    for (int a : outputSlots) {

                        if (inv.getItem(a) == null) {

                            if(random) {
                                determineOutputRandom(inv, recipe, machine, machInv, inputSlots, input0, input1, count, a);
                            }
                            else determineOutput(inv, recipe, machine, machInv, inputSlots, input0, input1, count, a);
                        }
                    }
                }
                count++;
            }
        }
    }

    private static void determineOutput(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, int[] inputSlots, ItemStack input0, ItemStack input1, int count, int a) {
        if(input0 != null) input0.setAmount(input0.getAmount() - 1);
        if(input1 != null) input1.setAmount(input1.getAmount() - 1);

        machine.setEnergy(machine.getEnergy() - machine.getMachineItem().energyPerSecond);
        machInv.setInventoryEnergy(inv, machine.getEnergy());

        inv.setItem(a, new ItemStack(recipe.validOutputs().get(count)[0]));

        inv.setItem(inputSlots[0], input0);
        inv.setItem(inputSlots[1], input1);

        machInv.setInventory(inv);
    }

    private static void determineOutputRandom(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, int[] inputSlots, ItemStack input0, ItemStack input1, int count, int a) {
        if(input0 != null) input0.setAmount(input0.getAmount() - 1);
        if(input1 != null) input1.setAmount(input1.getAmount() - 1);

        machine.setEnergy(machine.getEnergy() - machine.getMachineItem().energyPerSecond);
        machInv.setInventoryEnergy(inv, machine.getEnergy());

        inv.setItem(a, new ItemStack(recipe.validOutputs().get(count)[ThreadLocalRandom.current().nextInt(recipe.validOutputs().size() - 1)]));

        inv.setItem(inputSlots[0], input0);
        inv.setItem(inputSlots[1], input1);

        machInv.setInventory(inv);
    }

    public static void updateMachineOneSlot(Inventory inv, MachineRecipe recipe, Machine machine) {
        MachineInventory machInv = machine.getMachineInventory();
        MachineType type = machInv.getType();

        final int[] inputSlots = type.getInput();
        final int[] outputSlots = type.getOutputOrWhitelist();
        if(inv.getContents() != null) {

            ItemStack[] contents = inv.getContents();
            ItemStack input0 = contents[inputSlots[0]];
            ItemStack input1 = contents[inputSlots[1]];

            int count = 0;
            for (ItemStack[] r : recipe.validInputs()) {
                List<ItemStack> item = Arrays.asList(r);

                if (machine.getEnergy() < machine.getMachineItem().energyPerSecond) return;

                if (item.contains(input0)) {
                    determineOutputOneSlot(inv, recipe, machine, machInv, inputSlots, outputSlots, input0, count);
                    return;
                }
                else if(item.contains(input1)) {
                    determineOutputOneSlot(inv, recipe, machine, machInv, inputSlots, outputSlots, input1, count);
                    return;
                }
                count++;
            }
        }
    }

    private static void determineOutputOneSlot(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, int[] inputSlots, int[] outputSlots, ItemStack input, int count) {
        for (int a : outputSlots) {
            if (inv.getItem(a) == null) {
                determineOutput(inv, recipe, machine, machInv, inputSlots, input, input, count, a);
            }
        }
    }

    public static void updateGenerator(Inventory inv, MachineRecipe recipe, Machine generator) {

        if(generator.getEnergy() >= generator.getMachineItem().maxEnergy) return;

        MachineInventory genInv = generator.getMachineInventory();

        Bukkit.broadcastMessage(inv.toString());
        Bukkit.broadcastMessage(inv.getContents().length + "");
        MachineType type = generator.getMachineItem().getType();

        for(ItemStack[] r : recipe.validInputs()) {
            List<ItemStack> mat = Arrays.asList(r);

            if(inv.getContents() != null) {

                final int[] inputSlots = type.getInput();
                List<ItemStack> items = new ArrayList<>();
                for(int b : inputSlots) {
                    items.add(inv.getItem(b));
                }
                System.out.println(items.toString());

                for (int i = 0; i < items.size(); i++) {
                    ItemStack a = items.get(i);

                    if (a != null && mat.contains(a)) {

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

    private boolean creates(ItemStack item) {
        for(ItemStack[] output : this.validOutputs()) {
            List<ItemStack> out = Arrays.asList(output);
            if(out.contains(item)) return true;
        }
        return false;
    }

    public static MachineRecipe getWhatCreates(ItemStack item) {
        for(MachineRecipe m : recipes) {
            if(m.creates(item)) return m;
        }
        return null;
    }

    public abstract MachineItem machineItem();

    public abstract void handleClick(Inventory inv, Machine machine);

    public abstract List<ItemStack[]> validInputs();

    public abstract List<ItemStack[]> validOutputs();
}
