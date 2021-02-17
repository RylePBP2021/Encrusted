package com.moddybunch.encrusted.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.util.EncrustedRegistries;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class SmithingRecipeMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for(JsonObject recipe : EncrustedRegistries.smithingRecipes) {
            if (recipe != null) {
                map.put(new Identifier(recipe.get("result").getAsJsonObject().get("item").getAsString()), recipe);
            }
        }
    }
}
