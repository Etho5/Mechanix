package com.etho5.mechanix.machines;

import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MachineInventory implements InventoryHolder {

    protected transient Inventory inventory;
    protected MachineItem item;
    private transient final MachineType type;
    protected ItemStack energy, addPlayers;

    public MachineInventory(MachineItem item, MachineType type) {
        this.type = type;
        this.item = item;
        this.addPlayers = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(ChatColor.AQUA + "Click to add players").build();
        this.energy = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + "0J").build();

        this.inventory = Bukkit.createInventory(this, type.getSize(), item.getDisplayName());
        List<Integer> blank = new ArrayList<>(type.getClickableSlotList());

        for (int i = 0; i < type.getSize(); i++) {
            if (!(blank.contains(i))) inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        switch (type) {
            case MACHINE:
            case CARGO:
            case GENERATOR:
                inventory.setItem(9, item.getItem(1));
                inventory.setItem(18, addPlayers);
                inventory.setItem(27, energy);
                break;
            case WIRE:
                inventory.setItem(11, item.getItem(1));
                inventory.setItem(15, energy);
                break;
        }
    }

    public Machine getMachine() {
        return Machine.invMap.get(this);
    }

    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inv) {
        this.inventory = inv;
    }

    public MachineType getType() {
        return type;
    }

    public void setInventoryEnergy(Inventory inv, double val) {
        if (this.getMachine().getMachineItem().getType() == MachineType.WIRE) {
            if (val >= 1000) {
                val = val * 0.001;
                inv.setItem(15, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + val + "kJ").build());
            } else {
                inv.setItem(15, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + val + "J").build());
            }
        } else {
            if (val >= 1000) {
                val = val * 0.001;
                inv.setItem(27, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + val + "kJ").build());
            } else {
                inv.setItem(27, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + val + "J").build());
            }
        }
    }
}
