package com.etho5.mechanix.listeners;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineManager;
import org.bukkit.GameMode;
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
        if (MachineManager.isLocationAMachine(loc)) {
            Machine mach = MachineManager.getMachineFromLocation(loc);

            e.setDropItems(false);
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                b.getWorld().dropItem(loc, mach.getMachineItem().getItem());
            }
            mach.deleteMachine();
        }
    }
}
