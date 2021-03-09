package com.moddybunch.encrusted.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moddybunch.encrusted.Encrusted;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.*;

/*public class Translation {

    private static final JsonParser parser = new JsonParser();
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
    }
}*/
