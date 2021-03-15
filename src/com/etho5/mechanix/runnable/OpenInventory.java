package com.etho5.mechanix.runnable;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class OpenInventory extends BukkitRunnable {

    private final Inventory inv;
    private final Player p;
    public OpenInventory(Player p, Inventory inv) {
        this.p = p;
        this.inv = inv;
    }

    @Override
    public void run() {
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.5F, 3F);
    }
}
