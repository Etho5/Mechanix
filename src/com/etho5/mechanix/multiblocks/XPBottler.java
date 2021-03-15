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

public class XPBottler extends Multiblock {

    @Override
    public String name() {
        return "XP Bottler";
    }

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        buildClickHandler(e, d, recipes(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    @Override
    public boolean shaped() {
        return false;
    }

    @Override
    public Material clickedBlock() {
        return Material.ENCHANTING_TABLE;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        recipes.put(new ItemStack(Material.GLASS_BOTTLE), new ItemStack[]{ new ItemStack(Material.EXPERIENCE_BOTTLE) });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Saps the user's XP and bottles it for them.";
    }
}
