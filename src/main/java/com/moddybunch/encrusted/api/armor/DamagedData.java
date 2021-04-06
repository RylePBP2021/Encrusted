package com.moddybunch.encrusted.api.armor;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.function.Consumer;

/**
 * Stores data to be eaten by a consumer when the entity wearing encrusted armor is damaged
 *
 * @author MitchP404
 */
public class DamagedData {

    private final DamageSource sauce;
    private final float amount;
    private final Consumer<StatusEffectInstance> statusEffect;

    public DamagedData(DamageSource sauce, float amount, Consumer<StatusEffectInstance> applyStatusEffect) {
        this.sauce = sauce;
        this.statusEffect = applyStatusEffect;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public DamageSource getSauce() {
        return sauce;
    }

    public void applyStatus(StatusEffectInstance effect) {
        statusEffect.accept(effect);
    }

}
