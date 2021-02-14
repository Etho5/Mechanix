package com.etho5.mechanix.listeners;

import com.etho5.mechanix.machines.MachineInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getInventory().getHolder() instanceof MachineInventory) {
            ((MachineInventory) e.getInventory().getHolder()).setInventory(e.getInventory());
        }
    }
}
