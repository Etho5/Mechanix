package com.etho5.mechanix.machines;

import com.etho5.mechanix.Main;
import com.etho5.mechanix.abstraction.MachineRecipe;
import com.etho5.mechanix.machines.recipes.*;
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
    COMBUSTION_ENGINE(new CombustionEngine(), MachineType.GENERATOR, 5000, 10, Material.BLAST_FURNACE, ChatColor.DARK_GRAY + "Combustion Engine", "Uses heat to generate a small stream of energy.", 0),
    @SerializedName("ArcFurnace")
    ARC_FURNACE(new ArcFurnace(), MachineType.MACHINE, 10000, 25, Material.CHISELED_QUARTZ_BLOCK, ChatColor.WHITE + "Arc Furnace", "Heats materials faster and more precisely.", 20),
    @SerializedName("AutoAnvil")
    AUTO_ANVIL(new AutoAnvil(), MachineType.MACHINE, 10000, 50, Material.IRON_BLOCK, ChatColor.WHITE + "Auto Anvil", "Repairs items without the need for XP.", 200),
    @SerializedName("Wire")
    WIRE(MachineType.WIRE, 50, 50, Material.GREEN_STAINED_GLASS_PANE, ChatColor.DARK_GREEN + "Wire", "Transmits energy at a rate of 50 J/s.", 0),
    @SerializedName("Freezer")
    FREEZER(new Freezer(), MachineType.MACHINE, 10000, 25, Material.BLUE_STAINED_GLASS, ChatColor.BLUE + "Freezer", "Cools substances rapidly with electrical power.", 40),
    @SerializedName("Grinder")
    GRINDER(new Grinder(), MachineType.MACHINE, 10000, 50, Material.FURNACE, ChatColor.DARK_GRAY + "Grinder", "A simple electric stone processing unit.", 40),
    @SerializedName("Incinerator")
    INCINERATOR(new Incinerator(), MachineType.GENERATOR, 5000, 10, Material.SMOKER, ChatColor.DARK_GREEN + "Incinerator", "Burns organic matter to produce energy.", 0),
    @SerializedName("Sifter")
    SIFTER(new Sifter(), MachineType.MACHINE, 10000, 50, Material.CHISELED_STONE_BRICKS, ChatColor.GRAY + "Sifter", "Sieves through dusts and other grainy material.", 40),
    @SerializedName("PrintingPress")
    PRINTING_PRESS(new PrintingPress(), MachineType.MACHINE, 15000, 100, Material.BOOKSHELF, ChatColor.GOLD + "Printing Press", "Enchants books with random level 1 enchantments.", 100),
    @SerializedName("Facilitator")
    FACILITATOR(new Facilitator(), MachineType.MACHINE, 20000, 100, Material.WHITE_STAINED_GLASS, ChatColor.WHITE + "Facilitator", "Can perform simple two-ingredient chemical reactions.", 120),
    @SerializedName("OreProcessor")
    ORE_PROCESSOR(MachineType.MACHINE, 10000, 50, Material.SMOOTH_STONE, ChatColor.DARK_GRAY + "Ore Processor", "Gets out even the most well-hidden ores.", 80),
    @SerializedName("Pipe")
    PIPE(MachineType.CARGO, 0, 0, Material.BLACK_STAINED_GLASS_PANE, ChatColor.GRAY + "Pipe", "Moves items through itself from one place to another.", 0),
    @SerializedName("OutputPipe")
    OUTPUT_PIPE(MachineType.CARGO, 0, 0, Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Output Pipe", "Takes items from a container/machine and transfers them to a pipe.", 0),
    @SerializedName("InputPipe")
    INPUT_PIPE(MachineType.CARGO, 0, 0, Material.LIGHT_BLUE_STAINED_GLASS_PANE, ChatColor.AQUA + "Input Pipe", "Takes items from a pipe and puts them into a connected container/machine.", 0),
    @SerializedName("GatewayPipe")
    GATEWAY_PIPE(MachineType.CARGO, 0, 0, Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW + "Gateway Pipe", "Allows items from a specified whitelist to pass through.", 0),
    @SerializedName("DirectorPipe")
    DIRECTOR_PIPE(MachineType.CARGO, 0, 0, Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GOLD + "Director Pipe", "Sets the direction of travel of items in a cargo network.", 0),
    ;

    public final MachineRecipe recipe;
    public final ItemStack item;
    public final MachineType invType;
    public final int maxEnergy, energyPerSecond, delay;
    MachineItem(MachineRecipe recipe, MachineType invType, int maxEnergy, int energyPerSecond, Material type, String displayName, String info, int delay) {
        this.recipe = recipe;
        this.invType = invType;
        this.item = new ItemStack(type);
        this.maxEnergy = maxEnergy;
        this.energyPerSecond = energyPerSecond;
        this.delay = delay;
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(Collections.singletonList(ChatColor.GREEN + info));
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "items"), PersistentDataType.STRING, this.name());
        }
        this.item.setItemMeta(meta);
    }

    MachineItem(MachineType invType, int maxEnergy, int energyPerSecond, Material type, String displayName, String info, int delay) {
        this.recipe = null;
        this.invType = invType;
        this.item = new ItemStack(type);
        this.maxEnergy = maxEnergy;
        this.energyPerSecond = energyPerSecond;
        this.delay = delay;
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
        ItemStack neu = new ItemStack(this.getItem());
        neu.setAmount(amount);
        return neu;
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

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public int getDelay() {
        return delay;
    }
}
