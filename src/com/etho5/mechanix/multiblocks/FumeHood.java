package com.etho5.mechanix.multiblocks;

import com.etho5.mechanix.abstraction.Multiblock;
import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;

public class FumeHood extends Multiblock {

    @Override
    public String name() {
        return "Fume Hood";
    }

    @Override
    public void handleClick(PlayerInteractEvent e, Dispenser d) {
        e.setCancelled(true);
        buildShapedHandler(e, d, recipes(), Sound.ENTITY_GENERIC_BURN);
    }

    @Override
    public boolean shaped() {
        return true;
    }

    @Override
    public Material clickedBlock() {
        return Material.GLASS;
    }

    @Override
    public Map<ItemStack, ItemStack[]> recipes() {
        Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

        // silica sand
        recipes.put(IngredientList.SILICA_SAND.getItem(), new ItemStack[]{
                null, IngredientList.CARBON.getItem(), null,
                IngredientList.CARBON.getItem(), new ItemStack(Material.SAND), IngredientList.CARBON.getItem(),
                null, IngredientList.CARBON.getItem(), null
        });

        // brine
        recipes.put(IngredientList.BRINE.getItem(), new ItemStack[]{
                null, IngredientList.SALT.getItem(), null,
                IngredientList.SALT.getItem(), new ItemBuilder(Material.POTION).setPotionData(Color.BLUE, PotionType.WATER).build(), IngredientList.SALT.getItem(),
                null, IngredientList.SALT.getItem(), null
        } );

        // chloroalkali process (yields chlorine gas)
        recipes.put(IngredientList.CHLORINE.getItem(), new ItemStack[]{
                null, null, null,
                IngredientList.COPPER_INGOT.getItem(), IngredientList.BRINE.getItem(), new ItemStack(Material.GOLD_INGOT),
                IngredientList.COPPER_WIRE.getItem(), new ItemStack(Material.REDSTONE), IngredientList.COPPER_WIRE.getItem()
        });

        // electrolyzing water (yields oxygen gas)
        recipes.put(IngredientList.OXYGEN.getItem(), new ItemStack[]{
                null, null, null,
                IngredientList.COPPER_INGOT.getItem(), new ItemBuilder(Material.POTION).setPotionData(Color.BLUE, PotionType.WATER).build(), new ItemStack(Material.GOLD_INGOT),
                IngredientList.COPPER_WIRE.getItem(), new ItemStack(Material.REDSTONE), IngredientList.COPPER_WIRE.getItem()
        });

        // zinc chloride
        recipes.put(IngredientList.ZINC_CHLORIDE.getItem(2), new ItemStack[]{
                null, IngredientList.ZINC.getItem(), null,
                IngredientList.ZINC.getItem(), IngredientList.HYDROCHLORIC_ACID.getItem(), IngredientList.ZINC.getItem(),
                null, IngredientList.ZINC.getItem(), null
        });

        // sodium
        recipes.put(IngredientList.SODIUM.getItem(4), new ItemStack[]{
                IngredientList.SODIUM_CARBONATE.getItem(), IngredientList.CARBON.getItem(), IngredientList.SODIUM_CARBONATE.getItem(),
                IngredientList.CARBON.getItem(), null, IngredientList.CARBON.getItem(),
                IngredientList.SODIUM_CARBONATE.getItem(), IngredientList.CARBON.getItem(), IngredientList.SODIUM_CARBONATE.getItem(),
        });

        // sodium hydroxide
        recipes.put(IngredientList.SODIUM_HYDROXIDE.getItem(4), new ItemStack[]{
                null, IngredientList.SODIUM.getItem(), null,
                IngredientList.SODIUM.getItem(), new ItemBuilder(Material.POTION).setPotionData(Color.BLUE, PotionType.WATER).build(), IngredientList.SODIUM.getItem(),
                null, IngredientList.SODIUM.getItem(), null
        });

        // hydrochloric acid
        recipes.put(IngredientList.HYDROCHLORIC_ACID.getItem(2), new ItemStack[]{
                null, IngredientList.HYDROGEN.getItem(), null,
                IngredientList.CHLORINE.getItem(), null, IngredientList.CHLORINE.getItem(),
                null, IngredientList.HYDROGEN.getItem(), null
        });

        // sulfuric acid
        recipes.put(IngredientList.SULFURIC_ACID.getItem(2), new ItemStack[]{
                null, IngredientList.OXYGEN.getItem(), null,
                IngredientList.SULFUR_DIOXIDE.getItem(), new ItemBuilder(Material.POTION).setPotionData(Color.BLUE, PotionType.WATER).build(), IngredientList.SULFUR_DIOXIDE.getItem(),
                null, IngredientList.VANADIUM.getItem(), null
        });

        // salt
        recipes.put(IngredientList.SALT.getItem(4), new ItemStack[]{
                IngredientList.HYDROCHLORIC_ACID.getItem(), IngredientList.SODIUM_HYDROXIDE.getItem(), IngredientList.HYDROCHLORIC_ACID.getItem(),
                IngredientList.SODIUM_HYDROXIDE.getItem(), null, IngredientList.SODIUM_HYDROXIDE.getItem(),
                IngredientList.HYDROCHLORIC_ACID.getItem(), IngredientList.SODIUM_HYDROXIDE.getItem(), IngredientList.HYDROCHLORIC_ACID.getItem()
        });

        return recipes;
    }

    @Override
    public String description() {
        return ChatColor.GREEN + "Facilitates chemical reactions. Isn't chemistry such fun?";
    }
}
