package com.etho5.mechanix.machines;

import com.etho5.mechanix.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class Machine {

    private transient final MachineItem machineItem;
    private final MachineInventory machineInventory;
    private final UUID owner;
    private final Location placedLocation;
    private final Set<UUID> allowedPlayers;
    private int energy = 0;
    private boolean hasBeenChecked = false;
    public static final Map<MachineInventory, Machine> invMap = new HashMap<>();

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

        this.allowedPlayers = new HashSet<>();
        this.allowedPlayers.add(owner);

        invMap.put(machineInventory, this);

        if(!Main.getInstance().getManager().machinesCache.containsKey(owner)) {
            Main.getInstance().getManager().machinesCache.put(owner, new ArrayList<>());
        }
        if(!Main.getInstance().getManager().machinesCache.get(owner).contains(this)) {
            Main.getInstance().getManager().machinesCache.get(owner).add(this);
            Bukkit.broadcastMessage("added machine");
        }
    }

    public UUID getOwner() {
        return this.owner;
    }

    public OfflinePlayer getOwnerAsPlayer() {
        return Bukkit.getPlayer(this.owner);
    }

    public Location getLocation() {
        return this.placedLocation;
    }

    public Set<UUID> getAllowedPlayers() {
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
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean hasBeenChecked() {
        return hasBeenChecked;
    }

    public void setHasBeenChecked(boolean hasBeenChecked) {
        this.hasBeenChecked = hasBeenChecked;
    }

    public MachineItem getMachineItem() {
        return machineItem;
    }

    public void deleteMachine(Player player) {
        Main.getInstance().getManager().machinesCache.get(player.getUniqueId()).remove(this);
    }

    public List<Machine> getAdjacentMachines(int radius) {

        MachineManager m = Main.getInstance().getManager();
        List<Machine> machines = new ArrayList<>();
        Location location = this.getLocation();
        Block b = location.getBlock();

        for (int x = location.getBlockX() - radius; x <= b.getX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= b.getY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= b.getZ() + radius; z++) {
                    Location newLocation = new Location(b.getWorld(), x, y, z);
                    if(m.isLocationAMachine(newLocation)) machines.add(m.getMachineFromLocation(newLocation));
                    Bukkit.broadcastMessage("Block: " + newLocation.getBlock().getType().toString());
                }
            }
        }

        return machines;
    }
}
