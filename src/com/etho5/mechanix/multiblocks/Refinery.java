package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Refinery extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        buildShapedHandler(e, d, recipes(), Sound.BLOCK_FIRE_EXTINGUISH);
    }

    @Override
    public boolean shaped() {
        return true;
    }

    @Override
    public Material clickedBlock() {
        return Material.BREWING_STAND;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        // hydrogen
        recipes.put(IngredientList.HYDROGEN.getItem(4), new ItemStack[]{
                null, IngredientList.LIQUID_AIR.getItem(), null,
                IngredientList.LIQUID_AIR.getItem(), new ItemStack(Material.REDSTONE), IngredientList.LIQUID_AIR.getItem(),
                null, IngredientList.LIQUID_AIR.getItem(), null
        });

        // sodium carbonate
        recipes.put(IngredientList.SODIUM_CARBONATE.getItem(2), new ItemStack[]{
                null, IngredientList.SODA_ASH.getItem(), null,
                IngredientList.SODA_ASH.getItem(), new ItemStack(Material.REDSTONE), IngredientList.SODA_ASH.getItem(),
                null, IngredientList.SODA_ASH.getItem(), null
        });

        // carbon
        recipes.put(IngredientList.CARBON.getItem(2), new ItemStack[]{
                new ItemStack(Material.COAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.COAL),
                new ItemStack(Material.CHARCOAL), new ItemStack(Material.COAL), new ItemStack(Material.CHARCOAL),
                new ItemStack(Material.COAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.COAL)
        });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Allows for a few select chemical reactions to take place.";
    }
}
