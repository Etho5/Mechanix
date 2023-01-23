package com.etho5.mechanix.saving;

import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineInventory;
import com.etho5.mechanix.machines.MachineItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MachineSerializer implements Serializable {

    /**
     * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
     *
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     */
    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = toBase64(playerInventory);
        String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[] { content, armor };
    }

    /**
     *
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * <p />
     *
     * Based off of {@link #toBase64(Inventory)}.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to serialize an inventory to Base64 string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     *
     * A method to serialize an {@link Machine} array to Base64 String.
     *
     * <p />
     *
     * Based off of {@link #itemStackArrayToBase64(ItemStack[])}.
     *
     * @param machines to turn into a Base64 String.
     * @return Base64 string of the items.
     */
    public static String machineListToBase64(List<Machine> machines) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Serialize that list
            dataOutput.writeObject(machines);

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save machines.", e);
        }
    }

    /**
     *
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     */
    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * <p />
     *
     * Base off of {@link #fromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            ItemStack[] items = (ItemStack[]) dataInput.readObject();

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type. Perhaps your data is not an ItemStack[]?", e);
        }
    }

    /**
     * Gets a List of Machines from Base64 string.
     *
     * <p />
     *
     * Base off of {@link #itemStackArrayFromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return Machine List created from the Base64 string.
     */
    public static List<Machine> machineListFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            //todo ...and then read those objects back to here
            //todo the question is, i cant save List, so how do i save (then read) a bunch of objects w/o that?
            List<Machine> machinesRaw = (List<Machine>) dataInput.readObject();
            dataInput.close();

            List<Machine> machines = new ArrayList<>();

            // get rid of any non-existent machines
            for(Machine m : machinesRaw) {
                if(m != null) machines.add(m);
            }

            return machines;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Attempts to save the master Machine List to a File.
     * @return true if the list was saved successfully, false otherwise
     */
    public static boolean saveMachines() {
        try {
            Path p = Paths.get("machines.data");
            FileOutputStream fOut = new FileOutputStream(p.toFile());
            BufferedOutputStream bOut = new BufferedOutputStream(fOut);
            String data = machineListToBase64(Machine.machines);

            bOut.write(data.getBytes(StandardCharsets.UTF_8));
            bOut.close();
            fOut.close();
            return true;
        } catch(IOException | InvalidPathException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Attempts to load the master Machine List from a File.
     * @return true if the list was loaded successfully, false otherwise
     */
    public static boolean loadMachines() {
        try {
            Path p = Paths.get("machines.data");
            byte[] bytes = Files.readAllBytes(p);
            String s = new String(bytes);

            Machine.machines = machineListFromBase64(s);
            return true;
        } catch(IOException | InvalidPathException e) {
            e.printStackTrace();
            return false;
        }

    }

//    private static transient final long serialVersionUID = 5455105591780260713L;
//    public static transient String encodedData;
////    public final List<List<UUID>> players;
////    public final List<Location> locations;
////    public final List<MachineItem> items;
////    public final List<String> inventoriesBase64;
////    public final List<Integer> energies;
//    public final List<Machine> machine;
//
////    public MachineSerializer(List<List<UUID>> players, List<Location> locations, List<MachineItem> items, List<String> inventoriesBase64, List<Integer> energies) {
////        this.players = players;
////        this.locations = locations;
////        this.items = items;
////        this.inventoriesBase64 = inventoriesBase64;
////        this.energies = energies;
////    }
//
//    public MachineSerializer(List<Machine> machine) {
//        this.machine = machine;
//    }
//
//
//    public void serialize(String fileName) {
//        try {
//            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(fileName)));
//            out.writeObject(this);
//            out.close();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static MachineSerializer deserialize(String fileName) {
//        try {
//            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(fileName)));
//            MachineSerializer ser = (MachineSerializer) in.readObject();
//            in.close();
//            return ser;
//        } catch(ClassNotFoundException | IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String encodeContentsToBase64(List<ItemStack> items) {
//        if(items.size() == 0) return null;
//        else {
//            try {
//                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//                BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(byteOut);
//                bukkitOut.writeInt(items.size());
//
//                for (ItemStack i : items) {
//                    bukkitOut.writeObject(i);
//                }
//
//                bukkitOut.flush();
//                byte[] rawData = byteOut.toByteArray();
//                bukkitOut.close();
//                encodedData = Base64.getEncoder().encodeToString(rawData);
//                return encodedData;
//
//            } catch(IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//    }
//
//    public static List<ItemStack> decodeContentsFromBase64() {
//        List<ItemStack> items = new ArrayList<>();
//        String encodedContents = encodedData;
//        if(encodedContents != null && !encodedContents.isEmpty()) {
//            byte[] rawData = Base64.getDecoder().decode(encodedContents);
//
//            try {
//                ByteArrayInputStream byteIn = new ByteArrayInputStream(rawData);
//                BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(byteIn);
//                int count = bukkitIn.readInt();
//
//                for(int i = 0; i < count; ++i) {
//                    items.add((ItemStack) bukkitIn.readObject());
//                }
//
//                bukkitIn.close();
//                return items;
//            }catch(IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                return new ArrayList<>();
//            }
//        }
//        else return new ArrayList<>();
//    }
//
//    public static void saveMachines() {
////        List<List<UUID>> players = new ArrayList<>();
////        List<Location> locations = new ArrayList<>();
////        List<MachineItem> items = new ArrayList<>();
////        List<String> inventoriesBase64 = new ArrayList<>();
////        List<Integer> energies = new ArrayList<>();
//
//        if (!Machine.machines.isEmpty()) {
////                players.add(m.getAllowedPlayers());
////                locations.add(m.getLocation());
////                items.add(m.getMachineItem());
////                energies.add(m.getEnergy());
////
////                List<ItemStack> contents = Arrays.asList(m.getMachineInventory().getInventory().getContents());
////                inventoriesBase64.add(encodeContentsToBase64(contents));
////                new MachineSerializer(players, locations, items, inventoriesBase64, energies).serialize("machines.data");
//                new MachineSerializer(Machine.machines).serialize("machines.data");
//        }
//        Machine.machines.clear();
//        Bukkit.getServer().getLogger().log(Level.INFO, "Machine data saved");
//    }
//
//    public static void loadMachines() {
//        MachineSerializer machineInfo = MachineSerializer.deserialize("machines.data");
//        if(machineInfo != null && machineInfo.machine != null) {
//           for(Machine m : machineInfo.machine) {
//                if(m == null) continue;
//                Machine newMach = new Machine(m);
//                Bukkit.getServer().getLogger().log(Level.INFO, "Loaded machine: " + newMach);
//            }
//
////            for (int x = 0; x < machineInfo.locations.size(); x++) {
////                if (!decodeContentsFromBase64().isEmpty()) {
////                    Machine mach = new Machine(machineInfo.players.get(x).get(0), machineInfo.locations.get(x), machineInfo.items.get(x), new MachineInventory(machineInfo.items.get(x), decodeContentsFromBase64()));
////                    mach.setEnergy(machineInfo.energies.get(x));
////                    System.out.println("Machine generated from file: " + mach);
////                }
////            }
//            Bukkit.getServer().getLogger().log(Level.INFO, "Machine data loaded.");
//        }
//    }
//
//    public static void loadRecipes() {
//        if(MachineRecipe.recipes != null) {
//            for (MachineItem i : MachineItem.values()) {
//                System.out.println("Recipe for " + i.getDisplayName() + ": " + MachineRecipe.getMachineRecipe(i));
//            }
//        }
//    }
}
