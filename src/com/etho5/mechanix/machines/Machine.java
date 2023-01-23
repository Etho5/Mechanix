package com.etho5.mechanix.machines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.*;

public class Machine implements Serializable {

    private static final long serialVersionUID = -2060637267482868836L;
    private transient final MachineItem machineItem;
    private final MachineInventory machineInventory;
    private final UUID owner;
    private final Location placedLocation;
    private final List<UUID> allowedPlayers;
    public static final Map<MachineInventory, Machine> invMap = new HashMap<>();
    public static List<Machine> machines = new ArrayList<>();
    public boolean fried = false;
    public Charge charge;

    /**
     * Creates a Machine "object" at the location of a placed block.
     * @param owner the owner of the Machine, i.e. who placed the Machine
     * @param placedLocation the location of the placed block, and thus the Machine
     */
    public Machine(UUID owner, Location placedLocation, MachineItem machineItem, MachineInventory machineInventory, Charge charge) {
        this.owner = owner;
        this.placedLocation = placedLocation;
        this.machineItem = machineItem;
        this.machineInventory = machineInventory;

        this.allowedPlayers = new ArrayList<>();
        this.allowedPlayers.add(owner);

        invMap.put(machineInventory, this);
        machines.add(this);

        this.charge = charge;

//        if(!Main.getInstance().getManager().machinesCache.containsKey(owner)) {
//            Main.getInstance().getManager().machinesCache.put(owner, new ArrayList<>());
//        }
//        if(!Main.getInstance().getManager().machinesCache.get(owner).contains(this)) {
//            Main.getInstance().getManager().machinesCache.get(owner).add(this);
//            Bukkit.broadcastMessage("added machine");
//        }
    }

    public Machine(Machine m) {
        this.owner = m.getOwner();
        this.placedLocation = m.getLocation();
        this.machineItem = m.getMachineItem();
        this.machineInventory = m.getMachineInventory();

        this.allowedPlayers = new ArrayList<>();
        this.allowedPlayers.add(this.owner);
        machines.add(this);
        this.charge = m.getCharge();
    }

    public OfflinePlayer getOwnerAsPlayer() {
        return Bukkit.getPlayer(this.owner);
    }

    public UUID getOwner() { return this.owner; }

    public Location getLocation() {
        return this.placedLocation;
    }

//    public List<UUID> getAllowedPlayers() {
//        return this.allowedPlayers;
//    }

    public void addAllowedPlayers(UUID... players) {
        allowedPlayers.addAll(Arrays.asList(players));
    }

    public MachineInventory getMachineInventory() {
        return machineInventory;
    }

    public boolean isAllowedPlayer(UUID uuid) {
        return allowedPlayers.contains(uuid);
    }

    public void setChargeMagnitude(int energy) {
        if(energy < 0) return;
        this.charge = new Charge(energy, charge.getDirection());
        this.machineInventory.setInventoryEnergy(this.machineInventory.getInventory(), energy);
    }

    public int getChargeMagnitude() {
        return charge.getMagnitude();
    }

    public MachineItem getMachineItem() {
        return machineItem;
    }

    public void deleteMachine() {
        machines.remove(this);
    }

    public List<Block> getAdjacentBlocks() {
        List<Block> blocks = new ArrayList<>();
        Location location = this.getLocation();
        Block b = location.getBlock();
            for (int x = location.getBlockX() - 1; x <= b.getX() + 1; x++) {
                for (int y = location.getBlockY() - 1; y <= b.getY() + 1; y++) {
                    for (int z = location.getBlockZ() - 1; z <= b.getZ() + 1; z++) {
                        Location newLocation = new Location(b.getWorld(), x, y, z);
                        blocks.add(b.getWorld().getBlockAt(newLocation));
                    }
                }
            }
        return blocks;

    }

    /**
     * For use only with Wires, disregard for all other Machine types.
     * @param newStatus true if the Wire should be "fried" (i.e., turned off), false if it should remain functional
     */
    public void setFriedStatus(boolean newStatus) {
        this.fried = newStatus;
    }

    public boolean fried() { return this.fried; }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
}
