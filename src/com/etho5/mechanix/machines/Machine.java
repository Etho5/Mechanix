package com.etho5.mechanix.machines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import java.util.*;

public class Machine {

    private transient final MachineItem machineItem;
    private final MachineInventory machineInventory;
    private final UUID owner;
    private final Location placedLocation;
    private final List<UUID> allowedPlayers;
    private int energy, checkedStatus = 0;
    public static final Map<MachineInventory, Machine> invMap = new HashMap<>();
    public static final Map<Location, Machine> machines = new HashMap<>();

    /**
     * Creates a Machine "object" at the location of a placed block.
     * @param owner the owner of the Machine, i.e. who placed the Machine
     * @param placedLocation the location of the placed block, and thus the Machine
     */
    public Machine(UUID owner, Location placedLocation, MachineItem machineItem, MachineInventory machineInventory) {
        this.owner = owner;
        this.placedLocation = placedLocation;
        this.machineItem = machineItem;
        this.machineInventory = machineInventory;

        this.allowedPlayers = new ArrayList<>();
        this.allowedPlayers.add(owner);

        invMap.put(machineInventory, this);
        machines.put(this.placedLocation, this);

//        if(!Main.getInstance().getManager().machinesCache.containsKey(owner)) {
//            Main.getInstance().getManager().machinesCache.put(owner, new ArrayList<>());
//        }
//        if(!Main.getInstance().getManager().machinesCache.get(owner).contains(this)) {
//            Main.getInstance().getManager().machinesCache.get(owner).add(this);
//            Bukkit.broadcastMessage("added machine");
//        }
    }

    public OfflinePlayer getOwnerAsPlayer() {
        return Bukkit.getPlayer(this.owner);
    }

    public Location getLocation() {
        return this.placedLocation;
    }

    public List<UUID> getAllowedPlayers() {
        return this.allowedPlayers;
    }

    public void addAllowedPlayers(UUID... players) {
        allowedPlayers.addAll(Arrays.asList(players));
    }

    public MachineInventory getMachineInventory() {
        return machineInventory;
    }

    public boolean isAllowedPlayer(UUID uuid) {
        return allowedPlayers.contains(uuid);
    }

    public void setEnergy(int energy) {
        if(energy < 0) return;
        this.energy = energy;
        this.machineInventory.setInventoryEnergy(this.machineInventory.getInventory(), energy);
    }

    public int getEnergy() {
        return energy;
    }

    /**
     * Gets the "checked status" of an energy-transferring Machine (i.e. one of MachineType WIRE)
     * @return the status (0 = not checked; 1 = checked; 2 = need to check)
     */
    public int getCheckedStatus() {
        return checkedStatus;
    }

    /**
     * Sets the "checked status" of an energy-transferring Machine (i.e. one of MachineType WIRE)
     * @param newStatus the new status (0 = not checked; 1 = checked; 2 = need to check)
     */
    public void setCheckedStatus(int newStatus) {
        this.checkedStatus = newStatus;
    }

    public MachineItem getMachineItem() {
        return machineItem;
    }

    public void deleteMachine() {
        machines.remove(this.placedLocation, this);
    }

    public List<Machine> getAdjacentMachines() {

        List<Machine> machines = new ArrayList<>();
        Location location = this.getLocation();
        Block b = location.getBlock();

        for (int x = location.getBlockX() - 1; x <= b.getX() + 1; x++) {
            for (int y = location.getBlockY() - 1; y <= b.getY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= b.getZ() + 1; z++) {
                    Location newLocation = new Location(b.getWorld(), x, y, z);
                    if(MachineManager.isLocationAMachine(newLocation)) machines.add(MachineManager.getMachineFromLocation(newLocation));
                }
            }
        }

        return machines;
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
}
