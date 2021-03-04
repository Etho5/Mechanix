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

public class Crusher extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildClickHandler(e, d, this.recipes(), Sound.BLOCK_STONE_BREAK);
    }

    @Override
    public boolean shaped() {
        return false;
    }

    @Override
    public Material clickedBlock() {
        return Material.IRON_BARS;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        recipes.put(new ItemStack(Material.STONE), new ItemStack[]{
                new ItemStack(Material.COBBLESTONE),
                new ItemStack(Material.IRON_NUGGET),
                IngredientList.SHINY_DUST.getItem(),
                IngredientList.PITCHBLENDE.getItem()
        });

        recipes.put(new ItemStack(Material.IRON_ORE), new ItemStack[]{
                new ItemStack(Material.IRON_NUGGET),
                IngredientList.IRON_DUST.getItem(),
                IngredientList.PYROLUSITE.getItem(),
        });

        recipes.put(IngredientList.PYROLUSITE.getItem(), new ItemStack[]{
                IngredientList.MANGANESE_OXIDE.getItem()
        });

        recipes.put(IngredientList.PITCHBLENDE.getItem(), new ItemStack[]{
                IngredientList.URANIUM.getItem()
        });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Crushes rock into powders and other useful materials.";
    }
}
