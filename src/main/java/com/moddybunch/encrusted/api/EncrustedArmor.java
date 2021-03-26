package com.moddybunch.encrusted.api;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Encrusted Armors
 * @author MitchP404
 */
public class EncrustedArmor implements ArmorMaterial {

    //Basic information pertaining to armor materials
    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private String name;
    private int durabilityMultiplier;
    private int[] protectionAmounts;
    private int enchantability;
    private SoundEvent equipSound;
    private float toughness;
    private float knockbackResistance;
    private Ingredient repairIngredientSupplier;

    //The base material for the encrusted armor
    private ArmorMaterial baseMaterial;
    //The 'encrustor' used
    private ArmorEncrustor encrustor;

    /**
     * Creates a new EncrustedArmor material using an already made ArmorMaterial and Encrustor
     *
     * @param baseMaterial The armor material this armor is based on
     * @param encrustor The encrustor used
     * @author MitchP404
     */
    public EncrustedArmor(ArmorMaterial baseMaterial, ArmorEncrustor encrustor) {
        this.name = baseMaterial.getName();
        this.durabilityMultiplier = (baseMaterial.getDurability(EquipmentSlot.FEET)/13) + encrustor.getDurabilityBonus();
        this.protectionAmounts = new int[] {
                baseMaterial.getProtectionAmount(EquipmentSlot.FEET) + encrustor.getProtectionBonus(),
                baseMaterial.getProtectionAmount(EquipmentSlot.LEGS) + encrustor.getProtectionBonus(),
                baseMaterial.getProtectionAmount(EquipmentSlot.CHEST) + encrustor.getProtectionBonus(),
                baseMaterial.getProtectionAmount(EquipmentSlot.HEAD) + encrustor.getProtectionBonus()
        };
        this.enchantability = baseMaterial.getEnchantability() + encrustor.getEnchantabilityBonus();
        this.equipSound = baseMaterial.getEquipSound();
        this.toughness = baseMaterial.getToughness() + encrustor.getToughnessBonus();
        this.knockbackResistance = baseMaterial.getKnockbackResistance() + encrustor.getKnockbackResistanceBonus();
        this.repairIngredientSupplier = baseMaterial.getRepairIngredient();

        this.baseMaterial = baseMaterial;
        this.encrustor = encrustor;
    }

    /**
     * Get the durability of the armor for a specific slot
     *
     * @param slot The slot to return the durability for
     * @return The durability, as an int
     * @author MitchP404
     */
    @Override
    public int getDurability(EquipmentSlot slot) {
        return (BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier) + encrustor.getDurabilityBonus();
    }

    /**
     * Get the protection amount of the armor for a specific slot
     *
     * @param slot The slot to return the protection amount for
     * @return The protection amount, as an int
     * @author MitchP404
     */
    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return protectionAmounts[slot.getEntitySlotId()];
    }

    /**
     * Get the enchantability of the armor material
     *
     * @return Enchantability, as an int
     * @author MitchP404
     */
    @Override
    public int getEnchantability() {
        return enchantability;
    }

    /**
     * Get the sound for equiping the armor
     *
     * @return The SoundEvent for when the armor is equipped
     * @author MitchP404
     */
    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    /**
     * Get the repair material for the armor
     *
     * @return The repair material, as an Ingredient
     * @author MitchP404
     */
    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredientSupplier;
    }

    /**
     * Get the name of the armor material
     *
     * @return The name of the armor material
     * @author MitchP404
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the toughness of the armor material
     *
     * @return The toughness of the material
     * @author MitchP404
     */
    @Override
    public float getToughness() {
        return toughness;
    }

    /**
     * Get the knockback resistance of the armor material, as a float
     *
     * @return The knockback resistance, as a float
     * @author MitchP404
     */
    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    /**
     * Get the encrustor used to create the armor material
     *
     * @return The Encrustor for the material
     * @author MitchP404
     */
    public ArmorEncrustor getEncrustor() {
        return encrustor;
    }

    /**
     * Get the base material used to create the armor material
     *
     * @return The base ArmorMaterial for the new material
     * @author MitchP404
     */
    public ArmorMaterial getBaseMaterial() {
        return baseMaterial;
    }

    /**
     * Get the item settings for the armor
     */
    public FabricItemSettings getSettings() {
        if(encrustor.getSettings() == null) {
            return new FabricItemSettings();
        } else {
            return encrustor.getSettings();
        }
    }
}
