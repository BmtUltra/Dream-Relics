package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Player.class)
public class DreamBalanceMixin {
    
    @Inject(method = "getXpNeededForNextLevel", at = @At("HEAD"), cancellable = true)
    private void onGetXpNeededForNextLevel(CallbackInfoReturnable<Integer> cir) {
        Player player = (Player) (Object) this;

        boolean hasDreamBalance = CuriosApi.getCuriosInventory(player).resolve()
                .map(inventory -> inventory.findFirstCurio(DRItems.DREAM_BALANCE.get()).isPresent())
                .orElse(false);
        
        if (hasDreamBalance) {
            cir.setReturnValue(30);
        }
    }
}