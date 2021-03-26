package com.moddybunch.encrusted.util;

import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.*;
import com.moddybunch.encrusted.api.armor.EncrustedArmorItem;
import com.moddybunch.encrusted.api.armor.EncrustedDyeableArmorItem;
import com.moddybunch.encrusted.api.translation.Translation;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

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
    public static final Identifier BANANA_ID = new Identifier(Encrusted.MODID, "banana");
    public static final Identifier DEV_GEM_ID = new Identifier(Encrusted.MODID, "dev_gem");

    //Dyeable Encrusted Armors
    public static ArrayList<DyeableArmorItem> dyeableArmors = new ArrayList<>();

    //Build Item Groups
    public static final ItemGroup ENCRUSTED_GROUP = FabricItemGroupBuilder.build(
            ENCRUSTED_GROUP_ID,
            () -> new ItemStack(Registry.ITEM.get(RUBY_ID))
    );

    //Objects to be registered
    //Items
    public static final Item RUBY = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));
    public static final Item BANANA = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64).food(new FoodComponent.Builder().hunger(4).snack().saturationModifier(0.6f).build()));

    //For us to use to test things
    public static final Item DEV_GEM = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));

    //Banana encrusted items food values
    public static final FoodComponent BANANA_ENCRUSTOR_FOOD_COMPONENT = new FoodComponent.Builder().hunger(6).saturationModifier(1.6f).build();

    //Encrustors
    public static final ArmorEncrustor RUBY_ARMOR_ENCRUSTOR = new ArmorEncrustor(RUBY, "ruby", 0, 1, 0, 0.5f,0);
    public static final ArmorEncrustor BANANA_ARMOR_ENCRUSTOR = new ArmorEncrustor.Builder().baseItem(BANANA).registerName("banana").settings(new FabricItemSettings().food(BANANA_ENCRUSTOR_FOOD_COMPONENT)).build();
    public static final ArmorEncrustor DEV_ARMOR_ENCRUSTOR = new ArmorEncrustor(DEV_GEM, "dev_gem", 0, 0, 0, 0f,0.1f);

    //Smithing recipes
    public static ArrayList<JsonObject> smithingRecipes = new ArrayList<>();

    // Lang
    public static JLang langEnUs = JLang.lang();

    //Loot Table IDs
    private static final Identifier ABANDONED_MINESHAFT_ID = new Identifier("minecraft", "chests/abandoned_mineshaft");
    private static final Identifier STRONGHOLD_CORRIDOR_ID = new Identifier("minecraft", "chests/stronghold_corridor");
    private static final Identifier STRONGHOLD_CROSSING_ID = new Identifier("minecraft", "chests/stronghold_crossing");
    private static final Identifier STRONGHOLD_LIBRARY_ID = new Identifier("minecraft", "chests/stronghold_library");
    private static final Identifier END_CITY_TREASURE_ID = new Identifier("minecraft", "chests/end_city_treasure");

    /**
     * Runs in onInitialize, registers the objects in this class
     */
    public static void init() {
        //Items
        Registry.register(Registry.ITEM, RUBY_ID, RUBY);
        Registry.register(Registry.ITEM, BANANA_ID, BANANA);
        Registry.register(Registry.ITEM, DEV_GEM_ID, DEV_GEM);

        //Encrusted Armors
        registerAllVanillaArmors(RUBY_ARMOR_ENCRUSTOR);
        registerAllVanillaArmors(DEV_ARMOR_ENCRUSTOR);
        registerAllVanillaArmors(BANANA_ARMOR_ENCRUSTOR);

        // Lang
        RuntimeResourcePack rrpEnUs = RuntimeResourcePack.create(Encrusted.MODID + ":en_us");
        rrpEnUs.addLang(RuntimeResourcePack.id("en_us"), langEnUs);
        RRPCallback.EVENT.register(a -> a.add(rrpEnUs));
    }

    /**
     * Register a piece of encrusted armor using the EncrustedArmor and the desired slot
     *
     * @param encrustedMaterial The EncrustedArmor to make a piece with
     * @param slot The EquipmentSlot of the armor
     */
    public static void registerEnctrustedArmor(EncrustedArmor encrustedMaterial, EquipmentSlot slot) {
        EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, slot);

        smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getFullItemName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

        if(id.getBaseMaterialLongName().equals("leather")) {
            EncrustedDyeableArmorItem dyeableArmorItem = new EncrustedDyeableArmorItem(encrustedMaterial, slot, encrustedMaterial.getSettings().group(ENCRUSTED_GROUP));
            dyeableArmors.add(dyeableArmorItem);
            Registry.register(
                    Registry.ITEM,
                    id,
                    dyeableArmorItem
            );
        } else {
            Registry.register(
                    Registry.ITEM,
                    id,
                    new EncrustedArmorItem(encrustedMaterial, slot, encrustedMaterial.getSettings().group(ENCRUSTED_GROUP))
            );
        }

        langEnUs.item(id, Translation.ENUS.translate(id.getEncrustorName(), id.getFullItemName()));
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