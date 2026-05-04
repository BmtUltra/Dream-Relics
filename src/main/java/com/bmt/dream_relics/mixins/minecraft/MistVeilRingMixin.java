package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LivingEntity.class)
public abstract class MistVeilRingMixin {

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void onAddEffect(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity instanceof Player player) {
            boolean hasMistVeilRing = CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.MIST_VEIL_RING.get()))
                    .orElse(false);

            if (hasMistVeilRing) {
                MobEffect effect = effectInstance.getEffect().value();
                if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}