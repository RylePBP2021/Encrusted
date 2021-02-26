package com.moddybunch.encrusted.api;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

/**
 * An identifier specifically for an encrusted item, such as encrusted:ruby_encrusted_golden_chestplate
 * Will also store the name of the base item, the name of the encrustor, whether it is a tool or armor, etc
 * @author MitchP404
 */
public class EncrustedID extends Identifier {

    /**
     * The name of the encrustor, such as "ruby" or "dev_gem"
     */
    private String encrustorName;

    /**
     * The small name of the base material, such as "gold" and "chain", used for materials
     */
    private String baseMaterialShortName;

    /**
     * The long name of the base material, such as "golden" and "chainmail", used for identifiers
     */
    private String baseMaterialLongName;

    /**
     * The name of the item, such as "sword" and "chestplate"
     */
    private String itemName;

    /**
     * An alternative item name used for materials, such as "chest"
     */
    private String altItemName;

    /**
     * Creates an EncrustedID using an array
     * @param id An array of strings in which id[0] is the namespace and id[1] is the path
     * @author MitchP404
     */
    public EncrustedID(String[] id) {
        super(id);
        seperateNames(this.getPath());
    }

    /**
     * Creates an EncrustedID using a string
     * @param id A string in which the namespace is separated my the path by a colon
     * @author MitchP404
     */
    public EncrustedID(String id) {
        super(id);
        seperateNames(this.getPath());
    }

    /**
     * Creates an EncrustedID using two seperate strings
     * @param namespace The namespace of the id (almost always our mod id)
     * @param path The path of the item, such as ruby_encrusted_golden_chestplate
     * @author MitchP404
     */
    public EncrustedID(String namespace, String path) {
        super(namespace, path);
        seperateNames(this.getPath());
    }

    /**
     * Creates an EncrustedID using member fields
     * @param namespace The namespace, most of the time will be our mod id
     * @param encrustorName The encrustor's name, such as "ruby"
     * @param baseMaterialName The name of the material, such as "diamond"
     * @param itemName The name of the item, such as "chest" or "chestplate"
     * @param materialNames True if you are inputting material names
     *                      (short names for base materials and alternate names for items).
     *                      False if you are inputting path names
     *                      (long names for base materials and normal names for items).
     * @author MitchP404
     */
    public EncrustedID(String namespace, String encrustorName, String baseMaterialName, String itemName, boolean materialNames) {
        super(namespace, materialNames ?
                encrustorName + "_encrusted_" + materialShortToLongName(baseMaterialName) + "_" + itemNameFromAltName(itemName) :
                encrustorName + "_encrusted_" + baseMaterialName + "_" + itemName
        );

        this.encrustorName = encrustorName;
        if(materialNames) {
            this.baseMaterialShortName = baseMaterialName;
            this.baseMaterialLongName = materialShortToLongName(baseMaterialName);
            this.altItemName = itemName;
            this.itemName = itemNameFromAltName(itemName);
        }
        else {
            this.baseMaterialShortName = materialLongToShortName(baseMaterialName);
            this.baseMaterialLongName = baseMaterialName;
            this.altItemName = altNameFromItemName(itemName);
            this.itemName = altNameFromItemName(itemName);
        }
    }

    /**
     * Creates an EncrustedID using an EncrustedArmor, a namespace, and an EquipmentSlot
     * @param namespace The namespace of the ID
     * @param armor The armor to make an ID with
     * @param slot The armor slot for the ID
     * @author MitchP404
     */
    public EncrustedID(String namespace, EncrustedArmor armor, EquipmentSlot slot) {
        this(namespace, armor.getEncrustor().getName(), armor.getBaseMaterial().getName(), slot.getName(), true);
    }

    /**
     * Takes the item name and returns the alternate item name. Does not set altItemName
     * @param oldName The item name
     * @return The alternate name
     * @author MitchP404
     */
    private static String altNameFromItemName(String oldName) {
        switch(oldName) {
            case "boots":
                return "feet";
            case "leggings":
                return "legs";
            case "chestplate":
                return "chest";
            case "helmet":
                return "head";
            default:
                return oldName;
        }
    }

    /**
     * Takes the alternate item name and returns the item name. Does not set itemName
     * @param oldName The alternate item name
     * @return The item name
     * @author MitchP404
     */
    private static String itemNameFromAltName(String oldName) {
        switch(oldName) {
            case "feet":
                return "boots";
            case "legs":
                return "leggings";
            case "chest":
                return "chestplate";
            case "head":
                return "helmet";
            default:
                return oldName;
        }
    }

    /**
     * Takes the long material name and returns the short material name. Does not set baseMaterialShortName
     * @param longName The long item name
     * @return The short item name
     * @author MitchP404
     */
    private static String materialLongToShortName(String longName) {
        switch(longName) {
            case "golden":
                return "gold";
            default:
                return longName;
        }
    }

    /**
     * Takes the short material name and returns the long material name. Does not set baseMaterialLongName
     * @param shortName The short item name
     * @return The long item name
     * @author MitchP404
     */
    private static String materialShortToLongName(String shortName) {
        switch(shortName) {
            case "gold":
                return "golden";
            default:
                return shortName;
        }
    }

    /**
     * Takes in the path and sets the encrustorName, baseMaterialLongName,
     * baseMaterialShortName, itemName, and altItemName fields
     * @param id The path
     * @author MitchP404
     */
    private void seperateNames(String id) {

        //Pull the encrustor out
        encrustorName = id.substring(5, id.indexOf("encrusted") - 1);
        String newId = id.substring(id.indexOf("encrusted") + 10);

        //Pull the material and get its short name
        baseMaterialLongName = newId.substring(0, newId.indexOf("_"));
        baseMaterialShortName = materialLongToShortName(baseMaterialLongName);

        //All that is left is the item name, so grab it
        itemName = newId.substring(id.indexOf("_") + 1);
        altItemName = altNameFromItemName(itemName);
    }

    // Getters
    public String getEncrustorName() {
        return encrustorName;
    }

    public String getBaseMaterialShortName() {
        return baseMaterialShortName;
    }

    public String getBaseMaterialLongName() {
        return baseMaterialLongName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getAltItemName() {
        return altItemName;
    }
}
