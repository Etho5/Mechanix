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

public class Sieve extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildClickHandler(e, d, recipes(), Sound.BLOCK_SAND_HIT);
    }

    @Override
    public boolean shaped() {
        return false;
    }

    @Override
    public Material clickedBlock() {
        return Material.IRON_TRAPDOOR;
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
                IngredientList.CHROMIUM.getItem()
        });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Filters out valuable materials from solid mixtures.";
    }
}
