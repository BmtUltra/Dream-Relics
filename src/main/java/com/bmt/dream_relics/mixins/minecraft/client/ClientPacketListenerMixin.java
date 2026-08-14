package com.bmt.dream_relics.mixins.minecraft.client;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow
    private ClientLevel level;

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

    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void dreamRelics$handleDreamTotemEvent(ClientboundEntityEventPacket packet, CallbackInfo ci) {
        if (packet.getEventId() == 66) {
            Entity entity = packet.getEntity(this.level);
            if (entity instanceof Player player) {
                ci.cancel();
                Minecraft.getInstance().execute(() -> {
                    this.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                            SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F, false);
                    Minecraft minecraft = Minecraft.getInstance();
                    if (entity == minecraft.player) {
                        ItemStack totemStack = dreamRelics$findTotem(minecraft.player);
                        minecraft.gameRenderer.displayItemActivation(totemStack);
                    }
                });
            }
        }
    }

    @Unique
    private static ItemStack dreamRelics$findTotem(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(DRItems.DREAM_TOTEM.get())) {
                return stack.copy();
            }
        }
        return new ItemStack(DRItems.DREAM_TOTEM.get());
    }
}