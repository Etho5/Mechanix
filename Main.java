package com.etho5.mechanix;

import com.etho5.mechanix.commands.MachineMain;
import com.etho5.mechanix.listeners.*;
import com.etho5.mechanix.machines.Machine;
import com.etho5.mechanix.machines.MachineManager;
import com.etho5.mechanix.persist.Persist;
import com.etho5.mechanix.runnable.EnergyCheckTask;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;
    private final Persist persist = new Persist();
    private MachineManager manager;

    public void onEnable() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::saveTask, 0, 1200);

        System.out.println("[Mechanix] Loading machine data...");
        loadManager();
        System.out.println("[Mechanix] Machine data loaded.");

        System.out.println("[Mechanix] Registering listeners...");
        registerListeners(new BlockBreak(), new BlockPlace(), new PlayerInteract(), new AsyncPlayerChat(), new InventoryClick(), new InventoryClose());
        getCommand("mechanix").setExecutor(new MachineMain());
        getCommand("mechanix").setTabCompleter(new MachineMain());
        System.out.println("[Mechanix] All listeners registered.");

        System.out.println("[Mechanix] Mechanix has been successfully enabled.");

        for(List<Machine> l : manager.machinesCache.values()) {
            for(Machine m : l) {
                new EnergyCheckTask(m).runTaskTimer(this, 0L, 20L);
            }
        }
    }

    public void onDisable() {
        System.out.println("[Mechanix] Saving machine data...");
        if(manager != null) persist.save(manager);
        System.out.println("[Mechanix] Machine data has been saved.");

        System.out.println("[Mechanix] Mechanix has been successfully disabled.");
    }

    private void loadManager() {
        manager = persist.getFile(MachineManager.class).exists() ? persist.load(MachineManager.class) : new MachineManager();
    }

    private void registerListeners(final Listener... listeners) {
        for(Listener l : listeners) Bukkit.getPluginManager().registerEvents(l, this);
    }

    public static Main getInstance() {
        return instance == null ? instance = Main.getPlugin(Main.class) : instance;
    }

    public void saveTask() {
        if(manager != null) {
            getDataFolder().mkdir();
            persist.save(manager, persist.getFile("manager_NOTOUCH"));
            try {
                if(persist.load(MachineManager.class, persist.getFile("manager_NOTOUCH")) == null) {
                    persist.getFile("manager_NOTOUCH").delete();
                    return;
                }
            } catch(Exception e) {
                persist.getFile("manager_NOTOUCH").delete();
                return;
            }
            persist.getFile(manager).delete();
            persist.getFile("manager_NOTOUCH").renameTo(persist.getFile(manager));
        }
    }

    public MachineManager getManager() {
        return manager;
    }
}