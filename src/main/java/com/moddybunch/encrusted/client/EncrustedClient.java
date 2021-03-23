package com.moddybunch.encrusted.client;

import com.moddybunch.encrusted.util.EncrustedRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;

@Environment(EnvType.CLIENT)
public class EncrustedClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        for(DyeableArmorItem item : EncrustedRegistries.dyeableArmors) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), item);
        }
    }
}
