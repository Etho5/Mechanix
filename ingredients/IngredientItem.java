package com.etho5.mechanix.ingredients;

import com.etho5.mechanix.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class IngredientItem extends ItemStack {

    public IngredientItem(IngredientList listItem) {
        super(listItem.getMaterial(), 1);
        ItemMeta meta = this.getItemMeta();
        meta.setLore(Collections.singletonList(ChatColor.GREEN + listItem.getDescription()));
        meta.setDisplayName(listItem.getNameColor() + Utils.toSpacedPrint(listItem.toString().toLowerCase()));
        if(listItem.getCustomModelData() != -1) {
            meta.setCustomModelData(listItem.getCustomModelData());
        }
        this.setItemMeta(meta);
    }
}
