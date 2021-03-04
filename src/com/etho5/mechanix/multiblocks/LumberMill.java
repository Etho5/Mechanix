package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LumberMill extends Multiblock {

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        Multiblock.buildClickHandler(e, d, recipes(), Sound.BLOCK_WOOD_BREAK);
    }

    @Override
    public boolean shaped() {
        return false;
    }

    @Override
    public Material clickedBlock() {
        return Material.STONECUTTER;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        recipes.put(new ItemStack(Material.OAK_WOOD), new ItemStack[]{ new ItemStack(Material.OAK_PLANKS, 8) });
        recipes.put(new ItemStack(Material.OAK_LOG), new ItemStack[]{ new ItemStack(Material.OAK_PLANKS, 6) });

        recipes.put(new ItemStack(Material.DARK_OAK_WOOD), new ItemStack[]{ new ItemStack(Material.DARK_OAK_PLANKS, 8) });
        recipes.put(new ItemStack(Material.DARK_OAK_LOG), new ItemStack[]{ new ItemStack(Material.DARK_OAK_PLANKS, 6) });

        recipes.put(new ItemStack(Material.ACACIA_WOOD), new ItemStack[]{ new ItemStack(Material.ACACIA_PLANKS, 8) });
        recipes.put(new ItemStack(Material.ACACIA_LOG), new ItemStack[]{ new ItemStack(Material.ACACIA_PLANKS, 6) });

        recipes.put(new ItemStack(Material.BIRCH_WOOD), new ItemStack[]{ new ItemStack(Material.BIRCH_PLANKS, 8) });
        recipes.put(new ItemStack(Material.BIRCH_LOG), new ItemStack[]{ new ItemStack(Material.BIRCH_PLANKS, 6) });

        recipes.put(new ItemStack(Material.SPRUCE_WOOD), new ItemStack[]{ new ItemStack(Material.SPRUCE_PLANKS, 8) });
        recipes.put(new ItemStack(Material.SPRUCE_LOG), new ItemStack[]{ new ItemStack(Material.SPRUCE_PLANKS, 6) });

        recipes.put(new ItemStack(Material.JUNGLE_WOOD), new ItemStack[]{ new ItemStack(Material.JUNGLE_PLANKS, 8) });
        recipes.put(new ItemStack(Material.JUNGLE_LOG), new ItemStack[]{ new ItemStack(Material.JUNGLE_PLANKS, 6) });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Creates planks more efficiently than manual crafting.";
    }
}
