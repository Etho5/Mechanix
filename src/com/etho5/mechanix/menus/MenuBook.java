package com.etho5.mechanix.menus;

import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuBook extends ItemStack {

    public MenuBook() {
        super(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ChatColor.DARK_GREEN + "Mechanix Handbook").setLore(ChatColor.GREEN + "Your guide to everything Mechanix-al!").build());
    }
}
