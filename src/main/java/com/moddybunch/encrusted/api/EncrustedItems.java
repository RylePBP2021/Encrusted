package com.moddybunch.encrusted.api;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

/**
 * Encrusted Itmes
 * @author grover666
 */
public class EncrustedItems implements ToolMaterial {

    //Basic information pertaining to item materials

    private int Durability;
    private int enchantability;
    private Ingredient repairIngredientSupplier;
    private float sharpness;
    private float hasteBonus;
    private int miningLevel;

    //The base material for the encrusted item
    private ToolMaterial baseMaterial;
    //The 'encrustor' used
    private ItemsEncrustor encrustor;

    /**
     * Creates a new EncrustedArmor material using an already made ToolMaterial and Encrustor
     *
     * @param baseMaterial The item material this armor is based on
     * @param encrustor The encrustor used
     * @author grover666
     */
    public EncrustedItems(ToolMaterial baseMaterial, ItemsEncrustor encrustor) {
        this.Durability = (baseMaterial.getDurability() + encrustor.getDurabilityBonus());
        this.enchantability = baseMaterial.getEnchantability() + encrustor.getEnchantabilityBonus();
        this.repairIngredientSupplier = baseMaterial.getRepairIngredient();
        this.sharpness = baseMaterial.getAttackDamage() + encrustor.getSharpnessBonus();
        this.hasteBonus = baseMaterial.getMiningSpeedMultiplier() + encrustor.getHasteBonus();
        this.baseMaterial = baseMaterial;
        this.encrustor = encrustor;
        this.miningLevel = baseMaterial.getMiningLevel();

    }

    /**
     * Get the durability of the item
     *
     * @return The durability, as an int
     * @author grover666
     */
    @Override
    public int getDurability() { return (Durability); }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    public float getHasteBonus() {
        return hasteBonus;
    }

    @Override
    public float getAttackDamage() {
        return sharpness;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    /**
     * Get the enchantability of the item material
     *
     * @return Enchantability, as an int
     * @author grover666
     */
    @Override
    public int getEnchantability() { return enchantability; }

    /**
     * Get the repair material for the item
     *
     * @return The repair material, as an Ingredient
     * @author grover666
     */
    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredientSupplier;
    }

    /**
     * Get the encrustor used to create the item material
     *
     * @return The Encrustor for the material
     * @author grover666
     */
    public ItemsEncrustor getEncrustor() {
        return encrustor;
    }

    /**
     * Get the base material used to create the Imte material
     *
     * @return The base ItmesMaterial for the new material
     * @author grover666
     */
    public ToolMaterial getBaseMaterial() {
        return baseMaterial;
    }
}
