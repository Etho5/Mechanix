package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Sieve extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildClickHandler(e, d, recipes(), Sound.BLOCK_SAND_HIT);
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        recipes.put(IngredientList.SHINY_DUST.getItem(), new ItemStack[]{
                IngredientList.SULFUR.getItem(),
                IngredientList.COPPER.getItem(),
                IngredientList.ALUMINIUM.getItem(),
                IngredientList.LEAD.getItem(),
                IngredientList.ZINC.getItem(),
                IngredientList.VANADIUM.getItem(),
        });

        return recipes;
    }
}
