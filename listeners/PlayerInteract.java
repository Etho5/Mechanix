package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.abstraction.Multiblock;
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
        if(e.getHand() != EquipmentSlot.HAND) return;

        if (e.getClickedBlock() != null) {

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location loc = e.getClickedBlock().getLocation();

                if (Main.getInstance().getManager().isLocationAMachine(loc)) {
                    e.setCancelled(true);
                    Bukkit.broadcastMessage("ran rightclick");
                    Machine mach = Main.getInstance().getManager().getMachineFromLocation(loc);
                    if(mach.isAllowedPlayer(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission("mechanix.*")) {
                        e.getPlayer().openInventory(mach.getMachineInventory().getInventory());
                    }

                } else if (Multiblock.isMultiblock(e.getClickedBlock())) {
                    e.setCancelled(true);
                    Dispenser d = (Dispenser) e.getClickedBlock().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getState();
                    Multiblock multi = Multiblock.getMultiblock(e.getClickedBlock().getType());
                    if(multi != null) multi.handleClick(e, d);
                }
            }
        }
    }
}
