package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(LocalPlayer.class)
public abstract class IcarusWingsMixin extends AbstractClientPlayer {

    @Unique
    private int dreamRelics$jumpCount = 0;

    @Unique
    private boolean dreamRelics$jumpedLastTick = false;

    @Unique
    private boolean dreamRelics$hasIcarusWings = false;

    public IcarusWingsMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void dreamRelics$checkIcarusWings(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
        if (optional.isPresent()) {
            ICuriosItemHandler handler = optional.orElseThrow(NullPointerException::new);
            dreamRelics$hasIcarusWings = handler.isEquipped(DRItems.ICARUS_WINGS.get());
        } else {
            dreamRelics$hasIcarusWings = false;
        }
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void dreamRelics$handleMultiJump(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (!dreamRelics$hasIcarusWings) {
            return;
        }

        if (player.onGround() || player.onClimbable() || player.isInWater()) {
            dreamRelics$jumpCount = 4;
        } else if (!dreamRelics$jumpedLastTick && dreamRelics$jumpCount > 0 && player.getDeltaMovement().y < 0) {
            if (player.input.jumping && !player.getAbilities().flying) {
                if (dreamRelics$canJump(player)) {
                    dreamRelics$jumpCount--;
                    player.jumpFromGround();

                    player.playSound(net.minecraft.sounds.SoundEvents.PLAYER_SMALL_FALL,
                            0.5F, 1.0F);

                    if (player.level().isClientSide) {
                        for (int i = 0; i < 10; i++) {
                            double d0 = player.getRandom().nextGaussian() * 0.02D;
                            double d1 = player.getRandom().nextGaussian() * 0.02D;
                            double d2 = player.getRandom().nextGaussian() * 0.02D;
                            player.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD,
                                    player.getX(), player.getY(), player.getZ(),
                                    d0, d1, d2);
                        }
                    }
                }
            }
        }
        dreamRelics$jumpedLastTick = player.input.jumping;
    }

    @Unique
    private boolean dreamRelics$canJump(LocalPlayer player) {
        return !dreamRelics$wearingUsableElytra(player) &&
                !player.isFallFlying() &&
                !player.isPassenger() &&
                !player.isInWater() &&
                !player.hasEffect(net.minecraft.world.effect.MobEffects.LEVITATION);
    }

    @Unique
    private boolean dreamRelics$wearingUsableElytra(LocalPlayer player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA &&
                ElytraItem.isFlyEnabled(chestItemStack);
    }
}