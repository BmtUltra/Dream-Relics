package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(LivingEntity.class)
public abstract class MistVeilRingMixin {
    
    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void onAddEffect(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity instanceof Player player) {
            LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
            if (optional.isPresent()) {
                ICuriosItemHandler handler = optional.orElseThrow(NullPointerException::new);
                if (handler.isEquipped(DRItems.MIST_VEIL_RING.get())) {
                    MobEffect effect = effectInstance.getEffect();
                    if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                        cir.setReturnValue(false);
                        cir.cancel();
                    }
                }
            }
        }
    }
}