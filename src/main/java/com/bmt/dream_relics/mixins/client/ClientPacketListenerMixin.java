package com.bmt.dream_relics.mixins.client;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Inject(method = "findTotem", at = @At("RETURN"), cancellable = true)
    private static void dreamRelics$findDreamTotem(Player player, CallbackInfoReturnable<ItemStack> cir) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(DRItems.DREAM_TOTEM.get())) {
                cir.setReturnValue(stack.copy());
                return;
            }
        }
        
        ItemStack currentResult = cir.getReturnValue();
        if (currentResult.is(Items.TOTEM_OF_UNDYING)) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                var results = handler.findCurios(DRItems.DREAM_TOTEM.get());
                if (!results.isEmpty()) {
                    cir.setReturnValue(results.getFirst().stack().copy());
                }
            });
        }
    }
}
