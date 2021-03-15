package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Drill extends Multiblock {

    @Override
    public String name() {
        return "Drill";
    }

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        handleDrill(e, d, Sound.BLOCK_METAL_BREAK);
    }

    @Override
    public boolean shaped() {
        return false;
    }

    @Override
    public Material clickedBlock() {
        return Material.BEACON;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        return null;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Upon right-clicking, mines out any ore blocks directly beneath it.";
    }
}
