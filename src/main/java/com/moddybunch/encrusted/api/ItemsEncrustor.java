package com.moddybunch.encrusted.api;

import net.minecraft.item.Item;

/**
 * The class for armor encrustors. Created using an Item and used to create an EncrustedArmor
 *
 * @author MitchP404
 */
public class ItemsEncrustor {

    //The item being used as an Encrustor
    private Item baseItem;

    //Basic stats that will be applied to an EncrustedArmor
    private int durabilityBonus;
    private int enchantabilityBonus;
    private int sharpnessBonus;
    private float hasteBonus;

    //The name of the item, in all lowercase (used in registering the item)
    private String registerName;

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param sharpnessBonus the increase to the attack damage of the Encrustor
     * @param hasteBonus the increase to the mining speed of the Encrustor
     *
     * @author groverr666
     */
    public ItemsEncrustor(Item baseItem, String name, int durabilityBonus, int enchantabilityBonus, int sharpnessBonus, float hasteBonus) {
        this.baseItem = baseItem;
        this.registerName = name;
        this.durabilityBonus = durabilityBonus;
        this.enchantabilityBonus = enchantabilityBonus;
        this.sharpnessBonus = sharpnessBonus;
        this.hasteBonus = hasteBonus;
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
     * Get the enchantability bonus for the Encrustor
     *
     * @return The enchantability bonus for the Encrustor, as an int
     * @author MitchP404
     */
    public int getEnchantabilityBonus() {
        return enchantabilityBonus;
    }

    /**
     * Get the sharpness bonus for the Encrustor
     *
     * @return The sharpness bonus for the Encrustor, as a int
     * @author groverr666
     */
    public float getSharpnessBonus() {
        return sharpnessBonus;
    }

    /**
     * Get the haste bonus for the Encrustor
     *
     * @return The haste bonus bonus for the Encrustor, as a float
     * @author groverr666
     */
    public float getHasteBonus() {
        return hasteBonus;
    }

    /**
     * Get the register name of the Encrustor (used for registering encrusted items)
     *
     * @return The register name
     * @author MitchP404
     */
    public String getName() {
        return registerName;
    }
}
