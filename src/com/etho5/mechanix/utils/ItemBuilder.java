package com.etho5.mechanix.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

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

    public ItemBuilder addStoredEnchantment(Enchantment enchantment, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.getItemMeta();
        meta.addStoredEnchant(enchantment, Math.min(enchantment.getMaxLevel(), level), true);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setPotionData(Color color, PotionType type) {
        PotionMeta meta = (PotionMeta) this.getItemMeta();
        meta.setBasePotionData(new PotionData(type));
        meta.setColor(color);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }
}