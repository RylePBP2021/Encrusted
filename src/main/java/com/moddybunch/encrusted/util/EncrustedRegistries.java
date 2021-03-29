package com.moddybunch.encrusted.util;

import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.*;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
    public static final ItemsEncrustor RUBY_ITEM_ENCRUSTOR = new ItemsEncrustor(RUBY, "ruby", 0, 0, 1, 0f);

    //Smithing recipes
    public static ArrayList<JsonObject> smithingRecipes = new ArrayList<>();

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

       //Encrusted Items
        registerAllVanillaItems(RUBY_ITEM_ENCRUSTOR);
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

        smithingRecipes.add(JsonGen.createSmithingRecipeJson(new Identifier("minecraft", id.getBaseMaterialLongName()), new Identifier(Encrusted.MODID, id.getEncrustorName()), id));

        Registry.register(
                Registry.ITEM,
                id,
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
}