package com.etho5.mechanix.abstraction;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.multiblocks.Drill;
import com.etho5.mechanix.multiblocks.XPBottler;
import com.etho5.mechanix.runnable.DrillRunnable;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public abstract class Multiblock {

    public static Multiblock getMultiblock(Material clickedBlock) {
//        switch (clickedBlock) {
//            case CRAFTING_TABLE:
//                return new Workstation();
//            case IRON_BARS:
//                return new Crusher();
//            case GLASS:
//                return new FumeHood();
//            case FURNACE:
//                return new Oven();
//            case IRON_TRAPDOOR:
//                return new Sieve();
//            case BREWING_STAND:
//                return new Refinery();
//            case SNOW_BLOCK:
//                return new Refrigerator();
//            case ENCHANTING_TABLE:
//                return new XPBottler();
//            case STONECUTTER:
//                return new LumberMill();
//            default:
//                return null;
//        }
        for(Multiblock block : MachineManager.multiblocks) {
            if(block.clickedBlock() == clickedBlock) return block;
        }
        return null;
    }

    /**
     * Checks to see if a clicked block is part of a Multiblock complex with a dispenser below it.
     * @param clickedBlock the block clicked that, if returned true, is part of a Multiblock
     * @return true if the block below is a dispenser (i.e. the "multiblock" exists)
     */
    public static boolean isMultiblock(Block clickedBlock) {
        Location loc = clickedBlock.getLocation();
        return loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() == Material.DISPENSER;
    }

    /**
     * For use in Multiblocks that gives an item from a group of possible results from each singular ingredient (i.e. most Multiblocks)
     */
    public void buildClickHandler(PlayerInteractEvent event, Dispenser dispenser, Map<ItemStack, ItemStack[]> recipes, Sound sound) {
        event.setCancelled(true);
        Inventory inv = dispenser.getInventory();
        Player p = event.getPlayer();
        for(ItemStack key : recipes.keySet()) {
            for(ItemStack slot : inv.getContents()) {
                if(key.isSimilar(slot)) {
                    slot.setAmount(slot.getAmount() - 1);
                    if(this instanceof XPBottler) {
                        if (event.getPlayer().getLevel() > 0) {
                            event.getPlayer().setLevel(event.getPlayer().getLevel() - 1);
                        }
                    }
                    if(!(Utils.isFull(inv))) {
                        inv.addItem(Utils.getRandomItem(recipes.get(key)));
                    } else {
                        p.getWorld().dropItem(p.getLocation(), Utils.getRandomItem(recipes.get(key)));
                    }
                    p.playSound(p.getLocation(), sound, 0.5F, 3F);
                    return;
                }
            }
        }
    }

    /**
     * For use in Multiblocks that require a specific pattern of items in its Dispenser to craft items (i.e. the Workstation)
     */
    public void buildShapedHandler(PlayerInteractEvent event, Dispenser dispenser, Map<ItemStack, ItemStack[]> recipes, Sound sound) {
        event.setCancelled(true);
        Inventory inv = dispenser.getInventory();
        Player p = event.getPlayer();
        ItemStack[] contents = inv.getContents();

        Map<ItemStack[], ItemStack> newRecipes = recipes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        for(ItemStack[] item : newRecipes.keySet()) {
            if(Arrays.equals(item, contents)) {
                ItemStack result = newRecipes.get(item);
                for (ItemStack content : contents) {
                    if (content == null) continue;
                    content.setAmount(content.getAmount() - 1);
                }
                inv.setContents(contents);
                if (!Utils.isFull(inv)) {
                    inv.setItem(inv.firstEmpty(), result);
                } else {
                    p.getWorld().dropItem(p.getLocation(), result);
                }
                p.playSound(p.getLocation(), sound, 0.5F, 3F);
            }
        }
    }

    public void handleDrill(PlayerInteractEvent event, Dispenser dispenser, Sound sound) {
        event.setCancelled(true);
        int y = dispenser.getY() - 1;
        while(y > 0) {
            new DrillRunnable(event.getPlayer(), dispenser.getInventory(), new Location(dispenser.getWorld(), dispenser.getX(), y, dispenser.getZ()))
                    .runTaskLater(Main.getInstance(), 10);
            dispenser.getWorld().playSound(dispenser.getLocation(), sound, 0.5F, 3F);
            y--;
        }
    }

    public boolean creates(ItemStack item) {
        if(this.getClass() == Drill.class) return false;

        if(this.shaped()) {
            AtomicBoolean b = new AtomicBoolean(false);
            this.recipes().keySet().forEach(k -> {
                if(Utils.isSimilar(k, item)) b.set(true);
            });
            return b.get();
        }

        else {
            for(ItemStack[] out : this.recipes().values()) {
                List<ItemStack> items = Arrays.asList(out);
                if (items.contains(item)) return true;
            }
        }
        return false;
    }

    public static boolean comesFromMultiblock(ItemStack item) {
        for(Multiblock cl : MachineManager.multiblocks) {
            if(cl.creates(item)) return true;
        }
        return false;
    }

    public static Multiblock getMultiblockFromResult(ItemStack item) {
        for(Multiblock cl : MachineManager.multiblocks) {
            if(cl.creates(item)) return cl;
        }
        return null;
    }

    public abstract void handleClick(PlayerInteractEvent e, Dispenser d);

    public abstract boolean shaped();

    public abstract Material clickedBlock();

    public abstract Map<ItemStack, ItemStack[]> recipes();

    public abstract String description();

    public abstract String name();
}
