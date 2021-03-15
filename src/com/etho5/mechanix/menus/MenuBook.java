package com.etho5.mechanix.menus;

import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuBook extends ItemStack {

    public MenuBook() {
        super(new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(ChatColor.DARK_GREEN + "Mechanix Handbook").setLore(ChatColor.GREEN + "Your guide to everything Mechanix-al!").build());
    }

    public static boolean isMenuBook(ItemStack item) {
        if(item.getItemMeta() == null) return false;
        ItemMeta meta = item.getItemMeta();

        if(item.getType() != Material.ENCHANTED_BOOK) return false;
        if(!meta.getDisplayName().equals(ChatColor.DARK_GREEN + "Mechanix Handbook")) return false;
        if(meta.getLore() == null) return false;
        else return meta.getLore().get(0).equals(ChatColor.GREEN + "Your guide to everything Mechanix-al!");
    }

}
