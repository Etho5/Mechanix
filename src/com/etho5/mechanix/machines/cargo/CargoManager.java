package com.etho5.mechanix.machines.cargo;

import com.etho5.mechanix.machines.*;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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
            Inventory sourceInv = source.getMachineInventory().getInventory();
            if(!ItemPackage.hasPackage(pipe)) {
                ItemPackage pack = new ItemPackage(pipe, Utils.getLastItem(sourceInv));
                System.out.println("Output transferred from machine: " + pack.toString());
            }
            if(Utils.getLastItemIndex(sourceInv) != -1) {
                sourceInv.setItem(Utils.getLastItemIndex(sourceInv), null);
            }
        }
    }

    public static void transferToOutput(Container source, Machine pipe) {
        if(pipe.getMachineItem() != MachineItem.OUTPUT_PIPE) Bukkit.getLogger().log(Level.WARNING, "Not an output pipe!");
        else {
            Inventory sourceInv = source.getInventory();
            if(!ItemPackage.hasPackage(pipe)) {
                ItemPackage pack = new ItemPackage(pipe, Utils.getLastItem(sourceInv));
                System.out.println("Output transferred from container: " + pack.toString());
            }
            if(Utils.getLastItemIndex(sourceInv) != -1) {
                source.getInventory().setItem(Utils.getLastItemIndex(sourceInv), null);
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
                } else if (b instanceof Container) {
                    CargoManager.transferToOutput((Container) b, mach);
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

                    else if(b instanceof Container) {
                        Container to = (Container) b;

                        Inventory inv = to.getInventory();

                        if(ItemPackage.hasPackage(from) && inv.firstEmpty() != -1) {
                            inv.addItem(ItemPackage.getPackageFromMachine(from).delete().getItem());
                        }
                    }
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
                    if(b instanceof Container) {
                        Container c = (Container) b;
                        Inventory inv = c.getInventory();

                        if(ItemPackage.hasPackage(gateway) && inv.firstEmpty() != -1) {
                            inv.addItem(ItemPackage.getPackageFromMachine(gateway).delete().getItem());
                        }
                    }
                }
            if(ItemPackage.hasPackage(gateway)) ItemPackage.getPackageFromMachine(gateway).delete();
            }
        }
    }
}
