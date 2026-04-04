package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    
    @Unique
    private boolean dreamRelics$hasIcarusWings(LivingEntity entity) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
            if (optional.isPresent()) {
                ICuriosItemHandler handler = optional.orElseThrow(NullPointerException::new);
                return handler.isEquipped(DRItems.ICARUS_WINGS.get());
            }
        }
        return false;
    }

    @Inject(method = "calculateFallDamage", at = @At("RETURN"), cancellable = true)
    public void dreamRelics$modifyFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (dreamRelics$hasIcarusWings(entity) && !entity.hasEffect(MobEffects.JUMP)) {
            int originalDamage = cir.getReturnValue();
            int reducedDamage = Math.max(0, originalDamage - 1);
            cir.setReturnValue(reducedDamage);
        }
    }

    @Inject(method = "getJumpBoostPower", at = @At(value = "HEAD"), cancellable = true)
    private void dreamRelics$improvedJumpBoost(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        if (dreamRelics$hasIcarusWings(entity)) {
            float jumpBoost = 0.1F;

            MobEffectInstance jumpEffect = entity.getEffect(MobEffects.JUMP);
            if (jumpEffect != null) {
                jumpBoost += 0.1F * (float)(jumpEffect.getAmplifier() + 1);
            }
            cir.setReturnValue(jumpBoost);
        }
    }
}