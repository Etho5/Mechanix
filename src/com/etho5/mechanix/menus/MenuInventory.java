package com.etho5.mechanix.menus;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MenuInventory implements InventoryHolder {

    private final ItemStack item;
    protected transient Inventory inventory;
    private final int page;
    public static final ItemStack nextPage = new ItemBuilder(Material.GREEN_WOOL).setDisplayName(ChatColor.GREEN + "Next page").build();
    public static final ItemStack lastPage = new ItemBuilder(Material.RED_WOOL).setDisplayName(ChatColor.RED + "Last page").build();


    // default menu, shown upon opening guide
    public MenuInventory() {
        this.page = 0;
        this.item = new ItemStack(Material.AIR);
        this.inventory = Bukkit.createInventory(this, 27, "Mechanix");
        for(ItemStack i : this.inventory.getContents()) {
            i.setType(Material.GREEN_STAINED_GLASS_PANE);
        }
        this.inventory.setItem(11, new ItemBuilder(Material.DISPENSER).setDisplayName(ChatColor.GOLD + "Multiblocks").build());
        this.inventory.setItem(13, new ItemBuilder(Material.GUNPOWDER).setDisplayName(ChatColor.GRAY + "Ingredients and Components").build());
        this.inventory.setItem(15, new ItemBuilder(Material.IRON_BLOCK).setDisplayName(ChatColor.AQUA + "Machines").build());
    }

    // menu for a category of items, such as "Multiblocks" or "Machines"
    public MenuInventory(ItemStack[] contents, String name, int page) {
        this.page = page;
        if(contents.length > 45) throw new ArrayIndexOutOfBoundsException();

        switch (name) {
            case "Multiblocks":
                this.item = new ItemBuilder(Material.DISPENSER).setDisplayName(ChatColor.GOLD + "Multiblocks").build();
                break;
            case "Ingredients and Components":
                this.item = new ItemBuilder(Material.GUNPOWDER).setDisplayName(ChatColor.GRAY + "Ingredients and Components").build();
                break;
            case "Machines":
                this.item = new ItemBuilder(Material.IRON_BLOCK).setDisplayName(ChatColor.AQUA + "Machines").build();
                break;
            default:
                this.item = new ItemStack(Material.AIR);
                break;
        }

        this.inventory = Bukkit.createInventory(this, 54, name);
        this.inventory.addItem(contents);
        this.inventory.setItem(45, lastPage);
        this.inventory.setItem(53, nextPage);
    }

    // if "recipe" = false, menu for the creation of a Multiblock, as in the Multiblock placement
    // if "recipe" = true, menu showing all possible recipes used in the given Multiblock
    public MenuInventory(Multiblock multi, boolean recipe) {
        this.page = 0;
        this.item = new ItemBuilder(multi.clickedBlock()).setDisplayName(multi.getClass().getName()).build();
        if (!recipe) {
            this.inventory = Bukkit.createInventory(this, 45, multi.getClass().getName());
            for (ItemStack i : this.inventory.getContents()) {
                i.setType(Material.BLACK_STAINED_GLASS_PANE);
            }
            for (int x = 0; x < 3; x++) {
                this.inventory.setItem(10 + x, null);
                this.inventory.setItem(19 + x, null);
                this.inventory.setItem(28 + x, null);
            }
            this.inventory.setItem(25, new ItemBuilder(multi.clickedBlock()).setDisplayName(multi.getClass().getName()).setLore(multi.description()).build());
            this.inventory.setItem(20, new ItemStack(multi.clickedBlock()));
            this.inventory.setItem(29, new ItemStack(Material.DISPENSER));
        }
        else {
            this.inventory = Bukkit.createInventory(this, 54, multi.getClass().getName());
            List<ItemStack> outputs = new ArrayList<>(multi.recipes().keySet());
            if(outputs.size() <= 45) {
                this.inventory.addItem(outputs.toArray(new ItemStack[0]));
            } else throw new IndexOutOfBoundsException();
        }
    }

    // if "recipe" = false, menu for the recipe of a Machine, as in how to craft it
    // if "recipe" = true, menu showing all possible recipes used in the given MachineItem
    public MenuInventory(MachineItem item, boolean recipe) {
        this.page = 0;
        this.item = item.getItem();
        if (!recipe) {
            this.inventory = Bukkit.createInventory(this, 45, item.getDisplayName());
            for (ItemStack i : this.inventory.getContents()) {
                i.setType(Material.BLACK_STAINED_GLASS_PANE);
            }
            this.inventory.setItem(25, item.getItem());
            if (Multiblock.comesFromMultiblock(item.getItem())) {
                Multiblock m = Multiblock.getMultiblockFromResult(item.getItem());
                if (m != null) {
                    for (int x = 0; x < 3; x++) {
                        this.inventory.setItem(10 + x, null);
                        this.inventory.setItem(19 + x, null);
                        this.inventory.setItem(28 + x, null);
                    }

                    this.inventory.setItem(23, new ItemBuilder(m.clickedBlock()).setDisplayName(m.getClass().getName()).build());

                    AtomicReference<ItemStack[]> recipes = new AtomicReference<>(new ItemStack[0]);
                    m.recipes().forEach((k, v) -> {
                        if (k.isSimilar(item.getItem())) recipes.set(v);
                    });
                    ItemStack[] items = recipes.get();
                    for (int x = 0; x < 3; x++) {
                        this.inventory.setItem(10 + x, items[x]);
                        this.inventory.setItem(19 + x, items[x + 3]);
                        this.inventory.setItem(28 + x, items[x + 6]);
                    }
                }
            }
        }

        else {
            this.inventory = Bukkit.createInventory(this, 54, item.getDisplayName());
            MachineRecipe machRecipe = item.getRecipe();
            if(machRecipe != null) {
                List<ItemStack> outputs = new ArrayList<>();
                machRecipe.validOutputs().forEach(o -> outputs.addAll(Arrays.asList(o)));
                if (outputs.size() <= 45) {
                    this.inventory.addItem(outputs.toArray(new ItemStack[0]));
                } else throw new IndexOutOfBoundsException();
            }
        }
    }

    // menu for the recipe of an ingredient
    public MenuInventory(IngredientList item) {
        this.page = 0;
        this.item = item.getItem();
        this.inventory = Bukkit.createInventory(this, 45, item.getDisplayName());
        for(ItemStack i : this.inventory.getContents()) {
            i.setType(Material.BLACK_STAINED_GLASS_PANE);
        }
        this.inventory.setItem(25, item.getItem());
        if(Multiblock.comesFromMultiblock(item.getItem())) {
            Multiblock m = Multiblock.getMultiblockFromResult(item.getItem());
            if (m != null) {
                for (int x = 0; x < 3; x++) {
                    this.inventory.setItem(10 + x, null);
                    this.inventory.setItem(19 + x, null);
                    this.inventory.setItem(28 + x, null);
                }

                this.inventory.setItem(23, new ItemBuilder(m.clickedBlock()).setDisplayName(m.getClass().getName()).build());
                if (m.shaped()) {
                    AtomicReference<ItemStack[]> recipe = new AtomicReference<>(new ItemStack[0]);
                    m.recipes().forEach((k, v) -> {
                        if (k.isSimilar(item.getItem())) recipe.set(v);
                    });
                    ItemStack[] items = recipe.get();
                    for (int x = 0; x < 3; x++) {
                        this.inventory.setItem(10 + x, items[x]);
                        this.inventory.setItem(19 + x, items[x + 3]);
                        this.inventory.setItem(28 + x, items[x + 6]);
                    }
                } else {
                    AtomicReference<ItemStack> thing = new AtomicReference<>(null);
                    m.recipes().forEach((k, v) -> {
                        if (k.isSimilar(item.getItem())) thing.set(k);
                    });
                    this.inventory.setItem(20, thing.get());
                }
            }
        }
        else if(MachineRecipe.getWhatCreates(item.getItem()) != null) {
            MachineRecipe recipe = MachineRecipe.getWhatCreates(item.getItem());
            MachineItem machItem = recipe.machineItem();
            this.inventory.setItem(23, machItem.getItem());
            this.inventory.setItem(19, null);
            this.inventory.setItem(20, null);
            AtomicInteger x = new AtomicInteger();
            recipe.validOutputs().forEach(o -> {
                if (Arrays.asList(o).contains(item.getItem())) x.set(recipe.validOutputs().indexOf(o));
            });
            int pos = x.get();
            ItemStack[] input = recipe.validInputs().get(pos);
            if (input.length == 2) {
                this.inventory.setItem(19, input[0]);
                this.inventory.setItem(20, input[1]);
            } else if (input.length == 1) {
                this.inventory.setItem(19, input[0]);
            }
        }
    }

    public ItemStack getItem() {
        return item;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public int getPage() {
        return page;
    }
}
