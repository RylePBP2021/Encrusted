package com.moddybunch.encrusted.api.translation;

import com.moddybunch.encrusted.Encrusted;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

/**
 * A class meant to store translations of encrusted objects, such as Ruby Encrusted Chestplate.
 * Each translation stores three things.
 * Encrustors is an array of Word objects in which the key is a String serving as an identifier such as "ruby" and the value is a translated String such as "Ruby"
 * Others is an array of Strings that stores other needed words, such as " encrusted ". SPACES ARE NECESSARY!
 * Positions is an array of ints that stores the locations of what is needed.
 * In this array, "1" is the location of the encrustor, "2" is the location of the encrusted item, and "3" is for other words, in order.
 *
 * @author MitchP404
 */
public enum Translation {

    ENUS(new Word[]{
            new Word("ruby", "Ruby"),
            new Word("dev_gem", "Developmentite"),
            new Word("banana", "Banana")
        },
        new String[]{
            " Encrusted "
        },
        new int[]{1, 3, 2}
    );

    //Encrustors
    private final Word[] encrustors;

    //Other words
    private final String[] others;

    //Positions
    private final int[] positions;

    /**
     * Creates a new translation
     *
     * @param encrustors Encrustor array, key is the identifying String and value is the translated String (ex ruby, Ruby)
     * @param others Other words, in order and with spaces
     * @param positions The positions of the words in the final string. "1" for the encrustor, "2" for the encrusted item, "3" for other words
     *
     * @author MitchP404
     */
    private Translation(Word[] encrustors, String[] others, int[] positions) {
        this.encrustors = encrustors;
        this.others = others;
        this.positions = positions;
    }

    /**
     * Generates the needed translation, using the encrustor and the encrusted item
     * @param encrustorKey The encrustor's key in the array, such as "ruby"
     * @param itemId The encrusted item's identifier, used to create a TranslatableText for the item (ex use "golden_leggings")
     * @return The translation for the item
     * @author MitchP404
     */
    public String translate(String encrustorKey, String itemId) {
        String baseItem = Registry.ITEM.get(new Identifier("minecraft", itemId)).getName().getString();
        String out = "";
        int otherIndex = 0;
        for(int val : positions) {
            switch(val) {
                case 1:
                    out += getEncrustorValFromKey(encrustorKey);
                    break;
                case 2:
                    out += baseItem;
                    break;
                case 3:
                    out += others[otherIndex];
                    otherIndex++;
                    break;
                default:
                    Encrusted.EncrustedLog.error("Unrecognized position value " + val);
                    return "ERROR";
            }
        }

        Encrusted.EncrustedLog.info("Creating translation for base item \"" + baseItem + "\" using encrustor \"" + getEncrustorValFromKey(encrustorKey) + "\"");

        return out;
    }

    private String getEncrustorValFromKey(String key) {
        for(Word word : encrustors) {
            if(word.getKey().equals(key)) {
                return word.getValue();
            }
        }
        Encrusted.EncrustedLog.error("Translation key does not have an associated value: " + key);
        return key;
    }

    /*private static final JsonParser parser = new JsonParser();
    private static ArrayList<JsonObject> translations = new ArrayList<JsonObject>();

    public static void registerTranslationResourceListener() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(Encrusted.MODID, "translation_resource");
            }

            @Override
            public void apply(ResourceManager manager) {
                // Clear caches here
                translations = new ArrayList<JsonObject>();
                for(Identifier id : manager.findResources("encrusted/translation", path -> path.endsWith(".json"))) {
                    try(InputStream stream = manager.getResource(id).getInputStream()) {
                        translations.add((JsonObject) parser.parse(new InputStreamReader(stream)));
                    } catch(Exception e) {
                        Encrusted.EncrustedLog.error("Error occurred while loading translation resource json " + id.toString(), e);
                    }
                }
                for (JsonObject t: translations) {
                    createLangFile(t);
                }
            }
        });
    }

    private static void createLangFile(JsonObject translation) {
        try {
            String path = new File(System.getProperty("user.dir")).getParent();
            path = path.substring(0, path.indexOf("java\\com\\moddybunch\\encrusted")) + "resources\\assets\\encrusted\\lang";
            File file = new File(path);
            if(file.delete()) {
                Encrusted.EncrustedLog.info("File deleted, creating new file: " + file.getName());
            } else {
                Encrusted.EncrustedLog.info("File does not exist, creating file: " + file.getName());
            }
            if(file.createNewFile()) {
                Encrusted.EncrustedLog.info("File created: " + file.getName());
            } else {
                Encrusted.EncrustedLog.error("File could not be created: " + file.getName());
            }
            FileWriter writer = new FileWriter(path);
            writer.write(createLangContent(translation));
            Encrusted.EncrustedLog.info("Wrote to file: " + file.getName());
        } catch (IOException e) {
            Encrusted.EncrustedLog.error("Could not create translated file due to an IOException");
            e.printStackTrace();
        }
    }

    private static String createLangContent(JsonObject translation) {
        String out = "{\n";

        Set<Map.Entry<String, JsonElement>> grammarTranslations = translation.getAsJsonObject("grammar").entrySet();
        Set<Map.Entry<String, JsonElement>> textTranslations = translation.getAsJsonObject("text").entrySet();
        Set<Map.Entry<String, JsonElement>> otherTranslations = translation.getAsJsonObject("other").entrySet();

        Iterator<Map.Entry<String, JsonElement>> otherTransIterator = otherTranslations.iterator();
        while(otherTransIterator.hasNext()) {
            Map.Entry<String,JsonElement> trans = otherTransIterator.next();
            out += "\"" + trans.getKey() + "\" : \"" + trans.getValue() + "\",\n";
        }



        Iterator<Map.Entry<String, JsonElement>> grammarTransIterator = grammarTranslations.iterator();
        ArrayList<int> = new
        while(otherTransIterator.hasNext()) {
            Map.Entry<String,JsonElement> trans = otherTransIterator.next();
            out += "\"" + trans.getKey() + "\" : \"" + trans.getValue() + "\",\n";
        }
    }

    private static int getMax(Set<Map.Entry<String, JsonElement>> set) {
        int max = -100;
        for (Map.Entry<String, JsonElement> entry : set) {
            int val = entry.getValue().getAsInt();
            if(val > max) {
                max = val;
            }
        }
        return max;
    }*/
}
