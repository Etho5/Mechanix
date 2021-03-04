package com.etho5.mechanix.utils;

import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static String toSpacedPrint(String namespacedString) {
        StringBuilder camel = new StringBuilder(namespacedString.replace('_', ' '));
        for(int x = 0; x < camel.length(); x++) {
            if(x == 0 || camel.charAt(x - 1) == ' ') {
                char c = Character.toUpperCase(camel.charAt(x));
                camel.setCharAt(x, c);
            }
        }
        return camel.toString();
    }

    public static boolean isFull(Inventory inv) {
        return inv.firstEmpty() == -1;
    }

    public static ItemStack getRandomItem(ItemStack[] possibilities) {
        return possibilities[ThreadLocalRandom.current().nextInt(possibilities.length)];
    }

    public static ItemStack getMachineItem(String machineName, int amount) {
       ItemStack item = MachineItem.fromString(machineName).getItem();
       item.setAmount(amount);
       return item;
    }

    public static ItemStack getIngredientItem(String ingredientName, int amount) {
        ItemStack item = IngredientList.fromString(ingredientName).getItem();
        item.setAmount(amount);
        return item;
    }

    public static ItemStack getLastItem(Inventory inv) {
        for(int i = inv.getSize() - 1; i >= 0; i--) {
            if(inv.getItem(i) != null) return inv.getItem(i);
        }
        return null;
    }

    public static int getLastItemIndex(Inventory inv) {
        for(int i = inv.getSize() - 1; i >= 0; i--) {
            if(inv.getItem(i) != null) return i;
        }
        return -1;
    }
}
