package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Oven extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildClickHandler(e, d, recipes(), Sound.ITEM_FIRECHARGE_USE);
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        recipes.put(new ItemStack(Material.KELP), new ItemStack[]{
            IngredientList.SODA_ASH.getItem()
        });

        recipes.put(IngredientList.COPPER.getItem(), new ItemStack[]{
                IngredientList.COPPER_INGOT.getItem()
        });

        recipes.put(IngredientList.ALUMINIUM.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_INGOT.getItem()
        });

        recipes.put(IngredientList.LEAD.getItem(), new ItemStack[]{
                IngredientList.LEAD_INGOT.getItem()
        });

        recipes.put(IngredientList.ALUMINIUM_ALLOY.getItem(), new ItemStack[]{
                IngredientList.ALUMINIUM_ALLOY_INGOT.getItem()
        });

        return recipes;
    }
}
