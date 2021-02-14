package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.runnable.UpdateMachine;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

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

            if (e.getRawSlot() == 27) {
                new UpdateMachine(e.getClickedInventory(), machInv.getMachine()).runTask(Main.getInstance());
            }

            else if (e.getRawSlot() == 18) {
                e.setCancelled(true);
                AsyncPlayerChat.addingPlayers.put(p, machInv.getMachine());
                p.closeInventory();
                p.sendMessage(ChatColor.GREEN + "Please name a player to add to this machine:");
            }
        }
    }
}

