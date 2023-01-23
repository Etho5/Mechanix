package com.etho5.mechanix.abstraction;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.recipes.*;
import org.bukkit.Material;
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

    public static MachineRecipe getMachineRecipe(MachineItem item) {
        switch(item) {
            case COMBUSTION_ENGINE:
                return new CombustionEngine();
            case ARC_FURNACE:
                return new ArcFurnace();
            case AUTO_ANVIL:
                return new AutoAnvil();
            case FREEZER:
                return new Freezer();
            case GRINDER:
                return new Grinder();
            case INCINERATOR:
                return new Incinerator();
            case SIFTER:
                return new Sifter();
            case PRINTING_PRESS:
                return new PrintingPress();
            case FACILITATOR:
                return new Facilitator();
            case ORE_PROCESSOR:
                return new OreProcessor();
            case GEOTHERMAL_ENGINE:
                return new GeothermalEngine();
            default:
                return null;
        }
    }

    public static void updateMachineBothSlots(Inventory inv, MachineRecipe recipe, Machine machine, boolean random) {
        MachineInventory machInv = machine.getMachineInventory();
        MachineType type = machInv.getType();

        final int[] inputSlots = type.getInput();
        if(inv.getContents() != null) {

            ItemStack[] contents = inv.getContents();
            ItemStack left = contents[inputSlots[0]];
            ItemStack right = contents[inputSlots[1]];

            for (ItemStack[] r : recipe.validInputs()) {
                List<ItemStack> item = Arrays.asList(r);

                if (machine.getChargeMagnitude() < machine.getMachineItem().current) return;

                if (item.contains(left) && item.contains(right) && recipe.validInputs().contains(r)) {
                    if (random) {
                        determineOutputTwoSlotsRandom(inv, recipe, machine, machInv, left, right, inputSlots[0], inputSlots[1], recipe.validInputs().indexOf(r));
                    } else determineOutputTwoSlots(inv, recipe, machine, machInv, left, right, inputSlots[0], inputSlots[1], recipe.validInputs().indexOf(r));
                }
            }
        }
    }

    private static void determineOutputTwoSlotsRandom(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, ItemStack input0, ItemStack input1, int inputSlot0, int inputSlot1, int inputRecipeIndex) {
        boolean hasStackable = false;
        int stackableSlot = -1;
        ItemStack result = recipe.validOutputs().get(inputRecipeIndex)[ThreadLocalRandom.current().nextInt(recipe.validOutputs().size() - 1)];

        for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {
            if (inv.getItem(outputSlot) != null) {
                if (inv.getItem(outputSlot).getAmount() < 64 && inv.getItem(outputSlot).isSimilar(result)) {
                    hasStackable = true;
                    stackableSlot = outputSlot;
                }
            }
        }
        if (hasStackable && stackableSlot != -1) {

            inv.setItem(stackableSlot, result);

            machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
            machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

            machInv.setInventory(inv);
        }

        else {

            for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {

                if (inv.getItem(inputSlot0) != null && inv.getItem(inputSlot1) != null) {
                    if (input0.getAmount() - 1 <= 0) {
                        inv.setItem(inputSlot0, null);
                    } else {
                        input0.setAmount(input0.getAmount() - 1);
                        inv.setItem(inputSlot0, input0);
                    }

                    if (input1.getAmount() - 1 <= 0) {
                        inv.setItem(inputSlot1, null);
                    } else {
                        input1.setAmount(input1.getAmount() - 1);
                        inv.setItem(inputSlot1, input1);
                    }
                }

                machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
                machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

                inv.setItem(outputSlot, result);
                machInv.setInventory(inv);
            }
        }
    }

    public static void updateMachineOneSlot(Inventory inv, MachineRecipe recipe, Machine machine) {
        MachineInventory machInv = machine.getMachineInventory();
        MachineType type = machInv.getType();

        final int[] inputSlots = type.getInput();
        if(inv.getContents() != null) {

            ItemStack[] contents = inv.getContents();
            ItemStack input0 = contents[inputSlots[0]];
            ItemStack input1 = contents[inputSlots[1]];

            if(input0 != null && input1 != null) {

                ItemStack input0Comp = input0.clone();
                ItemStack input1Comp = input1.clone();
                input0Comp.setAmount(1);
                input1Comp.setAmount(1);

                for (ItemStack[] r : recipe.validInputs()) {
                    List<ItemStack> item = Arrays.asList(r);

                    if (machine.getChargeMagnitude() < machine.getMachineItem().current) return;

                    if (recipe.validInputs().contains(r)) {
                        if (item.contains(input0Comp)) {
                            determineOutputOneSlot(inv, recipe, machine, machInv, input0, inputSlots[0], recipe.validInputs().indexOf(r));
                            return;
                        } else if (item.contains(input1Comp)) {
                            determineOutputOneSlot(inv, recipe, machine, machInv, input1, inputSlots[1], recipe.validInputs().indexOf(r));
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void determineOutputOneSlot(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, ItemStack input, int inputSlot, int inputRecipeIndex) {

        boolean hasStackable = false;
        int stackableSlot = -1;
        ItemStack result = recipe.validOutputs().get(inputRecipeIndex)[0];

        for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {
            if (inv.getItem(outputSlot) != null) {
                if (inv.getItem(outputSlot).getAmount() < 64 && inv.getItem(outputSlot).isSimilar(result)) {
                    hasStackable = true;
                    stackableSlot = outputSlot;
                }
            }
        }

        for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {
            if (hasStackable && stackableSlot != -1) {

                if(inv.getItem(inputSlot) != null) {
                    if(input.getAmount() - 1 <= 0) {
                        inv.setItem(inputSlot, null);
                    } else {
                        input.setAmount(input.getAmount() - 1);
                        inv.setItem(inputSlot, input);
                    }
                }

                machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
                machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

                inv.setItem(stackableSlot, result);

                machInv.setInventory(inv);
                break;
            }

            else {

                if(inv.getItem(inputSlot) != null) {
                    if(input.getAmount() - 1 <= 0) {
                        inv.setItem(inputSlot, null);
                    } else {
                        input.setAmount(input.getAmount() - 1);
                        inv.setItem(inputSlot, input);
                    }
                }

                machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
                machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

                inv.setItem(outputSlot, result);
                machInv.setInventory(inv);
            }
        }
    }

    private static void determineOutputTwoSlots(Inventory inv, MachineRecipe recipe, Machine machine, MachineInventory machInv, ItemStack input0, ItemStack input1, int inputSlot0, int inputSlot1, int inputRecipeIndex) {

        boolean hasStackable = false;
        int stackableSlot = -1;
        ItemStack result = recipe.validOutputs().get(inputRecipeIndex)[0];

        for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {
            if (inv.getItem(outputSlot) != null) {
                if (inv.getItem(outputSlot).getAmount() < 64 && inv.getItem(outputSlot).isSimilar(result)) {
                    hasStackable = true;
                    stackableSlot = outputSlot;
                }
            }
        }
            if (hasStackable && stackableSlot != -1) {

                inv.setItem(stackableSlot, result);

                machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
                machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

                machInv.setInventory(inv);
            }

            else {

                for (int outputSlot : machInv.getType().getOutputOrWhitelist()) {

                    if (inv.getItem(inputSlot0) != null && inv.getItem(inputSlot1) != null) {
                        if (input0.getAmount() - 1 <= 0) {
                            inv.setItem(inputSlot0, null);
                        } else {
                            input0.setAmount(input0.getAmount() - 1);
                            inv.setItem(inputSlot0, input0);
                        }

                        if (input1.getAmount() - 1 <= 0) {
                            inv.setItem(inputSlot1, null);
                        } else {
                            input1.setAmount(input1.getAmount() - 1);
                            inv.setItem(inputSlot1, input1);
                        }
                    }

                    machine.setChargeMagnitude(machine.getChargeMagnitude() - machine.getMachineItem().current);
                    machInv.setInventoryEnergy(inv, machine.getChargeMagnitude());

                    inv.setItem(outputSlot, result);
                    machInv.setInventory(inv);
                }
            }
    }

    public static void updateGenerator(Inventory inv, MachineRecipe recipe, Machine generator) {

        if(generator.getChargeMagnitude() >= generator.getMachineItem().maxCharge) {
            generator.setChargeMagnitude(generator.getMachineItem().maxCharge);
            return;
        }

        MachineInventory genInv = generator.getMachineInventory();
        MachineType type = generator.getMachineItem().getType();

        for(ItemStack[] r : recipe.validInputs()) {
            List<Material> mat = new ArrayList<Material>(){{
                Arrays.asList(r).forEach(i -> this.add(i.getType()));
            }};

            if(inv.getContents() != null) {

                final int[] inputSlots = type.getInput();

                for (int inputSlot : inputSlots) {
                    ItemStack a = inv.getItem(inputSlot);

                    if (a != null && mat.contains(a.getType())) {

                        a.setAmount(a.getAmount() - 1);
                        generator.setChargeMagnitude(Math.min(generator.getChargeMagnitude() + generator.getMachineItem().current, generator.getMachineItem().maxCharge));

                        inv.setItem(inputSlot, a);

                        genInv.setInventoryEnergy(inv, generator.getChargeMagnitude());

                        genInv.setInventory(inv);
                    }
                }
            }
        }
    }

    private boolean creates(ItemStack item) {
        if(this.validOutputs() != null) {
            for (ItemStack[] output : this.validOutputs()) {
                List<ItemStack> out = Arrays.asList(output);
                if (out.contains(item)) return true;
            }
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
