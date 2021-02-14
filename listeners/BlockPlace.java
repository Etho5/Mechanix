package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        if (e.getItemInHand().getType() == Material.AIR) return;

        ItemStack item = e.getItemInHand();
        if (!item.hasItemMeta()) return;

        if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), "items"), PersistentDataType.STRING)) {
            final String type = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "items"), PersistentDataType.STRING);
            final MachineItem mi = MachineItem.fromString(type);
            if(mi != null) {
                final MachineInventory inv = new MachineInventory(mi, mi.getType());
                new Machine(e.getPlayer().getUniqueId(), b.getLocation(), mi, inv);
            }
        }
    }
}
