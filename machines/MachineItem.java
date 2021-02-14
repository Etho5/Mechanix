package com.etho5.mechanix.machines;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.utils.Utils;
import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public enum MachineItem {

    @SerializedName("CombustionEngine")
    COMBUSTION_ENGINE(MachineType.GENERATOR, 5000, 10, Material.BLAST_FURNACE, ChatColor.DARK_GRAY + "Combustion Engine", "Uses heat to generate a small stream of energy."),
    @SerializedName("SolarPanel")
    SOLAR_PANEL(MachineType.GENERATOR, 5000, 10, Material.DAYLIGHT_DETECTOR, ChatColor.YELLOW + "Solar Panel", "Transfers light energy to electrical energy."),
    @SerializedName("ArcFurnace")
    ARC_FURNACE(MachineType.MACHINE, 10000, 25, Material.CHISELED_QUARTZ_BLOCK, ChatColor.GRAY + "Arc Furnace", "Heats materials faster and more precisely."),
    @SerializedName("AutoAnvil")
    AUTO_ANVIL(MachineType.MACHINE, 10000, 50, Material.IRON_BLOCK, ChatColor.GRAY + "Auto Anvil", "Repairs items without the need for XP."),
    @SerializedName("Wire")
    WIRE(MachineType.WIRE, 50, 50, Material.GREEN_STAINED_GLASS_PANE, ChatColor.DARK_GREEN + "Wire", "Transmits energy at a rate of 50 J/s.")
    ;

    public final ItemStack item;
    public final MachineType invType;
    public final int maxEnergy, energyPerSecond;
    MachineItem(MachineType invType, int maxEnergy, int energyPerSecond, Material type, String displayName, String info) {
        this.invType = invType;
        this.item = new ItemStack(type);
        this.maxEnergy = maxEnergy;
        this.energyPerSecond = energyPerSecond;
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(Collections.singletonList(ChatColor.GREEN + info));
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "items"), PersistentDataType.STRING, this.name());
        }
        this.item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemStack getItem(int amount) {
        this.item.setAmount(amount);
        return this.item;
    }

    public String getDisplayName() {
        return Utils.toSpacedPrint(this.toString().toLowerCase());
    }

    public static MachineItem fromString(String str) {
        for(MachineItem item : MachineItem.values()) {
            if(item.name().equalsIgnoreCase(str)) return item;
        }
        return null;
    }

    public MachineType getType() {
        return this.invType;
    }
}
