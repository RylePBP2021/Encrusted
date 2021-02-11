package com.moddybunch.encrusted.util;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.ArmorEncrustor;
import com.moddybunch.encrusted.api.EncrustedArmor;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Where we will register our items, blocks, tools, etc, and store some basic information about them.
 * @author MitchP404
 */
public class EncrustedRegistries {
    //Identifiers
    //Item Groups
    public static final Identifier ENCRUSTED_GROUP_ID = new Identifier(Encrusted.MODID, "encrusted_group");

    //Items
    public static final Identifier RUBY_ID = new Identifier(Encrusted.MODID, "ruby");
    public static final Identifier DEV_GEM_ID = new Identifier(Encrusted.MODID, "dev_gem");

    //Build Item Groups
    public static final ItemGroup ENCRUSTED_GROUP = FabricItemGroupBuilder.build(
            ENCRUSTED_GROUP_ID,
            () -> new ItemStack(Registry.ITEM.get(RUBY_ID))
    );

    //Objects to be registered
    //Items
    public static final Item RUBY = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));

    //For us to use to test things
    public static final Item DEV_GEM = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));


    //Encrustors
    public static final ArmorEncrustor RUBY_ARMOR_ENCRUSTOR = new ArmorEncrustor(RUBY, "ruby", 0, 1, 0, 0.5f,0);
    public static final ArmorEncrustor DEV_ARMOR_ENCRUSTOR = new ArmorEncrustor(DEV_GEM, "dev_gem", 0, 0, 0, 0f,0.1f);

    /**
     * Runs in onInitialize, registers the objects in this class
     */
    public static void init() {
        //Items
        Registry.register(Registry.ITEM, RUBY_ID, RUBY);
        Registry.register(Registry.ITEM, DEV_GEM_ID, DEV_GEM);

       //Encrusted Armors
       registerAllVanillaArmors(RUBY_ARMOR_ENCRUSTOR);
       registerAllVanillaArmors(DEV_ARMOR_ENCRUSTOR);
    }

    /**
     * Register a piece of encrusted armor using the EncrustedArmor and the desired slot
     *
     * @param encrustedMaterial The EncrustedArmor to make a piece with
     * @param slot The EquipmentSlot of the armor
     * @author MitchP404
     */
    public static void registerEnctrustedArmor(EncrustedArmor encrustedMaterial, EquipmentSlot slot) {

        String slotName;
        switch(slot.getName()) {
            case "feet":
                slotName = "boots";
                break;
            case "legs":
                slotName = "leggings";
                break;
            case "chest":
                slotName = "chestplate";
                break;
            case "head":
                slotName = "helmet";
                break;
            default:
                slotName = "error_armor";
        }

        String baseName;
        if ("gold".equals(encrustedMaterial.getBaseMaterial().getName())) {
            baseName = "golden";
        } else {
            baseName = encrustedMaterial.getBaseMaterial().getName();
        }

        Registry.register(
                Registry.ITEM,
                new Identifier(Encrusted.MODID, encrustedMaterial.getEncrustor().getName() + "_encrusted_" + baseName + "_" + slotName),
                new ArmorItem(encrustedMaterial, slot, new Item.Settings().group(ENCRUSTED_GROUP))
        );
    }

    /**
     * Quickly registers an entire set of encrusted armor
     *
     * @param encrustedMaterial The EncrustedArmor to make a set with
     * @author MitchP404
     */
    public static void registerEncrustedArmorSet(EncrustedArmor encrustedMaterial) {
        registerEnctrustedArmor(encrustedMaterial, EquipmentSlot.FEET);
        registerEnctrustedArmor(encrustedMaterial, EquipmentSlot.LEGS);
        registerEnctrustedArmor(encrustedMaterial, EquipmentSlot.CHEST);
        registerEnctrustedArmor(encrustedMaterial, EquipmentSlot.HEAD);
    }

    /**
     * Quickly registers every single vanilla armor using an encrustor
     *
     * @param encrustor The encrustor to use
     * @author MitchP404
     */
    public static void registerAllVanillaArmors(ArmorEncrustor encrustor) {
        //Leather
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.LEATHER, encrustor));
        //Iron
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.IRON, encrustor));
        //Chain
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.CHAIN, encrustor));
        //Gold
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.GOLD, encrustor));
        //Diamond
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.DIAMOND, encrustor));
        //Netherite
        registerEncrustedArmorSet(new EncrustedArmor(ArmorMaterials.NETHERITE, encrustor));
    }
}