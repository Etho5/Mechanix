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

public class Refrigerator extends Multiblock {
    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        buildClickHandler(e, d, recipes(), Sound.BLOCK_SNOW_BREAK);
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        // liquid air
        recipes.put(new ItemStack(Material.GLASS_BOTTLE), new ItemStack[]{
                IngredientList.LIQUID_AIR.getItem()
        });

        // ice
        recipes.put(new ItemStack(Material.WATER_BUCKET), new ItemStack[]{
                new ItemStack(Material.ICE)
        });

        // packed ice
        recipes.put(new ItemStack(Material.ICE), new ItemStack[]{
                new ItemStack(Material.PACKED_ICE)
        });

        // blue ice
        recipes.put(new ItemStack(Material.PACKED_ICE), new ItemStack[]{
                new ItemStack(Material.BLUE_ICE)
        });

        return recipes;
    }
}
