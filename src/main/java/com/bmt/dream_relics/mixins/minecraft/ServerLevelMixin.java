package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Accessor("sleepStatus")
    public abstract SleepStatus getSleepStatus();

    @Inject(
            method = "updateSleepingPlayerList",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$onUpdateSleepingPlayerList(CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        SleepStatus sleepStatus = this.getSleepStatus();
        List<ServerPlayer> filteredPlayers = new ArrayList<>();

        for (ServerPlayer player : level.players()) {
            LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
            boolean hasAwakenDreamBracelet = false;

            if (optional.isPresent()) {
                ICuriosItemHandler handler = optional.orElseThrow(NullPointerException::new);
                hasAwakenDreamBracelet = handler.isEquipped(DRItems.AWAKEN_DREAM_BRACELET.get());
            }

            if (!hasAwakenDreamBracelet) {
                filteredPlayers.add(player);
            }
        }
        sleepStatus.update(filteredPlayers);
        ci.cancel();
    }
}