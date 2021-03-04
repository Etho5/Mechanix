package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.menus.MenuBook;
import com.etho5.mechanix.menus.MenuInventory;
import com.etho5.mechanix.multiblocks.XPBottler;
import org.bukkit.Bukkit;
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

        if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(new MenuBook())) {
            e.setCancelled(true);
            e.getPlayer().openInventory(new MenuInventory().getInventory());
        }

        if (e.getClickedBlock() != null) {

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location loc = e.getClickedBlock().getLocation();

                if (Main.getInstance().getManager().isLocationAMachine(loc)) {
                    e.setCancelled(true);
                    Bukkit.broadcastMessage("ran rightclick");
                    Machine mach = Main.getInstance().getManager().getMachineFromLocation(loc);
                    if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(IngredientList.MACHINE_INFO_TERMINAL.getItem())) {
                        if (new MachineManager().isLocationAMachine(e.getClickedBlock().getLocation())) {
                            e.getPlayer().openInventory(new MenuInventory(mach.getMachineItem(), true).getInventory());
                        } else if (mach.isAllowedPlayer(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission("mechanix.*")) {
                            e.getPlayer().openInventory(mach.getMachineInventory().getInventory());
                        }

                    } else if (Multiblock.isMultiblock(e.getClickedBlock())) {
                        e.setCancelled(true);
                        Dispenser d = (Dispenser) e.getClickedBlock().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getState();
                        Multiblock multi = Multiblock.getMultiblock(e.getClickedBlock().getType());

                        if (multi instanceof XPBottler) {
                            if (e.getPlayer().getLevel() > 0) {
                                e.getPlayer().setLevel(e.getPlayer().getLevel() - 1);
                                multi.handleClick(e, d);
                            }

                        } else if (multi != null) {
                            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(IngredientList.MACHINE_INFO_TERMINAL.getItem())) {
                                if (new MachineManager().isLocationAMachine(e.getClickedBlock().getLocation())) {
                                    e.setCancelled(true);
                                    e.getPlayer().openInventory(new MenuInventory(multi, true).getInventory());
                                }
                            } else multi.handleClick(e, d);
                        }
                    }
                }
            }
        }
    }
}
