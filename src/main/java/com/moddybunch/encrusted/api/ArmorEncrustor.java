package com.moddybunch.encrusted.api;

import net.minecraft.item.Item;

/**
 * The class for armor encrustors. Created using an Item and used to create an EncrustedArmor
 *
 * @author MitchP404
 */
public class ArmorEncrustor {

    //The item being used as an Encrustor
    private Item baseItem;

    //Basic stats that will be applied to an EncrustedArmor
    private int durabilityBonus;
    private int protectionBonus;
    private int enchantabilityBonus;
    private float toughnessBonus;
    private float knockbackResistanceBonus;

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param protectionBonus The increase to protection of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param toughnessBonus The increase to toughness of the Encrustor
     * @param knockbackResistanceBonus The increase to knockback resistance of the Encrustor
     *
     * @author MitchP404
     */
    public ArmorEncrustor(Item baseItem, int durabilityBonus, int protectionBonus, int enchantabilityBonus, int toughnessBonus, int knockbackResistanceBonus) {
        this.baseItem = baseItem;
        this.durabilityBonus = durabilityBonus;
        this.protectionBonus = protectionBonus;
        this.enchantabilityBonus = enchantabilityBonus;
        this.toughnessBonus = toughnessBonus;
        this.knockbackResistanceBonus = knockbackResistanceBonus;
    }

    /**
     * Get the base item used as the Encrustor
     *
     * @return The base item for the Encrustor, as an Item
     * @author MitchP404
     */
    public Item getBaseItem() {
        return baseItem;
    }

    /**
     * Get the durability bonus for the Encrustor
     *
     * @return The durability bonus for the Encrustor, as an int
     * @author MitchP404
     */
    public int getDurabilityBonus() {
        return durabilityBonus;
    }

    /**
     * Get the protection bonus for the Encrustor
     *
     * @return The protection bonus for the Encrustor, as an int
     * @author MitchP404
     */
    public int getProtectionBonus() {
        return protectionBonus;
    }

    /**
     * Get the enchantability bonus for the Encrustor
     *
     * @return The enchantability bonus for the Encrustor, as an int
     * @author MitchP404
     */
    public int getEnchantabilityBonus() {
        return enchantabilityBonus;
    }

    /**
     * Get the toughness bonus for the Encrustor
     *
     * @return The toughness bonus for the Encrustor, as a float
     * @author MitchP404
     */
    public float getToughnessBonus() {
        return toughnessBonus;
    }

    /**
     * Get the knockback resistance bonus for the Encrustor
     *
     * @return The knockback resistance bonus for the Encrustor, as a float
     * @author MitchP404
     */
    public float getKnockbackResistanceBonus() {
        return knockbackResistanceBonus;
    }
}
