package com.etho5.mechanix.menus;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    public static final Map<ItemStack, MenuInventory> menus = new HashMap<>();
    public static final Map<Integer, MenuInventory> ingredientMenus = new HashMap<>();
    public static final Map<Integer, MenuInventory> machineMenus = new HashMap<>();

    public static void generateMenus() {

        List<ItemStack> multi = new ArrayList<>();
        for (Multiblock b : MachineManager.multiblocks) {
            MenuInventory binv = new MenuInventory(b, false);
            menus.put(binv.getItem(), binv);
            multi.add(binv.getItem());
        }
        ItemStack[] multItems = multi.toArray(new ItemStack[0]);
        MenuInventory multis = new MenuInventory(multItems, "Multiblocks", 0);
        menus.put(multis.getItem(), multis);

        List<ItemStack> ings = new ArrayList<>();
        for (IngredientList i : IngredientList.values()) {
            MenuInventory inv = new MenuInventory(i);
            menus.put(i.getItem(), inv);
            ings.add(inv.getItem());
        }

        System.out.println("Menu Manager - ingredient list size: " + ings.size());

        if (ings.size() <= 45) {
            ItemStack[] it = ings.toArray(new ItemStack[0]);
            MenuInventory ing = new MenuInventory(it, "Ingredients and Components", 0);
            ingredientMenus.put(0, ing);
        } else {
            ItemStack[] ingItems = ings.toArray(new ItemStack[0]);
            try {
                for (int x = 0; x < ingItems.length; x += 45) {
                    ItemStack[] t = new ItemStack[45];
                    System.arraycopy(ingItems, x, t, 0, 45);
                    MenuInventory ing = new MenuInventory(t, "Ingredients and Components", x / 45);
                    if (x == 0) menus.put(ing.getItem(), ing);
                    ingredientMenus.put(x / 45, ing);
                }
            } catch (IndexOutOfBoundsException e) {
                ItemStack[] remaining = new ItemStack[ingItems.length % 45];
                int a = ingItems.length - (ingItems.length % 45);
                if (ingItems.length - a >= 0) {
                    System.arraycopy(ingItems, a, remaining, 0, ingItems.length - a);
                }
                MenuInventory rem = new MenuInventory(remaining, "Ingredients and Components", (ingItems.length / 45) + 1);
                ingredientMenus.put(ingItems.length / 45, rem);
            }
        }


        List<ItemStack> machs = new ArrayList<>();
        for (MachineItem m : MachineItem.values()) {
            MenuInventory minv = new MenuInventory(m, false);
            menus.put(minv.getItem(), minv);
            machs.add(minv.getItem());
        }

        System.out.println("Menu Manager - machine list size: " + machs.size());

        if (machs.size() <= 45) {
            ItemStack[] it = machs.toArray(new ItemStack[0]);
            MenuInventory machInv = new MenuInventory(it, "Machines", 0);
            machineMenus.put(0, machInv);
        } else {
            ItemStack[] machItems = machs.toArray(new ItemStack[0]);
            try {
                for (int x = 0; x < machItems.length; x += 45) {
                    ItemStack[] temp = new ItemStack[45];
                    System.arraycopy(machItems, x, temp, 0, 45);
                    MenuInventory minv1 = new MenuInventory(temp, "Machines", x / 45);
                    if (x == 0) menus.put(minv1.getItem(), minv1);
                    machineMenus.put(x / 45, minv1);
                }
            } catch (IndexOutOfBoundsException e) {
                ItemStack[] remaining = new ItemStack[machItems.length % 45];
                int c = machItems.length - (machItems.length % 45);
                if (machItems.length - c >= 0) {
                    System.arraycopy(machItems, c, remaining, 0, remaining.length - c);
                }
                MenuInventory rem = new MenuInventory(remaining, "Machines", (machItems.length / 45) + 1);
                machineMenus.put(machItems.length / 45, rem);
            }
        }
    }
}
