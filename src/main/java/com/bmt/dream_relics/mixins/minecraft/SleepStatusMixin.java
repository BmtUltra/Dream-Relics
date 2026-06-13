package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

@Mixin(SleepStatus.class)
public abstract class SleepStatusMixin {

    @Shadow
    private int sleepingPlayers;

    @Shadow
    public abstract int sleepersNeeded(int requiredSleepPercentage);

    @Unique
    private List<ServerPlayer> dreamRelics$cachedPlayers = List.of();

    @Inject(method = "update", at = @At("HEAD"))
    private void dreamRelics$cachePlayers(List<ServerPlayer> players, CallbackInfoReturnable<Boolean> cir) {
        dreamRelics$cachedPlayers = players;
    }

    @Inject(method = "areEnoughSleeping", at = @At("HEAD"), cancellable = true)
    private void dreamRelics$checkBraceletSleeping(int requiredSleepPercentage, CallbackInfoReturnable<Boolean> cir) {
        int needed = sleepersNeeded(requiredSleepPercentage);
        if (sleepingPlayers >= needed) {
            return;
        }

        for (ServerPlayer player : dreamRelics$cachedPlayers) {
            boolean hasBracelet = CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.AWAKEN_DREAM_BRACELET.get()))
                    .orElse(false);

            if (hasBracelet && !player.isSleeping()) {
                cir.setReturnValue(true);
                return;
            }
        }
    }

    @Inject(method = "areEnoughDeepSleeping", at = @At("HEAD"), cancellable = true)
    private void dreamRelics$checkBraceletDeepSleeping(int requiredSleepPercentage, List<ServerPlayer> players, CallbackInfoReturnable<Boolean> cir) {
        int needed = sleepersNeeded(requiredSleepPercentage);

        int deepSleepCount = 0;
        for (ServerPlayer player : players) {
            if (player.isSleepingLongEnough()) {
                deepSleepCount++;
            }
        }

        if (deepSleepCount >= needed) {
            return;
        }

        for (ServerPlayer player : dreamRelics$cachedPlayers) {
            boolean hasBracelet = CuriosApi.getCuriosInventory(player)
                    .map(handler -> handler.isEquipped(DRItems.AWAKEN_DREAM_BRACELET.get()))
                    .orElse(false);

            if (hasBracelet && !player.isSleeping()) {
                cir.setReturnValue(true);
                return;
            }
        }
    }
}
