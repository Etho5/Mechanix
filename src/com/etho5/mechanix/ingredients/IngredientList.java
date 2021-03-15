package com.etho5.mechanix.ingredients;

import com.etho5.mechanix.utils.ItemBuilder;
import com.etho5.mechanix.utils.Utils;
import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum IngredientList {

    @SerializedName("ShinyDust")
    SHINY_DUST(ChatColor.GRAY, "Something about this seems... valuable.", Material.GUNPOWDER),
    @SerializedName("Carbon")
    CARBON(ChatColor.DARK_GRAY, "A gray lump, with the power of life held inside.", Material.CHARCOAL),
    @SerializedName("Sulfur")
    SULFUR(ChatColor.YELLOW, "Smells like rotten eggs, but it has its uses.", Material.GLOWSTONE_DUST),
    @SerializedName("SilicaSand")
    SILICA_SAND(ChatColor.GRAY, "Carbon-treated sand, ready to be smelted.", Material.WHITE_CONCRETE_POWDER),
    @SerializedName("Silicon")
    SILICON(ChatColor.WHITE, "Carbon's little brother, forged from sand.", Material.QUARTZ),
    @SerializedName("Salt")
    SALT(ChatColor.WHITE, "It's pronounced \"sodium chloride\", actually.", Material.SUGAR),
    @SerializedName("IronDust")
    IRON_DUST(ChatColor.GRAY, "Much less rusty than that red stuff off-world.", Material.GUNPOWDER),
    @SerializedName("Copper")
    COPPER(ChatColor.RED, "The backbone of electrical power.", 536119),
    @SerializedName("CopperIngot")
    COPPER_INGOT(ChatColor.RED, "Copper made ready for standard use.", 536120),
    @SerializedName("Sodium")
    SODIUM(ChatColor.WHITE, "Part of salt, but has other uses as well.", Material.SUGAR),
    @SerializedName("Aluminium")
    ALUMINIUM(ChatColor.GRAY, "A cheaper, less conductive metal.", Material.GUNPOWDER),
    @SerializedName("AluminiumIngot")
    ALUMINIUM_INGOT(ChatColor.DARK_GRAY, "It's aluminium, but in a bar shape.", 536121),
    @SerializedName("Lead")
    LEAD(ChatColor.GRAY, "Don't even think about eating it.", Material.GUNPOWDER),
    @SerializedName("LeadIngot")
    LEAD_INGOT(ChatColor.DARK_GRAY, "Good luck fitting that in your mouth.", 536122),
    @SerializedName("Zinc")
    ZINC(ChatColor.GRAY, "An all-around useful metal.", Material.GUNPOWDER),
    @SerializedName("Vanadium")
    VANADIUM(ChatColor.GRAY, "A niche metal mostly used in making sulfuric acid.", Material.GUNPOWDER),
    @SerializedName("Chromium")
    CHROMIUM(ChatColor.LIGHT_PURPLE, "Despite the name, it actually turns minerals red.", Material.GUNPOWDER),
    @SerializedName("Pyrolusite")
    PYROLUSITE(ChatColor.GRAY, "A manganese ore found near iron deposits.", Material.GUNPOWDER),
    @SerializedName("Pitchblende")
    PITCHBLENDE(ChatColor.GRAY, "Is it just me, or does it look kind of green...", Material.GUNPOWDER),
    @SerializedName("Bauxite")
    BAUXITE(ChatColor.GRAY, "An ore comprised mainly of various aluminium silicates.", Material.GUNPOWDER),
    @SerializedName("Gibbsite")
    GIBBSITE(ChatColor.GRAY, "A mineral made up primarily of aluminium hydroxide.", Material.GUNPOWDER),
    @SerializedName("AluminiumOxide")
    ALUMINIUM_OXIDE(ChatColor.BLUE, "Used in making aluminium alloy, and artificial gemstones...", Material.SUGAR),
    @SerializedName("VanadiumOxide")
    VANADIUM_OXIDE(ChatColor.GOLD, "A crucial catalyst in making sulfuric acid.", Material.GLOWSTONE_DUST),
    @SerializedName("SulfurDioxide")
    SULFUR_DIOXIDE(ChatColor.BLUE, "Oxidized sulfur. Man, does it smell bad...", Material.GLASS_BOTTLE),
    @SerializedName("SodaAsh")
    SODA_ASH(ChatColor.WHITE, "An impure form of NaCO3 made by processing kelp.", Material.GUNPOWDER),
    @SerializedName("SodiumCarbonate")
    SODIUM_CARBONATE(ChatColor.WHITE, "Found from ashes, used in making NaOH.", Material.SUGAR),
    @SerializedName("SulfuricAcid")
    SULFURIC_ACID(ChatColor.WHITE, "Very dangerous, but can be worth the risk.", Material.POTION),
    @SerializedName("LiquidAir")
    LIQUID_AIR(ChatColor.AQUA, "So cold that it's gone from gas to liquid.", Material.POTION),
    @SerializedName("Steel")
    STEEL(ChatColor.GRAY, "A strong alloy of carbon and iron.", Material.GUNPOWDER),
    @SerializedName("SteelIngot")
    STEEL_INGOT(ChatColor.WHITE, "It lasts a lifetime, and then some.", Material.IRON_INGOT),
    @SerializedName("AluminiumAlloy")
    ALUMINIUM_ALLOY(ChatColor.GRAY, "A powder mix of aluminium, aluminium oxide, and zinc.", Material.GUNPOWDER),
    @SerializedName("AluminiumAlloyIngot")
    ALUMINIUM_ALLOY_INGOT(ChatColor.DARK_GRAY, "Melted-down aluminium alloy, strong and durable.", 536121),
    @SerializedName("CopperWire")
    COPPER_WIRE(ChatColor.RED, "A thin wire of copper, for use in... wires.", 536123),
    @SerializedName("Electromagnet")
    ELECTROMAGNET(ChatColor.RED, "Sure, it pulls in metal, but can it spin?", 536124),
    @SerializedName("SiliconWafer")
    SILICON_WAFER(ChatColor.WHITE, "A thin sheet of shiny silicon.", 536125),
    @SerializedName("Brine")
    BRINE(ChatColor.AQUA, "NaCl and H20. Now just add some diodes...", Material.POTION),
    @SerializedName("Hydrogen")
    HYDROGEN(ChatColor.WHITE, "The first element, and an explosive one.", Material.GLASS_BOTTLE),
    @SerializedName("Chlorine")
    CHLORINE(ChatColor.YELLOW, "Dangerously toxic, but quite useful.", Material.GLASS_BOTTLE),
    @SerializedName("Oxygen")
    OXYGEN(ChatColor.AQUA, "Crucial to life, but toxic when inhaled pure.", Material.GLASS_BOTTLE),
    @SerializedName("HydrochloricAcid")
    HYDROCHLORIC_ACID(ChatColor.WHITE, "A powerful acid. Really, don't drink!", Material.POTION),
    @SerializedName("SodiumHydroxide")
    SODIUM_HYDROXIDE(ChatColor.WHITE, "A powerful base. Don't drink it!", Material.POTION),
    @SerializedName("ZincChloride")
    ZINC_CHLORIDE(ChatColor.WHITE, "The electrolyte paste used in batteries.", Material.SUGAR),
    @SerializedName("ManganeseOxide")
    MANGANESE_OXIDE(ChatColor.GRAY, "The oxidizing agent used in batteries.", Material.GUNPOWDER),
    @SerializedName("LeadDioxide")
    LEAD_DIOXIDE(ChatColor.DARK_GRAY, "A positively charged compound used in lead-acid batteries.", Material.GUNPOWDER),
    @SerializedName("Battery")
    BATTERY(ChatColor.GRAY, "Stores energy, in a smaller capacity.", 536126),
    @SerializedName("CarbonBrush")
    CARBON_BRUSH(ChatColor.GRAY, "A crucial component of a motor's commutator.", 536127),
    @SerializedName("Commutator")
    COMMUTATOR(ChatColor.GRAY, "The spinny part of a motor.", 536128),
    @SerializedName("Motor")
    MOTOR(ChatColor.DARK_GRAY, "This can most indeed spin.", 536129),
    @SerializedName("HeatingCoil")
    HEATING_COIL(ChatColor.DARK_RED, "You can get close, but not too close.", 536130),
    @SerializedName("LeadAcidBattery")
    LEAD_ACID_BATTERY(ChatColor.DARK_GRAY, "This one's got voltage to spare, for sure.", 536131),
    @SerializedName("ComputerChip")
    COMPUTER_CHIP(ChatColor.DARK_GREEN, "Can perform complex calculations that people just can't.", 536132),
    @SerializedName("SyntheticRuby")
    SYNTHETIC_RUBY(ChatColor.RED, "A scientific masterpiece, but just not quite like the original.", 536118),
    @SerializedName("SyntheticSapphire")
    SYNTHETIC_SAPPHIRE(ChatColor.BLUE, "A product of man, yet its beauty seems almost alien.", 536133),
    @SerializedName("Uranium")
    URANIUM(ChatColor.GREEN, "Highly radioactive, and thus highly useful for making reactors.", 536134),
    @SerializedName("MachineInfoTerminal")
    MACHINE_INFO_TERMINAL(ChatColor.YELLOW, "Displays all possible outputs for a clicked Machine.", 536135),
    @SerializedName("Laser")
    LASER(ChatColor.DARK_RED, "Emits a high-energy beam of photons.", 536136)
    ;

    private final Material material;
    private final ItemBuilder build;
    private final ChatColor nameColor;
    private final String description;
    private final int customModelData;
    IngredientList(ChatColor nameColor, String description, int customModelData) {
        this.material = Material.HEART_OF_THE_SEA;
        this.build = null;
        this.nameColor = nameColor;
        this.description = description;
        this.customModelData = customModelData;
    }

    IngredientList(ChatColor nameColor, String description, Material material) {
        this.material = material;
        this.build = null;
        this.nameColor = nameColor;
        this.description = description;
        this.customModelData = -1;
    }

    public Material getMaterial() {
        return this.material;
    }

    public ChatColor getNameColor() {
        return this.nameColor;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public ItemStack getItem() {
        return new IngredientItem(this);
    }

    public ItemStack getItem(int amount) {
        if(this.build != null) {
            return this.build.setAmount(amount).build();
        }

        IngredientItem i = new IngredientItem(this);
        i.setAmount(amount);
        return i;
    }

    public String getDisplayName() {
        return Utils.toSpacedPrint(this.toString().toLowerCase());
    }

    public static IngredientList fromString(String str) {
        for(IngredientList item : IngredientList.values()) {
            if(item.toString().equalsIgnoreCase(str)) return item;
        }
        return null;
    }
}
