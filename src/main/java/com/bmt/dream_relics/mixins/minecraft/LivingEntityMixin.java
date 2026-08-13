package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.client.event.GlowEffectHandler;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private boolean dreamRelics$hasIcarusWings(LivingEntity entity) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            return CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.ICARUS_WINGS.get()))
                    .orElse(false);
        }
        return false;
    }

    @Unique
    private boolean dreamRelics$hasElvenBoots(LivingEntity entity) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            return CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.ELVEN_BOOTS.get()))
                    .orElse(false);
        }
        return false;
    }

    @Unique
    private boolean dreamRelics$hasAstralNecklace(LivingEntity entity) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            return CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.ASTRAL_NECKLACE.get()))
                    .orElse(false);
        }
        return false;
    }

    @Inject(method = "calculateFallDamage", at = @At("RETURN"), cancellable = true)
    public void dreamRelics$modifyFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (dreamRelics$hasElvenBoots(entity) && !entity.hasEffect(MobEffects.JUMP)) {
            int originalDamage = cir.getReturnValue();
            int reducedDamage = Math.max(0, originalDamage - 1);
            cir.setReturnValue(reducedDamage);
        }
    }

    @Inject(method = "getJumpBoostPower", at = @At(value = "HEAD"), cancellable = true)
    private void dreamRelics$improvedJumpBoost(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (dreamRelics$hasElvenBoots(entity)) {
            float jumpBoost = 0.1F;

            MobEffectInstance jumpEffect = entity.getEffect(MobEffects.JUMP);
            if (jumpEffect != null) {
                jumpBoost += 0.1F * (float)(jumpEffect.getAmplifier() + 1);
            }
            cir.setReturnValue(jumpBoost);
        }
    }

    @Inject(method = "getBlockSpeedFactor", at = @At("HEAD"), cancellable = true)
    private void dreamRelics$onGetBlockSpeedFactor(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (dreamRelics$hasIcarusWings(entity)) {
            if (!entity.onGround() && !entity.onClimbable() && !entity.isInWater()) {
                int x = (int) Math.floor(entity.getX());
                int y = (int) Math.floor(entity.getY() - 0.2);
                int z = (int) Math.floor(entity.getZ());

                BlockState blockState = entity.level().getBlockState(net.minecraft.core.BlockPos.containing(x, y, z));
                float friction = blockState.getBlock().getFriction();

                float baseSpeed = 1.0f;
                float frictionBonus = Math.max(1 - friction, 0) * 0.15f;
                float totalSpeed = baseSpeed + frictionBonus;

                cir.setReturnValue(totalSpeed);
            }
        }
    }

    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    private void dreamRelics$onGetDamageAfterArmorAbsorb(DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
        LivingEntity target = (LivingEntity) (Object) this;

        if (damageSource.getEntity() instanceof LivingEntity attacker) {
            if (dreamRelics$hasAstralNecklace(attacker)) {
                float targetArmor = target.getArmorValue();
                float percentagePenetration = targetArmor * 0.3f;
                float fixedPenetration = 10.0f;
                float totalPenetration = percentagePenetration + fixedPenetration;
                float effectiveArmor = Math.max(0, targetArmor - totalPenetration);
                float armorDamageReduction = effectiveArmor * 0.04f;
                float totalDamageReduction = armorDamageReduction * damage;
                float finalDamage = Math.max(0, damage - totalDamageReduction);
                cir.setReturnValue(finalDamage);
            }
        }
    }

    @Inject(method = "isCurrentlyGlowing", at = @At("RETURN"), cancellable = true)
    private void onIsCurrentlyGlowing(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (GlowEffectHandler.getGlowingEntities().contains(entity)) {
            cir.setReturnValue(true);
        }
    }

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