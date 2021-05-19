package com.moddybunch.encrusted.api.items;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.EncrustedArmor;
import com.moddybunch.encrusted.api.EncrustedItems;
import com.moddybunch.encrusted.api.armor.DamagedData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

import java.util.function.Consumer;

public class EncrustedSwordsItem extends SwordItem {

    public EncrustedSwordsItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public void onAttack(Entity target) {
        Encrusted.EncrustedLog.info("Inside the ArmorItem onDamaged function, continuing to Encrustor.");
        ((EncrustedItems) this.getMaterial()).getEncrustor().onAttack(target);
    }
}
