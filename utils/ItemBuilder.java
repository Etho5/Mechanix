package com.etho5.mechanix.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(new ItemStack(material, 1));
    }

    public ItemBuilder(ItemStack item) {
        this.itemStack = item;
    }

    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    public void setItemMeta(ItemMeta meta) {
        this.itemStack.setItemMeta(meta);
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = this.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.setItemMeta(meta);
        return this;
    }


    public ItemStack build() {
        return this.itemStack;
    }


}