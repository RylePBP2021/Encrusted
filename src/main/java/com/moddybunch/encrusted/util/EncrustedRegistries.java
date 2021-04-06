package com.moddybunch.encrusted.util;

import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.*;
import com.moddybunch.encrusted.api.armor.DamagedData;
import com.moddybunch.encrusted.api.armor.EncrustedArmorItem;
import com.moddybunch.encrusted.api.armor.EncrustedDyeableArmorItem;
import com.moddybunch.encrusted.api.translation.Translation;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import javax.tools.Tool;
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
    public static final Item BANANA = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64).food(new FoodComponent.Builder().hunger(4).snack().saturationModifier(0.6f).meat().build()));

    //For us to use to test things
    public static final Item DEV_GEM = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));

    //Banana encrusted items food values
    public static final FoodComponent BANANA_ENCRUSTOR_FOOD_COMPONENT = new FoodComponent.Builder().hunger(6).saturationModifier(1.6f).build();

    //Encrustors
    public static final ArmorEncrustor RUBY_ARMOR_ENCRUSTOR = new ArmorEncrustor(RUBY, "ruby", 0, 1, 0, 0.5f,0);
    public static final ItemsEncrustor RUBY_ITEM_ENCRUSTOR = new ItemsEncrustor(RUBY, "ruby", 0, 0, 1, 0f);
    public static final ArmorEncrustor BANANA_ARMOR_ENCRUSTOR = new ArmorEncrustor.Builder().baseItem(BANANA).registerName("banana").settings(new FabricItemSettings().food(BANANA_ENCRUSTOR_FOOD_COMPONENT)).build();
    public static final ArmorEncrustor DEV_ARMOR_ENCRUSTOR = new ArmorEncrustor.Builder().baseItem(DEV_GEM).registerName("dev_gem").onDamaged((DamagedData data) -> {
        data.getSauce().getAttacker().kill();
        data.applyStatus(new StatusEffectInstance(StatusEffects.JUMP_BOOST));
    }).build();

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

       //Encrusted Items
        registerAllVanillaItems(RUBY_ITEM_ENCRUSTOR);
      
        //Encrusted Armors
        registerAllVanillaArmors(RUBY_ARMOR_ENCRUSTOR);
        registerAllVanillaArmors(DEV_ARMOR_ENCRUSTOR);
        registerAllVanillaArmors(BANANA_ARMOR_ENCRUSTOR);

        // Functions
        modifyLootTables();
       
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


    /**
     * Register a encrusted tool using the EncrustedItem and the desired tool
     *
     * @param encrustedMaterial The EncrustedItems to make a piece with
     * @param tool The type of tool you are making
     * @param MaterialName name of material
     * @author grover666
     */
    public static void registerEnctrustedItem(ToolMaterials material, EncrustedItems encrustedMaterial, String tool, String MaterialName) {

        if (tool.equals("sword")){
            EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, MaterialName, tool);

            smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

            Registry.register(
                    Registry.ITEM,
                    id,
                    new SwordItem(material, (int)(encrustedMaterial.getAttackDamage()), encrustedMaterial.getHasteBonus(), new Item.Settings().group(ENCRUSTED_GROUP))
            );
       }/*else if (tool.equals("hoe")){
            EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, MaterialName, tool);

            smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

            Registry.register(
                    Registry.ITEM,
                    id,
                    new HoeItem(material, (int)(encrustedMaterial.getAttackDamage()), encrustedMaterial.getHasteBonus(), new Item.Settings().group(ENCRUSTED_GROUP))
            );
        }else if(tool.equals("pickaxe")){
            EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, MaterialName, tool);

            smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

            Registry.register(
                    Registry.ITEM,
                    id,
                    new PickaxeItem(material, (int)(encrustedMaterial.getAttackDamage()), encrustedMaterial.getHasteBonus(), new Item.Settings().group(ENCRUSTED_GROUP))
            );
        }else if(tool.equals("axe")){
            EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, MaterialName, tool);

            smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

            Registry.register(
                    Registry.ITEM,
                    id,
                    new AxeItem(material, (int)(encrustedMaterial.getAttackDamage()), encrustedMaterial.getHasteBonus(), new Item.Settings().group(ENCRUSTED_GROUP))
            );
        }else if(tool.equals("shovel")){
            EncrustedID id = new EncrustedID(Encrusted.MODID, encrustedMaterial, MaterialName, tool);

            smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

            Registry.register(
                    Registry.ITEM,
                    id,
                    new ShovelItem(material, (int)(encrustedMaterial.getAttackDamage()), encrustedMaterial.getHasteBonus(), new Item.Settings().group(ENCRUSTED_GROUP))
            );
        }*/
    }

    /**
     * Quickly registers an entire set of encrusted tools
     *
     * @param encrustedMaterial The EncrustedItems to make a tool set with
     * @param MaterialName name of material
     * @author grover666
     */
    public static void registerEnctrustedItemSet(EncrustedItems encrustedMaterial, String MaterialName, ToolMaterials material) {
        registerEnctrustedItem(material, encrustedMaterial, "sword", MaterialName);
       /* registerEnctrustedItem(material, encrustedMaterial, "hoe", MaterialName);
        registerEnctrustedItem(material, encrustedMaterial, "pickaxe", MaterialName);
        registerEnctrustedItem(material, encrustedMaterial, "axe", MaterialName);
        registerEnctrustedItem(material, encrustedMaterial, "shovel", MaterialName);*/
    }

    /**
     * Quickly registers every single vanilla item using an encrustor
     *
     * @param encrustor The encrustor to use
     * @author grover666
     */
    public static void registerAllVanillaItems(ItemsEncrustor encrustor) {
        //Wood
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("wood"), encrustor), "wood", ToolMaterials.WOOD);
        //Stone
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("stone"), encrustor), "stone", ToolMaterials.STONE);
        //Iron
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("iron"), encrustor), "iron", ToolMaterials.IRON);
        //Gold
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("gold"), encrustor), "gold", ToolMaterials.GOLD);
        //Diamond
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("diamond"), encrustor), "diamond", ToolMaterials.DIAMOND);
        //netherite
        registerEnctrustedItemSet(new EncrustedItems(StringToToolMaterial("netherite"), encrustor), "netherite", ToolMaterials.NETHERITE);
        }

    public static ToolMaterials StringToToolMaterial(String material){
        if(material.equals("wood")){
            return ToolMaterials.WOOD;
        }else if(material.equals("stone")){
            return ToolMaterials.STONE;
        }else if(material.equals("iron")){
            return ToolMaterials.IRON;
        }else if(material.equals("gold")){
            return ToolMaterials.GOLD;
        }else if(material.equals("diamond")){
            return ToolMaterials.DIAMOND;
        }else if(material.equals("netherite")) {
            return ToolMaterials.NETHERITE;
        }else{
            return null;
        }
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
