package com.etho5.mechanix.saving;

import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MachineSerializer implements Serializable {

    private static transient final long serialVersionUID = 5455105591780260713L;
    public static transient String encodedData;
    public final List<List<UUID>> players;
    public final List<Location> locations;
    public final List<MachineItem> items;
    public final List<String> inventoriesBase64;
    public final List<Integer> energies;

    public MachineSerializer(List<List<UUID>> players, List<Location> locations, List<MachineItem> items, List<String> inventoriesBase64, List<Integer> energies) {
        this.players = players;
        this.locations = locations;
        this.items = items;
        this.inventoriesBase64 = inventoriesBase64;
        this.energies = energies;
    }

    public MachineSerializer(MachineSerializer serializer) {
        this.players = serializer.players;
        this.locations = serializer.locations;
        this.items = serializer.items;
        this.inventoriesBase64 = serializer.inventoriesBase64;
        this.energies = serializer.energies;
    }


    public void serialize(String fileName) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(fileName)));
            out.writeObject(this);
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static MachineSerializer deserialize(String fileName) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(fileName)));
            MachineSerializer ser = (MachineSerializer) in.readObject();
            in.close();
            return ser;
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodeContentsToBase64(List<ItemStack> items) {
        if(items.size() == 0) return null;
        else {
            try {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(byteOut);
                bukkitOut.writeInt(items.size());

                for (ItemStack i : items) {
                    bukkitOut.writeObject(i);
                }

                bukkitOut.flush();
                byte[] rawData = byteOut.toByteArray();
                bukkitOut.close();
                encodedData = Base64.getEncoder().encodeToString(rawData);
                return encodedData;

            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static List<ItemStack> decodeContentsFromBase64() {
        List<ItemStack> items = new ArrayList<>();
        String encodedContents = encodedData;
        if(!encodedContents.isEmpty()) {
            byte[] rawData = Base64.getDecoder().decode(encodedContents);

            try {
                ByteArrayInputStream byteIn = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(byteIn);
                int count = bukkitIn.readInt();

                for(int i = 0; i < count; ++i) {
                    items.add((ItemStack) bukkitIn.readObject());
                }

                bukkitIn.close();
                return items;
            }catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        else return null;
    }

    public static void saveMachines() {
        List<List<UUID>> players = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<MachineItem> items = new ArrayList<>();
        List<String> inventoriesBase64 = new ArrayList<>();
        List<Integer> energies = new ArrayList<>();

        if (!Machine.machines.isEmpty()) {
            for (Machine m : Machine.machines) {
                players.add(m.getAllowedPlayers());
                locations.add(m.getLocation());
                items.add(m.getMachineItem());
                energies.add(m.getEnergy());

                List<ItemStack> contents = Arrays.asList(m.getMachineInventory().getInventory().getContents());
                inventoriesBase64.add(encodeContentsToBase64(contents));
            }
        }
        new MachineSerializer(players, locations, items, inventoriesBase64, energies).serialize("machines.data");
        Bukkit.getServer().getLogger().log(Level.INFO, "Machine data saved");
    }

    public static void loadMachines() {
        try {
            MachineSerializer machineInfo = new MachineSerializer(MachineSerializer.deserialize("machines.data"));

            int x;
            for(x = 0; x < machineInfo.locations.size(); x++) {
                Machine mach = new Machine(machineInfo.players.get(x).get(0), machineInfo.locations.get(x), machineInfo.items.get(x), new MachineInventory(machineInfo.items.get(x), decodeContentsFromBase64()));
                mach.setEnergy(machineInfo.energies.get(x));
            }

        } catch(NullPointerException e) {
            e.printStackTrace();
        }

    }
}
