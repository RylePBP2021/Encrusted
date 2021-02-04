package com.moddybunch.encrusted.util;

import com.moddybunch.encrusted.Encrusted;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

    //Build Item Groups
    public static final ItemGroup ENCRUSTED_GROUP = FabricItemGroupBuilder.build(
            ENCRUSTED_GROUP_ID,
            () -> new ItemStack(Registry.ITEM.get(RUBY_ID))
    );

    //Objects to be registered
    //Items
    public static final Item RUBY = new Item(new FabricItemSettings().group(ENCRUSTED_GROUP).maxCount(64));

    /**
     * Runs in onInitialize, registers the objects in this class
     */
    public static void init() {
        //Items
        Registry.register(Registry.ITEM, RUBY_ID, RUBY);
    }

}