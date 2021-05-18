package com.moddybunch.encrusted.api;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.armor.DamagedData;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import java.util.function.Consumer;

/**
 * The class for item encrustors. Created using an Item and used to create an EncrustedItems
 *
 * @author grover666
 */
public class ItemsEncrustor {

    //The item being used as an Encrustor
    private Item baseItem;

    //Basic stats that will be applied to an EncrustedArmor
    private int durabilityBonus;
    private int enchantabilityBonus;
    private int sharpnessBonus;
    private float attackSpeedBonus;
    private float hasteBonus;

    //Any ItemSettings that should be applied to the EncrustedItems
    private FabricItemSettings settings;

    //Consumer ran when the item is used to attack an entity
    private Consumer<Entity> onAttack;

    //The name of the item, in all lowercase (used in registering the item)
    private String registerName;

    /**
     * Create an empty encrustor for an item, in which values are initialized to 0 or null
     *
     * @author MitchP404
     */
    public ItemsEncrustor() {
        this(null, null, 0, 0, 0, 0, 0);
    }

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param name The name of the item, in all lowercase. Used when registering.
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param sharpnessBonus The increase to the attack damage of the Encrustor
     * @param attackSpeedBonus The increase to attack speed of the Encrustor
     * @param hasteBonus The increase to the mining speed of the Encrustor
     *
     * @author groverr666
     */
    public ItemsEncrustor(Item baseItem, String name, int durabilityBonus, int enchantabilityBonus, int sharpnessBonus, float attackSpeedBonus, float hasteBonus) {
        this(baseItem, name, durabilityBonus, enchantabilityBonus, sharpnessBonus, attackSpeedBonus, hasteBonus, new FabricItemSettings(), null);
    }

    /**
     * Create a new Encrustor for an Item
     *
     * @param baseItem The item that you are creating an Encrustor for
     * @param registerName The name of the item, in all lowercase. Used when registering.
     * @param durabilityBonus The increase to durability of the Encrustor
     * @param enchantabilityBonus The increase to enchantability of the Encrustor
     * @param sharpnessBonus The increase to the attack damage of the Encrustor
     * @param attackSpeedBonus The increase to attack speed of the Encrustor
     * @param hasteBonus The increase to the mining speed of the Encrustor
     * @param settings The FabricItemSettings that will be applied to the item
     * @param onAttack A consumer that is ran when the item is used to attack an entity
     *
     * @author MitchP404
     */
    public ItemsEncrustor(Item baseItem, String registerName, int durabilityBonus, int sharpnessBonus, int enchantabilityBonus, float attackSpeedBonus, float hasteBonus, FabricItemSettings settings, Consumer<Entity> onAttack) {
        this.baseItem = baseItem;
        this.registerName = registerName;
        this.durabilityBonus = durabilityBonus;
        this.enchantabilityBonus = enchantabilityBonus;
        this.sharpnessBonus = sharpnessBonus;
        this.attackSpeedBonus = attackSpeedBonus;
        this.hasteBonus = hasteBonus;
        this.settings = settings;
        this.onAttack = onAttack;
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

    public float getAttackSpeedBonus() {
        return attackSpeedBonus;
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

    public FabricItemSettings getSettings() {
        return this.settings;
    }

    public void onAttack(Entity target) {
        this.onAttack.accept(target);
    }

    /**
     * Allows you to create an item encrustor, without using a gigantic constructor.
     * Just call each function in a chain, for example, do: <br><br>
     *
     * <strong>new ItemsEncrustor.builder().baseItem(RUBY).registerName("ruby").sharpnessBonus(1).build();</strong> <br><br>
     *
     * This creates an item encrustor with a base item of RUBY, a registry name of "ruby", and a sharpness bonus of 1.0. <br>
     * Note that <strong>.build()</strong> is what converts the builder into an ItemsEncrustor. <br>
     * Also note that <strong>.registerName()</strong> and <strong>.baseItem</strong> are both required, and <strong>.build()</strong> will error without them
     */
    public static class Builder{
        //The item being used as an Encrustor
        private Item baseItem;

        //Basic stats that will be applied to an EncrustedItems
        private int durabilityBonus;
        private int enchantabilityBonus;
        private int sharpnessBonus;
        private float attackSpeedBonus;
        private float hasteBonus;

        //The name of the item, in all lowercase (used in registering the EncrustedItems)
        private String registerName;

        //Any ItemSettings that should be applied to the EncrustedItems
        private FabricItemSettings settings;

        //Consumer ran when the item is used to attack an entity
        private Consumer<Entity> onAttack;

        /**
         * <strong>REQUIRED</strong><br>
         * Sets the base item of the encrustor (ex Ruby)
         * @param item The item
         * @return The builder, with the item added
         */
        public ItemsEncrustor.Builder baseItem(Item item) {
            this.baseItem = item;
            return this;
        }

        /**
         * Sets the durability bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ItemsEncrustor.Builder durabilityBonus(int bonus) {
            this.durabilityBonus = bonus;
            return this;
        }

        /**
         * Sets the sharpness bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ItemsEncrustor.Builder sharpnessBonus(int bonus) {
            this.sharpnessBonus = bonus;
            return this;
        }

        /**
         * Sets the enchantability bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ItemsEncrustor.Builder enchantabilityBonus(int bonus) {
            this.enchantabilityBonus = bonus;
            return this;
        }

        /**
         * Sets the attack speed bonus of the encrustor
         * @param bonus The bonus, as a float
         * @return The builder, with the bonus added
         */
        public ItemsEncrustor.Builder attackSpeedBonus(float bonus) {
            this.attackSpeedBonus = bonus;
            return this;
        }

        /**
         * Sets the haste bonus of the encrustor
         * @param bonus The bonus, as an int
         * @return The builder, with the bonus added
         */
        public ItemsEncrustor.Builder hasteBonus(float bonus) {
            this.hasteBonus = bonus;
            return this;
        }

        /**
         * <strong>REQUIRED</strong><br>
         * The name of the item, in all lowercase. This is used when registering the EncrustedItems
         * @param name The name, in all lowercase (ex "ruby")
         * @return The builder, with the name added
         */
        public ItemsEncrustor.Builder registerName(String name) {
            this.registerName = name;
            return this;
        }

        /**
         * The item settings that will be applied to each item
         * @param settings The settings
         * @return The builder, with the settings added
         */
        public ItemsEncrustor.Builder settings(FabricItemSettings settings) {
            this.settings = settings;
            return this;
        }

        public ItemsEncrustor.Builder onAttack(Consumer<Entity> onAttack) {
            Encrusted.EncrustedLog.info("Adding onAttack function to encrustor \"" + this.registerName + "\".");
            this.onAttack = onAttack;
            return this;
        }

        /**
         * Converts the builder to an ItemsEncrustor
         * @return The encrustor, <strong>if and only if</strong> the builder has both a baseItem and a registerName.
         * Otherwise, creates a fatal error message and returns an empty encrustor, which will likely cause a crash
         */
        public ItemsEncrustor build() {
            if(registerName == null || baseItem == null) {
                Encrusted.EncrustedLog.fatal("Cannot convert builder to armor encrustor, needs both a registerName and baseItem");
                return new ItemsEncrustor();
            } else {
                return new ItemsEncrustor(this.baseItem, this.registerName, this.durabilityBonus, this.sharpnessBonus, this.enchantabilityBonus, this.attackSpeedBonus, this.hasteBonus, this.settings, this.onAttack);
            }
        }
    }
}
