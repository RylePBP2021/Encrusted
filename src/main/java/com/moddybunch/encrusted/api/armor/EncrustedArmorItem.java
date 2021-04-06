package com.moddybunch.encrusted.api.armor;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.EncrustedArmor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class EncrustedArmorItem extends ArmorItem {

    public EncrustedArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
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
        Encrusted.EncrustedLog.info("Inside the ArmorItem onDamaged function, continuing to Encrustor.");
        ((EncrustedArmor) this.getMaterial()).getEncrustor().onDamaged(new DamagedData(sauce, amount, applyStatusEffect));
    }
}
