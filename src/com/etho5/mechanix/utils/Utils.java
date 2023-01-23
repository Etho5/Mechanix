package com.etho5.mechanix.utils;

import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemStack item = MachineItem.fromUnformattedString(machineName).getItem();
        item.setAmount(amount);
        return item;
    }

    public static ItemStack getIngredientItem(String ingredientName, int amount) {
        ItemStack item = IngredientList.fromString(ingredientName).getItem();
        item.setAmount(amount);
        return item;
    }

    public static ItemStack getLastItem(List<ItemStack> inv) {
        for(int i = inv.size() - 1; i >= 0; i--) {
            if(inv.get(i) != null) return inv.get(i);
        }
        return null;
    }

    public static int getLastItemIndex(List<ItemStack> inv) {
        for(int i = inv.size() - 1; i >= 0; i--) {
            if(inv.get(i) != null) return i;
        }
        return -1;
    }

    public static boolean isSimilar(ItemStack one, ItemStack two) {
        if(one == null || two == null) return false;
        ItemMeta uno = one.getItemMeta();
        ItemMeta dos = two.getItemMeta();
        if(one.getType() != two.getType()) return false;
        if(uno == null || dos == null) return false;
        if(!uno.getDisplayName().equals(dos.getDisplayName())) return false;
        if(uno.getLore() != null && dos.getLore() != null) return uno.getLore().equals(dos.getLore());
        return true;
    }

    public static Location getLocationNextTo(Location originalLocation, BlockFace face) {
        switch(face) {
            case EAST:
                originalLocation.setX(originalLocation.getX() + 1);
                break;
            case WEST:
                originalLocation.setX(originalLocation.getX() - 1);
                break;
            case UP:
                originalLocation.setY(originalLocation.getY() + 1);
                break;
            case DOWN:
                originalLocation.setY(originalLocation.getY() - 1);
                break;
            case SOUTH:
                originalLocation.setZ(originalLocation.getZ() + 1);
                break;
            case NORTH:
                originalLocation.setZ(originalLocation.getZ() - 1);
                break;
        }
        return originalLocation;
    }
}
