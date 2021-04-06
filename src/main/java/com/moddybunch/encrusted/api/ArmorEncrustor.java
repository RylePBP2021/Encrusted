package com.moddybunch.encrusted.api;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.armor.DamagedData;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import java.util.function.Consumer;

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

    //The name of the item, in all lowercase (used in registering the armor)
    private String registerName;

    //Any ItemSettings that should be applied to the armor
    private FabricItemSettings settings;

    //Consumer ran when the entity wearing the armor takes damage
    private Consumer<DamagedData> onDamaged;

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param name The name of the item, in all lowercase. Important for registering armor
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param protectionBonus The increase to protection of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param toughnessBonus The increase to toughness of the Encrustor
     * @param knockbackResistanceBonus The increase to knockback resistance of the Encrustor
     * @param settings The FabricItemSettings applied
     * @param onDamaged Consumer ran when the entity wearing the armor takes damaged, eats a DamagedData parameter
     *
     * @author MitchP404
     */
    public ArmorEncrustor(Item baseItem, String name, int durabilityBonus, int protectionBonus, int enchantabilityBonus, float toughnessBonus, float knockbackResistanceBonus, FabricItemSettings settings, Consumer<DamagedData> onDamaged) {
        this.baseItem = baseItem;
        this.registerName = name;
        this.durabilityBonus = durabilityBonus;
        this.protectionBonus = protectionBonus;
        this.enchantabilityBonus = enchantabilityBonus;
        this.toughnessBonus = toughnessBonus;
        this.knockbackResistanceBonus = knockbackResistanceBonus;
        this.settings = settings;

        if(onDamaged == null) {
            onDamaged = (DamagedData data) -> {};
        } else {
            this.onDamaged = onDamaged;
        }
    }

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param name The name of the item, in all lowercase. Important for registering armor
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param protectionBonus The increase to protection of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param toughnessBonus The increase to toughness of the Encrustor
     * @param knockbackResistanceBonus The increase to knockback resistance of the Encrustor
     * @param settings The FabricItemSettings applied
     *
     * @author MitchP404
     */
    public ArmorEncrustor(Item baseItem, String name, int durabilityBonus, int protectionBonus, int enchantabilityBonus, float toughnessBonus, float knockbackResistanceBonus, FabricItemSettings settings) {
        this(baseItem, name, durabilityBonus, protectionBonus, enchantabilityBonus, toughnessBonus, knockbackResistanceBonus, settings, null);
    }

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param name The name of the item, in all lowercase. Important for registering armor
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param protectionBonus The increase to protection of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param toughnessBonus The increase to toughness of the Encrustor
     * @param knockbackResistanceBonus The increase to knockback resistance of the Encrustor
     *
     * @author MitchP404
     */
    public ArmorEncrustor(Item baseItem, String name, int durabilityBonus, int protectionBonus, int enchantabilityBonus, float toughnessBonus, float knockbackResistanceBonus) {
        this(baseItem, name, durabilityBonus, protectionBonus, enchantabilityBonus, toughnessBonus, knockbackResistanceBonus, new FabricItemSettings());
    }

    /**
     * Creates an ArmorEncrustor with values initialized to 0 or null
     *
     * @author MitchP404
     */
    public ArmorEncrustor() {
        this(null, null, 0, 0, 0, 0, 0, null);
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

    /**
     * Get the register name of the Encrustor (used for registering encrusted items)
     *
     * @return The register name
     * @author MitchP404
     */
    public String getName() {
        return registerName;
    }

    /**
     * Get the settings for the Encrustor
     *
     * @return The FabricItemSettings
     * @author MitchP404
     */
    public FabricItemSettings getSettings() {
        return this.settings;
    }

    //Effects

    public void onDamaged(DamagedData data) {
        Encrusted.EncrustedLog.info("Made it to the armor encrustor! Accepting the data and running the function.");
        this.onDamaged.accept(data);
    }


    /**
     * Allows you to create an armor encrustor, without using a gigantic constructor.
     * Just call each function in a chain, for example, do: <br><br>
     *
     * <strong>new ArmorEncrustor.builder().baseItem(RUBY).registerName("ruby").toughnessBonus(1).build();</strong> <br><br>
     *
     * This creates an armor encrustor with a base item of RUBY, a registry name of "ruby", and a toughness bonus of 1.0. <br>
     * Note that <strong>.build()</strong> is what converts the builder into an ArmorEncrustor. <br>
     * Also note that <strong>.registerName()</strong> and <strong>.baseItem</strong> are both required, and <strong>.build()</strong> will error without them
     */
    public static class Builder{
        //The item being used as an Encrustor
        private Item baseItem;

        //Basic stats that will be applied to an EncrustedArmor
        private int durabilityBonus;
        private int protectionBonus;
        private int enchantabilityBonus;
        private float toughnessBonus;
        private float knockbackResistanceBonus;

        //The name of the item, in all lowercase (used in registering the armor)
        private String registerName;

        //Any ItemSettings that should be applied to the armor
        private FabricItemSettings settings;

        //Consumer ran when the entity wearing the armor takes damage
        private Consumer<DamagedData> onDamaged;

        /**
         * <strong>REQUIRED</strong><br>
         * Sets the base item of the encrustor (ex Ruby)
         * @param item The item
         * @return The builder, with the item added
         */
        public ArmorEncrustor.Builder baseItem(Item item) {
            this.baseItem = item;
            return this;
        }

        /**
         * Sets the durability bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ArmorEncrustor.Builder durabilityBonus(int bonus) {
            this.durabilityBonus = bonus;
            return this;
        }

        /**
         * Sets the protection bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ArmorEncrustor.Builder protectionBonus(int bonus) {
            this.protectionBonus = bonus;
            return this;
        }

        /**
         * Sets the enchantability bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ArmorEncrustor.Builder enchantabilityBonus(int bonus) {
            this.enchantabilityBonus = bonus;
            return this;
        }

        /**
         * Sets the toughness bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ArmorEncrustor.Builder toughnessBonus(int bonus) {
            this.toughnessBonus = bonus;
            return this;
        }

        /**
         * Sets the knockback resistance bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ArmorEncrustor.Builder knockbackResistanceBonus(int bonus) {
            this.knockbackResistanceBonus = bonus;
            return this;
        }

        /**
         * <strong>REQUIRED</strong><br>
         * The name of the item, in all lowercase. This is used when registering the armor
         * @param name The name, in all lowercase (ex "ruby")
         * @return The builder, with the name added
         */
        public ArmorEncrustor.Builder registerName(String name) {
            this.registerName = name;
            return this;
        }

        /**
         * The item settings that will be applied to each armor piece
         * @param settings The settings
         * @return The builder, with the settings added
         */
        public ArmorEncrustor.Builder settings(FabricItemSettings settings) {
            this.settings = settings;
            return this;
        }

        public ArmorEncrustor.Builder onDamaged(Consumer<DamagedData> onDamaged) {
            Encrusted.EncrustedLog.info("Adding onDamaged function to encrustor \"" + this.registerName + "\".");
            this.onDamaged = onDamaged;
            return this;
        }

        /**
         * Converts the builder to an ArmorEncrustor
         * @return The encrustor, <strong>if and only if</strong> the builder has both a baseItem and a registerName.
         * Otherwise, creates a fatal error message and returns an empty encrustor, which will cause a crash
         */
        public ArmorEncrustor build() {
            if(registerName == null || baseItem == null) {
                Encrusted.EncrustedLog.fatal("Cannot convert builder to armor encrustor, needs both a registerName and baseItem");
                return new ArmorEncrustor();
            } else {
                return new ArmorEncrustor(this.baseItem, this.registerName, this.durabilityBonus, this.protectionBonus, this.enchantabilityBonus, this.toughnessBonus, this.knockbackResistanceBonus, this.settings, this.onDamaged);
            }
        }
    }
}
