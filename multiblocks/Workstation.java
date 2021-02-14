package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Workstation extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildShapedHandler(e, d, this.recipes(), Sound.BLOCK_METAL_STEP);
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        //TODO update for each new recipe

        // copper wire
        recipes.put(IngredientList.COPPER_WIRE.getItem(3), new ItemStack[]{
                null, null, IngredientList.COPPER_INGOT.getItem(),
                null, IngredientList.COPPER_INGOT.getItem(), null,
                IngredientList.COPPER_INGOT.getItem(), null, null
        });

        // battery
        recipes.put(IngredientList.BATTERY.getItem(), new ItemStack[]{
                IngredientList.ZINC.getItem(), new ItemStack(Material.IRON_NUGGET), IngredientList.ZINC.getItem(),
                IngredientList.MANGANESE_OXIDE.getItem(), IngredientList.CARBON.getItem(), IngredientList.ZINC_CHLORIDE.getItem(),
                IngredientList.ZINC.getItem(), new ItemStack(Material.IRON_NUGGET), IngredientList.ZINC.getItem()
        });

        // silicon wafer
        recipes.put(IngredientList.SILICON_WAFER.getItem(), new ItemStack[]{
                IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem(),
                IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem(),
                IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem(), IngredientList.SILICON.getItem()
        });

        // carbon brush
        recipes.put(IngredientList.CARBON_BRUSH.getItem(), new ItemStack[]{
                null, IngredientList.COPPER_WIRE.getItem(), null,
                null, IngredientList.COPPER.getItem(), null,
                null, IngredientList.CARBON.getItem(), null
        });

        // commutator
        recipes.put(IngredientList.COMMUTATOR.getItem(), new ItemStack[]{
                null, IngredientList.CARBON_BRUSH.getItem(), null,
                IngredientList.CARBON_BRUSH.getItem(), IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.CARBON_BRUSH.getItem(),
                null, IngredientList.CARBON_BRUSH.getItem(), null
        });

        // motor
        recipes.put(IngredientList.MOTOR.getItem(), new ItemStack[]{
                IngredientList.ELECTROMAGNET.getItem(), IngredientList.COPPER_WIRE.getItem(), IngredientList.ELECTROMAGNET.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.COMMUTATOR.getItem(), IngredientList.COPPER_WIRE.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.BATTERY.getItem(), IngredientList.COPPER_WIRE.getItem()
        });

        // heating coil
        recipes.put(IngredientList.HEATING_COIL.getItem(), new ItemStack[]{
                IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.BATTERY.getItem(), IngredientList.COPPER_WIRE.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem()
        });

        // combustion engine
        recipes.put(MachineItem.COMBUSTION_ENGINE.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_INGOT.getItem(), new ItemStack(Material.IRON_BLOCK), IngredientList.ALUMINIUM_INGOT.getItem(),
                IngredientList.HEATING_COIL.getItem(), IngredientList.MOTOR.getItem(), IngredientList.HEATING_COIL.getItem(),
                IngredientList.ALUMINIUM_INGOT.getItem(), new ItemStack(Material.IRON_BLOCK), IngredientList.ALUMINIUM_INGOT.getItem()
        });

        // arc furnace
        recipes.put(MachineItem.ARC_FURNACE.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_ALLOY_INGOT.getItem(), IngredientList.ALUMINIUM_ALLOY_INGOT.getItem(), IngredientList.ALUMINIUM_ALLOY_INGOT.getItem(),
                IngredientList.HEATING_COIL.getItem(), new ItemStack(Material.BLAST_FURNACE), IngredientList.HEATING_COIL.getItem(),
                IngredientList.COMPUTER_CHIP.getItem(), IngredientList.HEATING_COIL.getItem(), IngredientList.MOTOR.getItem()
        });

        // lead acid battery
        recipes.put(IngredientList.LEAD_ACID_BATTERY.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_INGOT.getItem(), new ItemStack(Material.IRON_INGOT), IngredientList.ALUMINIUM_INGOT.getItem(),
                IngredientList.LEAD_DIOXIDE.getItem(), IngredientList.SULFURIC_ACID.getItem(), IngredientList.LEAD_INGOT.getItem(),
                IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem()
        });

        // solar cell
        recipes.put(IngredientList.SOLAR_CELL.getItem(), new ItemStack[]{
                new ItemStack(Material.GLASS_PANE), new ItemStack(Material.GLASS_PANE), new ItemStack(Material.GLASS_PANE),
                IngredientList.SILICON_WAFER.getItem(), IngredientList.BATTERY.getItem(), IngredientList.SILICON_WAFER.getItem(),
                IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem()
        });

        // solar panel
        recipes.put(MachineItem.SOLAR_PANEL.getItem(), new ItemStack[]{
                IngredientList.SOLAR_CELL.getItem(), IngredientList.SOLAR_CELL.getItem(), IngredientList.SOLAR_CELL.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(),
                IngredientList.STEEL.getItem(), IngredientList.LEAD_ACID_BATTERY.getItem(), IngredientList.STEEL.getItem()
        });

        // auto anvil
        recipes.put(MachineItem.AUTO_ANVIL.getItem(), new ItemStack[]{
                IngredientList.STEEL.getItem(), new ItemStack(Material.ANVIL), IngredientList.STEEL.getItem(),
                IngredientList.COMPUTER_CHIP.getItem(), IngredientList.MOTOR.getItem(), IngredientList.LEAD_ACID_BATTERY.getItem(),
                IngredientList.STEEL.getItem(), IngredientList.STEEL.getItem(), IngredientList.STEEL.getItem()
        });

        // wire
        recipes.put(MachineItem.WIRE.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.COMPUTER_CHIP.getItem(), IngredientList.ALUMINIUM_INGOT.getItem(),
                IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(), IngredientList.COPPER_WIRE.getItem(),
                IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem(), IngredientList.ALUMINIUM_INGOT.getItem()
        });

       return recipes;
    }
}
