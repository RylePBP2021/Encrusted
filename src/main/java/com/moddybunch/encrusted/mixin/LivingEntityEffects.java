package com.moddybunch.encrusted.mixin;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.armor.EncrustedArmorItem;
import com.moddybunch.encrusted.api.armor.EncrustedDyeableArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityEffects {

    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow public abstract void applyStatusEffect(StatusEffectInstance effect);

    @Inject(method = "applyArmorToDamage", at = @At("RETURN"), cancellable = true)
    private void injectMethod(DamageSource source, float amount, CallbackInfoReturnable<Float> info) {

        Iterable<ItemStack> armorIterator = this.getArmorItems();
        if(armorIterator == null) {
            info.cancel();
        }
        else if(source instanceof EntityDamageSource) {
            Encrusted.EncrustedLog.info("Detected attack on armorable entity! Scanning for encrusted armor");
            for (ItemStack armor : armorIterator) {
                if(armor.getItem() instanceof EncrustedArmorItem) {
                    Encrusted.EncrustedLog.info("Found encrusted armor: " + armor.getItem().getName().asString() + ". Running the \"onDamaged\" command.");
                    ((EncrustedArmorItem) armor.getItem()).onDamaged(source, amount, this::applyStatusEffect);
                } else if(armor.getItem() instanceof EncrustedDyeableArmorItem) {
                    ((EncrustedDyeableArmorItem) armor.getItem()).onDamaged(source, amount, this::applyStatusEffect);
                }
            }
        }
    }
}
