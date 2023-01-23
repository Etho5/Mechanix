package com.etho5.mechanix.machines.cargo;

import com.etho5.mechanix.machines.*;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class CargoManager {

    public static boolean isOKToTransfer(Machine from, Machine to, ItemPackage pack) {
        MachineInventory machInv = from.getMachineInventory();
        MachineInventory mInv = to.getMachineInventory();
        try {
            Direction.getBlockFrom(from.getLocation().getWorld().getBlockAt(from.getLocation()), pack.getDirection(), 1).getLocation();
        } catch(NullPointerException e) {
            return false;
        }

        Location transferLoc = Direction.getBlockFrom(from.getLocation().getWorld().getBlockAt(from.getLocation()), pack.getDirection(), 1).getLocation();

        return ItemPackage.hasPackage(from)
                && !ItemPackage.hasPackage(to)
                && MachineManager.isLocationAMachine(transferLoc)
                && MachineManager.getMachineFromLocation(transferLoc).equals(to)
                && machInv.getNetwork() == mInv.getNetwork();
    }

    public static void transferToOutput(Machine source, Machine pipe) {
        if(pipe.getMachineItem() != MachineItem.OUTPUT_PIPE) Bukkit.getLogger().log(Level.WARNING, "Not an output pipe!");
        else {
            List<ItemStack> sourceInv = new ArrayList<ItemStack>(){{
                    source.getMachineInventory().getType().getClickableSlotList().forEach(n -> this.add(source.getMachineInventory().getInventory().getItem(n)));
                }};

            if(!ItemPackage.hasPackage(pipe)) {
                ItemPackage pack = new ItemPackage(pipe, Utils.getLastItem(sourceInv));
                System.out.println("Output transferred from machine: " + pack.toString());
                if(Utils.getLastItemIndex(sourceInv) != -1) {
                    source.getMachineInventory().getInventory().setItem(Utils.getLastItemIndex(sourceInv), null);
                }
            }
        }
    }

    public static void transferToOutput(Container source, Machine pipe) {
        if(pipe.getMachineItem() != MachineItem.OUTPUT_PIPE) Bukkit.getLogger().log(Level.WARNING, "Not an output pipe!");
        else {
            Inventory sourceInv = source.getInventory();
            List<ItemStack> items = Arrays.asList(sourceInv.getContents());
            if(!ItemPackage.hasPackage(pipe)) {
                ItemPackage pack = new ItemPackage(pipe, Utils.getLastItem(items));
                System.out.println("Output transferred from container: " + pack.toString());
                if (Utils.getLastItemIndex(items) != -1) {
                    source.getInventory().setItem(Utils.getLastItemIndex(items), null);
                }
            }
        }
    }

    public static void checkOutput(Machine mach, List<Block> near) {
        if (near != null) {

            for (Block b : near) {

                if (MachineManager.isLocationAMachine(b.getLocation())) {
                    Machine m = MachineManager.getMachineFromLocation(b.getLocation());
                    if (m.getMachineItem().getType() == MachineType.MACHINE) {
                        CargoManager.transferToOutput(m, mach);
                    }
                } else if (b.getType() == Material.CHEST) {
                    Container c = (Container) b.getState();
                    System.out.println("Chest found");
                    CargoManager.transferToOutput(c, mach);
                }
                else if(b.getType() == Material.DISPENSER) {
                    Dispenser d = (Dispenser) b.getState();
                    System.out.println("Dispenser found");
                    CargoManager.transferToOutput(d, mach);
                }
            }
        }
    }

    public static void checkInput(Machine from, List<Block> near) {
        if(from.getMachineItem() == MachineItem.INPUT_PIPE) {
            if (near != null) {

                for (Block b : near) {
                    if(MachineManager.isLocationAMachine(b.getLocation())) {
                        Machine machine = MachineManager.getMachineFromLocation(b.getLocation());

                        if (machine.getMachineItem().getType() == MachineType.CARGO || machine.getMachineItem().getType() == MachineType.WIRE)
                            continue;

                        Inventory inv = machine.getMachineInventory().getInventory();
                        int[] inputSlots = machine.getMachineItem().getType().getInput();

                        if (ItemPackage.getPackageFromMachine(from) != null) {
                            if (isOKToTransfer(from, machine, ItemPackage.getPackageFromMachine(from))) {

                                if (inv.getItem(inputSlots[0]) == null) {
                                    inv.setItem(inputSlots[0], ItemPackage.getPackageFromMachine(from).delete().getItem());
                                } else if (inv.getItem(inputSlots[1]) == null) {
                                    inv.setItem(inputSlots[1], ItemPackage.getPackageFromMachine(from).delete().getItem());
                                }
                            }
                        }
                    }

                    else containerCheck(from, b);
                }

            }
        }
    }

    public static void checkGateway(Machine gateway, ItemPackage passingThrough) {
        if(gateway.getMachineItem() == MachineItem.GATEWAY_PIPE) {
            int[] values = gateway.getMachineItem().getType().getOutputOrWhitelist();
            List<ItemStack> whitelist = new ArrayList<>();
            for(int i : values) {
                whitelist.add(gateway.getMachineInventory().getInventory().getItem(i));
            }
            if(whitelist.isEmpty() || whitelist.contains(passingThrough.getItem())) {
                passingThrough.delete().setPipe(gateway);
            }
            else {
                for(Block b : gateway.getAdjacentBlocks()) {
                    containerCheck(gateway, b);
                }
            if(ItemPackage.hasPackage(gateway)) ItemPackage.getPackageFromMachine(gateway).delete();
            }
        }
    }

    private static void containerCheck(Machine machine, Block b) {
        if(b instanceof Container) {
            Container c = (Container) b.getState();
            Inventory inv = c.getInventory();

            if(ItemPackage.hasPackage(machine) && inv.firstEmpty() != -1) {
                inv.addItem(ItemPackage.getPackageFromMachine(machine).delete().getItem());
            }
        }
    }
}
