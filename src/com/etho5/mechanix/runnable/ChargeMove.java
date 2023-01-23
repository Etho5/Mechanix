package com.etho5.mechanix.runnable;

import com.etho5.mechanix.machines.Charge;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.machines.MachineType;
import com.etho5.mechanix.machines.cargo.Direction;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ChargeMove extends BukkitRunnable {

    private final Machine machine;
    private boolean cancel = false;

    public ChargeMove(Machine machine) {
        this.machine = machine;
    }


    @Override
    public void run() {
        System.out.println("Moving charge");
        if (machine.getMachineInventory().getType() == MachineType.WIRE) {
            Charge c = machine.getCharge();
            // first, check if a charge is present on the observed machine
            if (c.getMagnitude() != 0) {
                List<Block> near = machine.getAdjacentBlocks();
                List<Machine> nearbyMachines = new ArrayList<>();
                // then check each block nearby the machine for any machines
                for (Block b : near) {
                    Machine nearby = getMachineFromLocationSafe(b.getLocation());
                    // if there is a machine there...
                    if (nearby != null) {
                        MachineType nearbyType = nearby.getMachineInventory().getType();
                        // if that machine can receive a charge...
                        if (nearbyType != MachineType.GENERATOR && nearbyType != MachineType.CARGO) {
                            // if it's a Machine machine, then just dump the charge in and move on
                            if (nearbyType == MachineType.MACHINE) {
                                nearby.setCharge(new Charge(c.getMagnitude() + nearby.getCharge().getMagnitude(), Direction.NONE));
                                c.nullify();
                                machine.setCharge(c);
                                continue;
                            }
                            // if it's a Junction Wire, move the charge to any nearby Wire/Machine in that direction; otherwise fry to notify improper junction placement
                            else if(nearbyType == MachineType.JUNCTION_WIRE) {
                                // if a machine is in that direction...
                                if(getMachineInDirection(nearby.getLocation(), nearby.getMachineInventory().getDirection()) != null) {
                                    Machine inDirection = getMachineInDirection(nearby.getLocation(), nearby.getMachineInventory().getDirection());
                                    MachineType typeInDirection = inDirection.getMachineInventory().getType();
                                    // and that machine can receive a charge...
                                    if (typeInDirection != MachineType.GENERATOR && typeInDirection!= MachineType.CARGO) {
                                        // move the charge there!
                                        if(typeInDirection == MachineType.MACHINE) {
                                            inDirection.setCharge(new Charge(c.getMagnitude() + inDirection.getCharge().getMagnitude(), Direction.NONE));
                                            c.nullify();
                                            machine.setCharge(c);
                                            return;
                                        }
                                        else if(typeInDirection == MachineType.WIRE) {
                                            // if the wire in the direction specified is somehow fried, don't bother moving the charge; otherwise proceed as normal
                                            if(!(inDirection.fried()))
                                                inDirection.setCharge(new Charge(c.getMagnitude() + inDirection.getCharge().getMagnitude(), nearby.getMachineInventory().getDirection()));
                                            c.nullify();
                                            machine.setCharge(c);
                                            return;
                                        }
                                    }
                                }
                            }
                            // the only choice left is a normal Wire, so if the original charge and the nearby charge aren't opposite...
                            if (!(Direction.isOpposite(c.getDirection(), nearby.getCharge().getDirection()))) {
                                // check for any wires next to the NEARBY machine
                                if (nearby.getCharge().getMagnitude() != 0) {
                                    List<Block> near2 = machine.getAdjacentBlocks();
                                    for (Block b2 : near2) {
                                        Machine nearby2 = this.getMachineFromLocationSafe(b2.getLocation());
                                        if (nearby2 != null) {
                                            // making sure to ignore the opposite behavior of the original machine's charge
                                            if(nearby2.getLocation().equals(machine.getLocation())) continue;
                                            // if any other wire has an opposite-pointed charge, denote movement for cancellation
                                            if (nearby2.getMachineInventory().getType() == MachineType.WIRE) {
                                                if (Direction.isOpposite(nearby.getCharge().getDirection(), nearby2.getCharge().getDirection())) {
                                                    this.cancel = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if(!this.cancel) {
                                        nearbyMachines.add(nearby);
                                    }
                                }
                            }
                        }
                    }
                }
                // split charge to be moved equally among all available targets
                int chargeForEach = c.getMagnitude() / nearbyMachines.size();
                for(Machine target : nearbyMachines) {
                    if(!(target.fried()))
                        target.setCharge(new Charge(chargeForEach + target.getCharge().getMagnitude(), getRelativeDirection(machine.getLocation(), target.getLocation())));
                    c.setMagnitude(c.getMagnitude() - chargeForEach);
                }
                c.nullify();
                machine.setCharge(c);
            }
        }
    }

    private Machine getMachineFromLocationSafe (Location l){
        if (MachineManager.isLocationAMachine(l)) return MachineManager.getMachineFromLocation(l);
        else return null;
    }

    private Machine getMachineInDirection(Location l, Direction d) {
        switch(d) {
            case NONE:
            case UNSET:
                return null;
            case POS_X:
                Location l2 = new Location(l.getWorld(), l.getX() + 1, l.getY(), l.getZ());
                if(MachineManager.isLocationAMachine(l2)) return MachineManager.getMachineFromLocation(l2);
                else return null;
            case NEG_X:
                Location l3 =  new Location(l.getWorld(), l.getX() - 1, l.getY(), l.getZ());
                if(MachineManager.isLocationAMachine(l3)) return MachineManager.getMachineFromLocation(l3);
                else return null;
            case POS_Y:
                Location l4 = new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ());
                if(MachineManager.isLocationAMachine(l4)) return MachineManager.getMachineFromLocation(l4);
                else return null;
            case NEG_Y:
                Location l5 = new Location(l.getWorld(), l.getX(), l.getY() - 1, l.getZ());
                if(MachineManager.isLocationAMachine(l5)) return MachineManager.getMachineFromLocation(l5);
                else return null;
            case POS_Z:
                Location l6 = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ() + 1);
                if(MachineManager.isLocationAMachine(l6)) return MachineManager.getMachineFromLocation(l6);
                else return null;
            case NEG_Z:
                Location l7 = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ() - 1);
                if(MachineManager.isLocationAMachine(l7)) return MachineManager.getMachineFromLocation(l7);
                else return null;
        }
        return null;
    }

    private Direction getRelativeDirection(Location origin, Location destination) {
        int xOr = origin.getBlockX();
        int yOr = origin.getBlockY();
        int zOr = origin.getBlockZ();

        int xDe = destination.getBlockX();
        int yDe = destination.getBlockY();
        int zDe = destination.getBlockZ();

        if(Math.abs(xDe - xOr) == 1) {
            if(xDe - xOr > 0) return Direction.POS_X;
            else if (xDe - xOr < 0) return Direction.NEG_X;
        }
        else if(Math.abs(yDe - yOr) == 1) {
            if(yDe - yOr > 0) return Direction.POS_Y;
            else if(yDe - yOr < 0) return Direction.NEG_Y;
        }
        else if(Math.abs(zDe - zOr) == 1) {
            if(zDe - zOr > 0) return Direction.POS_Z;
            else if(zDe - zOr < 0) return Direction.NEG_Z;
        }
        return Direction.NONE;
    }
}
