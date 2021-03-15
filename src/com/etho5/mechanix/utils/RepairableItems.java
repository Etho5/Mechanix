package com.etho5.mechanix.utils;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;

public enum RepairableItems {

    @SerializedName("LeatherHelmet")
    LEATHER_HELMET(Material.LEATHER_HELMET, Material.LEATHER),
    @SerializedName("LeatherChestplate")
    LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, Material.LEATHER),
    @SerializedName("LeatherLeggings")
    LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, Material.LEATHER),
    @SerializedName("LeatherBoots")
    LEATHER_BOOTS(Material.LEATHER_BOOTS, Material.LEATHER),

    @SerializedName("ChainmailHelmet")
    CHAINMAIL_HELMET(Material.CHAINMAIL_HELMET, Material.CHAIN),
    @SerializedName("ChainmailChestplate")
    CHAINMAIL_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE, Material.CHAIN),
    @SerializedName("ChainmailLeggings")
    CHAINMAIL_LEGGINGS(Material.CHAINMAIL_LEGGINGS, Material.CHAIN),
    @SerializedName("ChainmailBoots")
    CHAINMAIL_BOOTS(Material.CHAINMAIL_BOOTS, Material.CHAIN),

    @SerializedName("IronHelmet")
    IRON_HELMET(Material.IRON_HELMET, Material.IRON_INGOT),
    @SerializedName("IronChestplate")
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, Material.IRON_INGOT),
    @SerializedName("IronLeggings")
    IRON_LEGGINGS(Material.IRON_LEGGINGS, Material.IRON_INGOT),
    @SerializedName("IronBoots")
    IRON_BOOTS(Material.IRON_BOOTS, Material.IRON_INGOT),

    @SerializedName("GoldenHelmet")
    GOLDEN_HELMET(Material.GOLDEN_HELMET, Material.GOLD_INGOT),
    @SerializedName("GoldenChestplate")
    GOLDEN_CHESTPLATE(Material.GOLDEN_CHESTPLATE, Material.GOLD_INGOT),
    @SerializedName("GoldenLeggings")
    GOLDEN_LEGGINGS(Material.GOLDEN_LEGGINGS, Material.GOLD_INGOT),
    @SerializedName("GoldenBoots")
    GOLDEN_BOOTS(Material.GOLDEN_BOOTS, Material.GOLD_INGOT),

    @SerializedName("DiamondHelmet")
    DIAMOND_HELMET(Material.DIAMOND_HELMET, Material.DIAMOND),
    @SerializedName("DiamondChestplate")
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, Material.DIAMOND),
    @SerializedName("DiamondLeggings")
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, Material.DIAMOND),
    @SerializedName("DiamondBoots")
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, Material.DIAMOND),

    @SerializedName("WoodenSword")
    WOODEN_SWORD(Material.WOODEN_SWORD, Material.STICK),
    @SerializedName("WoodenPickaxe")
    WOODEN_PICKAXE(Material.WOODEN_PICKAXE, Material.STICK),
    @SerializedName("WoodenAxe")
    WOODEN_AXE(Material.WOODEN_AXE, Material.STICK),
    @SerializedName("WoodenShovel")
    WOODEN_SHOVEL(Material.WOODEN_SHOVEL, Material.STICK),
    @SerializedName("WoodenHoe")
    WOODEN_HOE(Material.WOODEN_HOE, Material.STICK),

    @SerializedName("StoneSword")
    STONE_SWORD(Material.STONE_SWORD, Material.COBBLESTONE),
    @SerializedName("StonePickaxe")
    STONE_PICKAXE(Material.STONE_PICKAXE, Material.COBBLESTONE),
    @SerializedName("StoneAxe")
    STONE_AXE(Material.STONE_AXE, Material.COBBLESTONE),
    @SerializedName("StoneShovel")
    STONE_SHOVEL(Material.STONE_SHOVEL, Material.COBBLESTONE),
    @SerializedName("StoneHoe")
    STONE_HOE(Material.STONE_HOE, Material.COBBLESTONE),

    @SerializedName("IronSword")
    IRON_SWORD(Material.IRON_SWORD, Material.IRON_INGOT),
    @SerializedName("IronPickaxe")
    IRON_PICKAXE(Material.IRON_PICKAXE, Material.IRON_INGOT),
    @SerializedName("IronAxe")
    IRON_AXE(Material.IRON_AXE, Material.IRON_INGOT),
    @SerializedName("IronShovel")
    IRON_SHOVEL(Material.IRON_SHOVEL, Material.IRON_INGOT),
    @SerializedName("IronHoe")
    IRON_HOE(Material.IRON_HOE, Material.IRON_INGOT),

    @SerializedName("GoldenSword")
    GOLDEN_SWORD(Material.GOLDEN_SWORD, Material.GOLD_INGOT),
    @SerializedName("GoldenPickaxe")
    GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE, Material.GOLD_INGOT),
    @SerializedName("GoldenAxe")
    GOLDEN_AXE(Material.GOLDEN_AXE, Material.GOLD_INGOT),
    @SerializedName("GoldenShovel")
    GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL, Material.GOLD_INGOT),
    @SerializedName("GoldenHoe")
    GOLDEN_HOE(Material.GOLDEN_HOE, Material.GOLD_INGOT),

    @SerializedName("DiamondSword")
    DIAMOND_SWORD(Material.DIAMOND_SWORD, Material.DIAMOND),
    @SerializedName("DiamondPickaxe")
    DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE, Material.DIAMOND),
    @SerializedName("DiamondAxe")
    DIAMOND_AXE(Material.DIAMOND_AXE, Material.DIAMOND),
    @SerializedName("DiamondShovel")
    DIAMOND_SHOVEL(Material.DIAMOND_SHOVEL, Material.DIAMOND),
    @SerializedName("DiamondHoe")
    DIAMOND_HOE(Material.DIAMOND_HOE, Material.DIAMOND)
    ;

    public final Material item, repairMaterial;
    RepairableItems(Material item, Material repairMaterial) {
        this.item = item;
        this.repairMaterial = repairMaterial;
    }

    public Material getRepairable() {
        return this.item;
    }

    public Material getRepairMaterial() {
        return repairMaterial;
    }
}
