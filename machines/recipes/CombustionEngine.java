package com.etho5.mechanix.machines.recipes;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CombustionEngine extends MachineRecipe {

    @Override
    public void handleClick(Inventory inv, Machine machine) {
        MachineRecipe.updateGenerator(inv,this, machine);
    }

    @Override
    public List<Material[]> validInputs() {
        List<Material[]> inputs = new ArrayList<>();

        inputs.add(new Material[]{
                Material.COAL,
                Material.CHARCOAL
        });

        return inputs;
    }

    @Override
    public List<Material[]> validOutputs() {
        return null;
    }
}
