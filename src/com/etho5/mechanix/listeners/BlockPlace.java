package com.etho5.mechanix.listeners;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
//        Block b = e.getBlockPlaced();
//        if (e.getItemInHand().getType() == Material.AIR) return;
//
//        ItemStack item = e.getItemInHand();
//        if (!item.hasItemMeta()) return;

//        for(MachineItem mitems : MachineItem.values()) {
//            String name = Utils.toSpacedPrint(mitems.toString());
//            final MachineItem mi = MachineItem.fromString(name);
//            if (mi != null) {
//                System.out.println("BlockPlace - is a machine");
//                final MachineInventory inv = new MachineInventory(mi, mi.getType());
//                new Machine(e.getPlayer().getUniqueId(), b.getLocation(), mi, inv);
//                System.out.println("BlockPlace - machine made");
//                return;
//            }
//        }
    }
}
