package com.etho5.mechanix;

import com.etho5.mechanix.commands.MachineMain;
import com.etho5.mechanix.listeners.*;
import com.etho5.mechanix.machines.*;
import com.etho5.mechanix.menus.MenuManager;
import com.etho5.mechanix.persist.Persist;
import com.etho5.mechanix.runnable.*;
import com.etho5.mechanix.saving.MachineSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
//    private final Persist persist = new Persist();
//    private MachineManager manager;

    public void onEnable() {
//        System.out.println("[Mechanix] Loading machine data...");
//        loadManager();
//        System.out.println("[Mechanix] Machine data loaded.");

        System.out.println("[Mechanix] Registering listeners...");
        registerListeners(new BlockBreak(), new BlockPlace(), new PlayerInteract(), new AsyncPlayerChat(), new InventoryClick(), new InventoryClose());
        getCommand("mechanix").setExecutor(new MachineMain());
        getCommand("mechanix").setTabCompleter(new MachineMain());
        System.out.println("[Mechanix] Listeners registered.");

        System.out.println("[Mechanix] Loading recipes...");
//        MachineSerializer.loadRecipes();
        System.out.println("[Mechanix] Recipes loaded.");

        System.out.println("[Mechanix] Loading machines...");
//        MachineSerializer.loadMachines();
        if(MachineSerializer.loadMachines(this)) {
            System.out.println("[Mechanix] Machine data has been loaded.");
        } else {
            System.out.println("[Mechanix] Error with machine loading! Check stack trace above!");
        }

        System.out.println("[Mechanix] Loading menus...");
        MenuManager.generateMenus();
        System.out.println("[Mechanix] Menus loaded.");


//        System.out.println(manager.isCacheEmpty());
//
//        if(!manager.isCacheEmpty()) {
//            for (List<Machine> l : manager.machinesCache.values()) {
//                for (Machine m : l) {
//                    new UpdateMachine(m.getMachineInventory().getInventory(), m).runTaskTimer(this, 0L, 20L);
//                }
//            }
//            Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::saveTask, 0, 1200);
//        } else System.out.println("Cache is empty!");

        System.out.println("[Mechanix] Mechanix has been successfully enabled.");

        new ChargesMove().runTaskTimer(this, 0L,20L);
        new UpdateMachines().runTaskTimer(this, 0L, 60L);
        new GeneratorUpdate().runTaskTimer(this, 10L, 20L);
    }

    public void onDisable() {
        System.out.println("[Mechanix] Saving machine data...");
//        if(manager != null) persist.save(manager);
//        MachineSerializer.saveMachines();
        if(MachineSerializer.saveMachines(this)) {
            System.out.println("[Mechanix] Machine data has been saved.");
        } else {
            System.out.println("[Mechanix] Error with machine saving! Check stack trace above!");
        }

        System.out.println("[Mechanix] Mechanix has been successfully disabled.");
    }

//    private void loadManager() {
//        this.manager = persist.getFile(MachineManager.class).exists() ? persist.load(MachineManager.class) : new MachineManager();
//    }

    private void registerListeners(final Listener... listeners) {
        for(Listener l : listeners) Bukkit.getPluginManager().registerEvents(l, this);
    }

    public static Main getInstance() {
        return instance == null ? instance = Main.getPlugin(Main.class) : instance;
    }

//    public void saveTask() {
//        if(manager != null) {
//            getDataFolder().mkdir();
//            persist.save(manager, persist.getFile("manager_NOTOUCH"));
//            try {
//                if(persist.load(MachineManager.class, persist.getFile("manager_NOTOUCH")) == null) {
//                    persist.getFile("manager_NOTOUCH").delete();
//                    return;
//                }
//            } catch(Exception e) {
//                persist.getFile("manager_NOTOUCH").delete();
//                return;
//            }
//            persist.getFile(manager).delete();
//            persist.getFile("manager_NOTOUCH").renameTo(persist.getFile(manager));
//        }
//    }

//    public MachineManager getManager() {
//        return manager;
//    }

//    public void saveConfigurations() {
//        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
//            if (manager != null) persist.save(manager);
//        });
//    }
}