package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.Direction;
import com.etho5.mechanix.menus.MenuInventory;
import com.etho5.mechanix.menus.MenuManager;
import com.etho5.mechanix.runnable.OpenInventory;
import com.etho5.mechanix.runnable.UpdateMachine;
import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        final InventoryHolder holder = e.getInventory().getHolder();
        if(holder instanceof MachineInventory) {

            if(e.getClickedInventory() == p.getOpenInventory().getBottomInventory()) return;

            MachineInventory machInv = (MachineInventory) holder;
            if(!machInv.getType().getClickableSlotList().contains(e.getRawSlot())) e.setCancelled(true);

            if(machInv.getType() == MachineType.WIRE) e.setCancelled(true);

            else if(machInv.getType() == MachineType.MACHINE) {
                if (e.getRawSlot() == 27) {
                    new UpdateMachine(machInv.getMachine()).runTaskLater(Main.getInstance(), machInv.getMachine().getMachineItem().delay);
                } else if (e.getRawSlot() == 18 && e.getInventory().getItem(18) != null) {
                    if (e.getInventory().getItem(18).isSimilar(MachineInventory.addPlayers)) {
                        e.setCancelled(true);
                        AsyncPlayerChat.addingPlayers.put(p, machInv.getMachine());
                        p.closeInventory();
                        p.sendMessage(ChatColor.GREEN + "Please name a player to add to this machine:");
                    }
                }
            }

            else if(machInv.getType() == MachineType.CARGO) {
                if(e.getRawSlot() == 20) {
                    e.setCancelled(true);
                    if(e.getClick() == ClickType.LEFT) {
                        machInv.setNetwork(machInv.getInventory(), machInv.getNetwork() + 1);
                    } else if(e.getClick() == ClickType.RIGHT) {
                        machInv.setNetwork(machInv.getInventory(), machInv.getNetwork() - 1);
                    }
                }

                else if(machInv.getItem() == MachineItem.DIRECTOR_PIPE) {
                    e.setCancelled(true);
                    if(e.getRawSlot() == 24) {
                        Direction d = machInv.getDirection();
                        machInv.changeDirection(machInv.getInventory(), d.next());
                    }
                }
            }
        } else if(holder instanceof MenuInventory) {
            e.setCancelled(true);

            MenuInventory menu = (MenuInventory) holder;

            if(e.getClickedInventory() == p.getOpenInventory().getBottomInventory()) return;
            if(e.getCurrentItem() == null) return;

            if(e.getCurrentItem().isSimilar(new ItemBuilder(Material.GREEN_WOOL).setDisplayName(ChatColor.GREEN + "Next page").build())) {
                if(MenuManager.ingredientMenus.containsKey(menu.getPage() + 1)) {
                    MenuInventory newMenu = MenuManager.ingredientMenus.get(menu.getPage() + 1);
                    p.closeInventory();
                    new OpenInventory(p, newMenu.getInventory()).runTaskLater(Main.getInstance(), 1);
                } else if(MenuManager.machineMenus.containsKey(menu.getPage() + 1)) {
                    MenuInventory newMenu = MenuManager.machineMenus.get(menu.getPage() + 1);
                    p.closeInventory();
                    new OpenInventory(p, newMenu.getInventory()).runTaskLater(Main.getInstance(), 1);
                }
            }

            else if(e.getCurrentItem().isSimilar(new ItemBuilder(Material.RED_WOOL).setDisplayName(ChatColor.RED + "Last page").build())) {
                if(MenuManager.ingredientMenus.containsKey(menu.getPage() - 1)) {
                    MenuInventory newMenu = MenuManager.ingredientMenus.get(menu.getPage() - 1);
                    p.closeInventory();
                    new OpenInventory(p, newMenu.getInventory()).runTaskLater(Main.getInstance(), 1);
                } else if(MenuManager.machineMenus.containsKey(menu.getPage() - 1)) {
                    MenuInventory newMenu = MenuManager.machineMenus.get(menu.getPage() - 1);
                    p.closeInventory();
                    new OpenInventory(p, newMenu.getInventory()).runTaskLater(Main.getInstance(), 1);
                }
            }

            for(ItemStack key : MenuManager.menus.keySet()) {
                if(e.getCurrentItem().isSimilar(key)) {
                    p.closeInventory();
                    new OpenInventory(p, MenuManager.menus.get(key).getInventory()).runTaskLater(Main.getInstance(), 1);
                }
            }
        }
    }
}

