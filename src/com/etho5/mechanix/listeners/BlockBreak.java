package com.etho5.mechanix.listeners;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.Machine;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Location loc = b.getLocation();
        if(Main.getInstance().getManager().isLocationAMachine(loc)) {
            Machine mach = Main.getInstance().getManager().getMachineFromLocation(loc);

            e.setDropItems(false);
            b.getWorld().dropItem(loc, mach.getMachineItem().getItem());
            mach.deleteMachine(e.getPlayer());
        }
    }
}
