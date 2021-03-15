package com.moddybunch.encrusted.util;

import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.ArmorEncrustor;
import com.moddybunch.encrusted.api.EncrustedArmor;
import com.moddybunch.encrusted.api.EncrustedID;
import com.moddybunch.encrusted.api.JsonGen;
import com.moddybunch.encrusted.api.translation.Translation;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
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

    //Smithing recipes
    public static ArrayList<JsonObject> smithingRecipes = new ArrayList<>();

    // Lang
    public static JLang langEnUs = JLang.lang();

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


        // Functions
        modifyLootTables();

       RuntimeResourcePack rrpEnUs = RuntimeResourcePack.create(Encrusted.MODID + ":en_us");
       rrpEnUs.addLang(RuntimeResourcePack.id("en_us"), langEnUs);
       RRPCallback.EVENT.register(a -> a.add(rrpEnUs));

    }

    /**
     * Register a piece of encrusted armor using the EncrustedArmor and the desired slot
     *
     * @param encrustedMaterial The EncrustedArmor to make a piece with
     * @param slot The EquipmentSlot of the armor
     * @author MitchP404
     */
    public static void registerEnctrustedArmor(EncrustedArmor encrustedMaterial, EquipmentSlot slot) {

        EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, slot);

        smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getFullItemName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

        Registry.register(
                Registry.ITEM,
                id,
                new ArmorItem(encrustedMaterial, slot, new Item.Settings().group(ENCRUSTED_GROUP))
        );

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






    /** Where we update the loot tables of chests so that rubies are inside of them
     *  @author loganwhaley
     */




        // Identifiers for the Loot Tables
        private static final Identifier Abandoned_Mineshaft_Loot_Table_ID = new Identifier ("minecraft", "chests/abandoned_mineshaft");
        private static final Identifier Bastion_Loot_Table_ID = new Identifier("minecraft", "chests/bastion_treasure");
        private static final Identifier Buried_Treasure_Loot_Table_ID = new Identifier("minecraft", "chests/buried_treasure");
        private static final Identifier Desert_Pyramid_Loot_Table_ID = new Identifier("minecraft", "chests/desert_pyramid");
        private static final Identifier Jungle_Temple_Loot_Table_ID = new Identifier("minecraft", "chests/jungle_temple");
        private static final Identifier Nether_Fortress_Loot_Table_ID = new Identifier("minecraft", "chests/nether_bridge");
        private static final Identifier Pillager_Outpost_Loot_Table_ID = new Identifier("minecraft", "chests/pillager_outpost");
        private static final Identifier Ruined_Portal_Loot_Table_ID = new Identifier("minecraft", "chests/ruined_portal");
        private static final Identifier Shipwreck_Loot_Table_ID = new Identifier("minecraft", "chests/shipwreck_treasure");
        private static final Identifier Woodland_Mansion_Loot_Table_ID = new Identifier("minecraft", "chests/woodland_mansion");

        // Creates the new chest loot tables
        private static void modifyLootTables() {
            LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {

                // Checks for the each loot table as the game is being loaded
                if (Abandoned_Mineshaft_Loot_Table_ID.equals(id) ||
                        Bastion_Loot_Table_ID.equals(id) ||
                        Buried_Treasure_Loot_Table_ID.equals(id) ||
                        Desert_Pyramid_Loot_Table_ID.equals(id) ||
                        Jungle_Temple_Loot_Table_ID.equals(id) ||
                        Nether_Fortress_Loot_Table_ID.equals(id) ||
                        Pillager_Outpost_Loot_Table_ID.equals(id) ||
                        Ruined_Portal_Loot_Table_ID.equals(id) ||
                        Shipwreck_Loot_Table_ID.equals(id) ||
                        Woodland_Mansion_Loot_Table_ID.equals(id)){
                    // Adds the ruby(ies) to the loot table
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootTableRange.create(1))
                            .withEntry(ItemEntry.builder(RUBY).build());
                    supplier.withPool(poolBuilder.build());
                }
            });
        }

    }
