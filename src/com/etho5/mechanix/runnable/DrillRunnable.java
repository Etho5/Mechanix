package com.etho5.mechanix.runnable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DrillRunnable extends BukkitRunnable {

    enum OreType {
        COAL(Material.COAL_ORE, Material.COAL),
        IRON(Material.IRON_ORE, Material.IRON_ORE),
        GOLD(Material.GOLD_ORE, Material.GOLD_ORE),
        REDSTONE(Material.REDSTONE_ORE, Material.REDSTONE),
        LAPIS(Material.LAPIS_ORE, Material.LAPIS_LAZULI),
        DIAMOND(Material.DIAMOND_ORE, Material.DIAMOND),
        EMERALD(Material.EMERALD_ORE, Material.EMERALD),
        QUARTZ(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);

        Material type, result;
        OreType(Material type, Material result) {
            this.type = type;
            this.result = result;
        }

        public Material getType() {
            return type;
        }

        public Material getResult() {
            return result;
        }

        public static ItemStack getResultItem(Material type) {
            for(OreType t : OreType.values()) {
                if(t.getType() == type) {
                    if(type == Material.REDSTONE_ORE || type == Material.LAPIS_ORE) {
                        return new ItemStack(t.getResult(), 4);
                    }
                    return new ItemStack(t.getResult());
                }
            }
            return null;
        }

        public static List<Material> getMaterialsList() {
            List<Material> values = new ArrayList<>();
            Arrays.stream(OreType.values()).forEach(m -> values.add(m.getType()));
            return values;
        }
    }

    private final Inventory inv;
    private final Location d;
    private final Player p;

    public DrillRunnable(Player p, Inventory inv, Location d) {
        this.inv = inv;
        this.d = d;
        this.p = p;
    }

    @Override
    public void run() {
        World w = d.getWorld();

        for(int y = 1; y < 6; y++) {
            d.setY(d.getY() - 1);
            if(OreType.getMaterialsList().contains(w.getBlockAt(d).getType())) {
                Material type = w.getBlockAt(d).getType();
                w.getBlockAt(d).setType(Material.AIR);
                Map<Integer, ItemStack> items = inv.addItem(OreType.getResultItem(type));
                if(!items.isEmpty()) {
                    for(ItemStack item : items.values()) {
                        w.dropItem(p.getLocation(), item);
                    }
                }
            }
        }
    }
}
