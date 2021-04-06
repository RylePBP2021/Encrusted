package com.moddybunch.encrusted.api.armor;

import com.moddybunch.encrusted.api.EncrustedArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class EncrustedDyeableArmorItem extends DyeableArmorItem {

    public EncrustedDyeableArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Settings settings) {
        super(armorMaterial, equipmentSlot, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this.isFood()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        } else {
            return super.use(world, user, hand);
        }
    }

    public void onDamaged(DamageSource sauce, float amount, Consumer<StatusEffectInstance> applyStatusEffect) {
        ((EncrustedArmor) this.getMaterial()).getEncrustor().onDamaged(new DamagedData(sauce, amount, applyStatusEffect));
    }
}
