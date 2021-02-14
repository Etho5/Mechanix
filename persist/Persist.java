package com.etho5.mechanix.persist;

import com.etho5.mechanix.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class Persist {

    private final Gson gson = buildGson().create();

    public static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    public static String getName(Object o) {
        return getName(o.getClass());
    }


    private GsonBuilder buildGson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .registerTypeAdapter(Inventory.class, new InventoryTypeAdapter());

    }

    public File getFile(String name) {
        return new File(Main.getInstance().getDataFolder(), name + ".json");
    }

    public File getFile(Class<?> clazz) {
        return getFile(getName(clazz));
    }

    public File getFile(Object o) {
        return getFile(getName(o));
    }

    public void save(Object instance) {
        save(instance, getFile(instance));
    }

    public void save(Object instance, File file) {
        if (!file.exists()) {
            file.mkdir();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        PersistUtil.writeCatch(file, gson.toJson(instance));
    }

    public <T> T load(Class<T> clazz) {
        return load(clazz, getFile(clazz));
    }

    public <T> T load(Class<T> clazz, File file) {
        String content = PersistUtil.readCatch(file);
        if (content == null) return null;
        try {
            return gson.fromJson(content, clazz);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Failed to parse " + file.toString() + ": " + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
        return null;
    }
}
