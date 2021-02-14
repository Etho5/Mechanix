package com.etho5.mechanix.utils;

import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Bukkit;
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

    public static boolean isSimilar(final ItemStack first, final ItemStack second) {
        boolean similar = false;

        if (first == null || second == null) return false;

        boolean sameMaterial = (first.getType() == second.getType());
        boolean sameAmount = (first.getAmount() == second.getAmount());
        boolean sameHasItemMeta = (first.hasItemMeta() == second.hasItemMeta());

        boolean sameItemMeta = Bukkit.getItemFactory().equals(first.getItemMeta(), second.getItemMeta());

        if (sameMaterial && sameAmount && sameHasItemMeta && sameItemMeta)
            similar = true;

        return similar;
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

    public static boolean isListEmpty(List<?> list) {
        for (Object o : list) {
            if (o != null) return false;
        }
        return true;
    }
}
