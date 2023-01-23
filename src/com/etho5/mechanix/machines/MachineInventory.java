package com.etho5.mechanix.machines;

import com.etho5.mechanix.machines.cargo.Direction;
import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MachineInventory implements InventoryHolder, Serializable {

    protected transient Inventory inventory;
    protected MachineItem item;
    private transient final MachineType type;
    public static final ItemStack addPlayers = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(ChatColor.AQUA + "Click to add players").build();
    public static final ItemBuilder networkPanel = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setDisplayName(ChatColor.DARK_GRAY + "Network (right click + 1, left click - 1):");
    public static final ItemBuilder directionPanel = new ItemBuilder(Material.COMPASS).setDisplayName(ChatColor.GREEN + "Direction: ");
    public static ItemStack energy = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Energy: " + ChatColor.RED + "0J").build();
    private int network = 1;
    private Direction direction = Direction.POS_X;

    public MachineInventory(MachineItem item, MachineType type) {
        this.item = item;
        this.type = item.getType();

        this.inventory = Bukkit.createInventory(this, type.getSize(), item.getDisplayName());

        if(type == MachineType.CARGO) {
            for (int i = 0; i < type.getSize(); i++) {
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }
        } else {
            List<Integer> blank = new ArrayList<>(type.getClickableSlotList());

            for (int i = 0; i < type.getSize(); i++) {
                if (!(blank.contains(i))) inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            }
        }

        switch (type) {
            case MACHINE:
            case GENERATOR:
                inventory.setItem(9, item.getItem());
                inventory.setItem(18, addPlayers);
                inventory.setItem(27, energy);
                break;
            case WIRE:
                inventory.setItem(13, energy);
                break;
            case JUNCTION_WIRE:
                inventory.setItem(11, directionPanel.build());
                inventory.setItem(15, energy);
                break;
            case CARGO:
                if(item == MachineItem.GATEWAY_PIPE) {
                    inventory.setItem(18, item.getItem());
                    networkPanel.setLore(ChatColor.YELLOW + "" + network);
                    inventory.setItem(20, networkPanel.build());
                    for(int i : type.getOutputOrWhitelist()) {
                        inventory.setItem(i, null);
                    }
                }
                else {
                    inventory.setItem(20, networkPanel.build());
                    inventory.setItem(22, item.getItem());
                    inventory.setItem(24, directionPanel.build());
                    this.type.setOutputOrWhitelist(null);
                }
                break;
        }
    }

    public MachineInventory(MachineItem item, List<ItemStack> contents) {
        this.item = item;
        this.type = item.getType();
        ItemStack[] con = new ItemStack[contents.size()];
        for(int y = 0; y < contents.size(); y++) {
            con[y] = contents.get(y);
        }
        this.inventory = Bukkit.createInventory(this, con.length, item.getDisplayName());
        this.inventory.setContents(con);
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

    public int getNetwork() {
        return network;
    }

    public void setNetwork(Inventory inv, int network) {
        this.network = network;
        networkPanel.setLore(ChatColor.YELLOW + "" + network);
        inv.setItem(20, networkPanel.build());
    }

    public MachineItem getItem() {
        return item;
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeDirection(Inventory inv, Direction direction) {
        this.direction = direction;
        directionPanel.setLore(ChatColor.YELLOW + direction.toString());
        inv.setItem(24, directionPanel.build());
    }

    public void setInventoryEnergy(Inventory inv, double val) {
        if (this.getMachine().getMachineItem().getType() == MachineType.WIRE) {
            if (val >= 1000) {
                val = val * 0.001;
                inv.setItem(15, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Charge: " + ChatColor.RED + val + "C").build());
            } else {
                inv.setItem(15, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Charge: " + ChatColor.RED + val + "J").build());
            }
        } else {
            if (val >= 1000) {
                val = val * 0.001;
                inv.setItem(27, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Charge: " + ChatColor.RED + val + "kJ").build());
            } else {
                inv.setItem(27, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "Click to activate").setLore(ChatColor.YELLOW + "Charge: " + ChatColor.RED + val + "J").build());
            }
        }
    }
}
