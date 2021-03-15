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

public class ArcFurnace extends MachineRecipe {

    @Override
    public MachineItem machineItem() {
        return MachineItem.ARC_FURNACE;
    }

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateMachineBothSlots(inv, this, machine, false);
    }

    @Override
    public List<ItemStack[]> validInputs() {
        List<ItemStack[]> l = new ArrayList<>();

        l.add(new ItemStack[]{IngredientList.STEEL.getItem(), IngredientList.OXYGEN.getItem() });
        l.add(new ItemStack[]{IngredientList.GIBBSITE.getItem(), new ItemStack(Material.WATER_BUCKET)});
        l.add(new ItemStack[]{IngredientList.ALUMINIUM_OXIDE.getItem(4), IngredientList.CHROMIUM.getItem()});
        l.add(new ItemStack[]{IngredientList.ALUMINIUM_OXIDE.getItem(4), IngredientList.IRON_DUST.getItem()});

        return l;
    }

    @Override
    public List<ItemStack[]> validOutputs() {
        List<ItemStack[]> l = new ArrayList<>();

        l.add(new ItemStack[]{IngredientList.STEEL_INGOT.getItem() });
        l.add(new ItemStack[]{IngredientList.ALUMINIUM_OXIDE.getItem()});
        l.add(new ItemStack[]{IngredientList.SYNTHETIC_RUBY.getItem()});
        l.add(new ItemStack[]{IngredientList.SYNTHETIC_SAPPHIRE.getItem()});

        return l;
    }
}
