package com.moddybunch.encrusted.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

/**
 * Stores functions related to generating JSON files, such as table recipes
 *
 * @author MitchP404
 */
public class JsonGen {

    /**
     * Creates a JSON object representing a smithing table recipe. Most of it was taken from the fabric wiki and edited
     *
     * @param baseItem The identifier of the item in the first smithing table slot
     * @param adderItem The identifier of the item in the second smithing table slot
     * @param output The identifier of the output for the recipe
     * @return A JsonObject representing the recipe
     * @author MitchP404
     */
    public static JsonObject createSmithingRecipeJson(Identifier baseItem, Identifier adderItem, Identifier output) {
        //Creating a new json object, where we will store our recipe.
        JsonObject json = new JsonObject();
        //The "type" of the recipe we are creating. In this case, a smithing recipe.
        json.addProperty("type", "minecraft:smithing");

        //We create a new Json Element, and add our crafting pattern to it.
        JsonObject base = new JsonObject();
        JsonObject addition = new JsonObject();
        base.addProperty("item", baseItem.toString());
        addition.addProperty("item", adderItem.toString());
        json.add("base", base);
        json.add("addition", addition);

        //Finally, we define our result object
        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        json.add("result", result);

        return json;
    }

    /**
     * Creates a String representing a json object of an item model
     *
     * @param baseId The sprite id of the base layer
     * @param encrustorId The sprite id of the encrustor
     * @param type Whether the item should be "generated" or "handheld", will return null if neither of these
     * @return The String of the Json object
     * @author MitchP404
     * @author Fabric Wiki
     */
    public static String createItemModelJson(String baseId, String encrustorId, String type) {
        if ("generated".equals(type) || "handheld".equals(type)) {
            //The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.

            // Leather is kinda wonky, so we need to make a special case here
            if(baseId.contains("leather")) {
                return "{\n" +
                        "  \"parent\": \"item/" + type + "\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"minecraft:item/" + baseId + "\",\n" +
                        "    \"layer1\": \"minecraft:item/" + baseId + "_overlay\",\n" +
                        "    \"layer2\": \"" + Encrusted.MODID + ":item/" + encrustorId + "\"\n" +
                        "  }\n" +
                        "}";
            } else {
                return "{\n" +
                        "  \"parent\": \"item/" + type + "\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"minecraft:item/" + baseId + "\",\n" +
                        "    \"layer1\": \"" + Encrusted.MODID + ":item/" + encrustorId + "\"\n" +
                        "  }\n" +
                        "}";
            }
        }
        else {
            //If the type is invalid, return an empty json string.
            return "";
        }
    }
}
