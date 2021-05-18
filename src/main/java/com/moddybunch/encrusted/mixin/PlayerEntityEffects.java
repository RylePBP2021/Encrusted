package com.moddybunch.encrusted.mixin;

import com.moddybunch.encrusted.Encrusted;
import com.moddybunch.encrusted.api.EncrustedItems;
import com.moddybunch.encrusted.api.items.EncrustedSwordsItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityEffects extends LivingEntity{

    protected PlayerEntityEffects(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void injectMethod(Entity target, CallbackInfo ci) {
        if(!world.isClient && target.isAttackable() && target instanceof LivingEntity &&
            getMainHandStack().getItem() instanceof EncrustedSwordsItem) {
            Encrusted.EncrustedLog.info("Detected player attacking with encrusted weapon! Using the onAttacking command");
            ((EncrustedSwordsItem)getMainHandStack().getItem()).onAttack(target);
        }
    }
}
