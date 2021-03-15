package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.menus.MenuBook;
import com.etho5.mechanix.menus.MenuInventory;
import com.etho5.mechanix.multiblocks.XPBottler;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;

        if (e.getItem() != null && MenuBook.isMenuBook(e.getItem())) {
            e.setCancelled(true);
            e.getPlayer().openInventory(new MenuInventory().getInventory());
        }

        if (e.getClickedBlock() != null) {

            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(IngredientList.MACHINE_INFO_TERMINAL.getItem())) {
                if (MachineManager.isLocationAMachine(e.getClickedBlock().getLocation())) {
                    e.setCancelled(true);
                    e.getPlayer().openInventory(new MenuInventory(MachineManager.getMachineFromLocation(e.getClickedBlock().getLocation()).getMachineItem(), true).getInventory());
                }
            }

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location loc = e.getClickedBlock().getLocation();

                if (Multiblock.isMultiblock(e.getClickedBlock())) {
                    e.setCancelled(true);
                    Dispenser d = (Dispenser) e.getClickedBlock().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getState();
                    Multiblock multi = Multiblock.getMultiblock(e.getClickedBlock().getType());

                    if (multi != null) {
                        multi.handleClick(e, d);
                    }
                }

                else if(!MachineManager.isLocationAMachine(loc)) {
                    loc = Utils.getLocationNextTo(e.getClickedBlock().getLocation(), e.getBlockFace());

                    for (MachineItem mitem : MachineItem.values()) {
                        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
                            String name = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                            final MachineItem mi = MachineItem.fromString(ChatColor.stripColor(name));
                            if (mi != null && mi == mitem) {
                                final MachineInventory inv = new MachineInventory(mi, mi.getType());
                                new Machine(e.getPlayer().getUniqueId(), loc, mi, inv);
                                return;
                            }
                        }
                    }
                }

                else {
                    Machine mach = MachineManager.getMachineFromLocation(loc);
                    e.setCancelled(true);
                    if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(IngredientList.MACHINE_INFO_TERMINAL.getItem())) {
                        e.getPlayer().openInventory(new MenuInventory(mach.getMachineItem(), true).getInventory());
                    } else if (mach.isAllowedPlayer(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission("mechanix.*")) {
                        e.getPlayer().openInventory(mach.getMachineInventory().getInventory());
                    }
                }
            }
        }
    }
}
