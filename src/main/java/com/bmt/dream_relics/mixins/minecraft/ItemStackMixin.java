package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.util.YearsAmberDurabilityTracker;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", 
            at = @At("HEAD"), cancellable = true)
    private void yearsAmberDurabilityTransfer(int amount, ServerLevel level, @Nullable LivingEntity entity, Consumer<Item> breakCallback, CallbackInfo ci) {
        if (!(entity instanceof Player player)) {
            return;
        }

        boolean hasYearsAmber = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(DRItems.YEARS_AMBER.get()))
                .orElse(false);

        if (!hasYearsAmber) {
            return;
        }

        ItemStack currentStack = (ItemStack) (Object) this;
        
        @Nullable Pair<Float, ItemStack> bestToolPair = YearsAmber.findBestCorrectToolFromTracker(player, currentStack);
        
        if (bestToolPair != null) {
            ItemStack bestTool = bestToolPair.getSecond();
            
            if (bestTool != currentStack) {
                ci.cancel();
                
                EquipmentSlot slot = dream_Relics$getEquipmentSlot(player, bestTool);
                bestTool.hurtAndBreak(amount, level, entity, breakCallback);
                
                YearsAmberDurabilityTracker.clear();
                return;
            }
        }
        YearsAmberDurabilityTracker.clear();
    }

    @Unique
    private EquipmentSlot dream_Relics$getEquipmentSlot(Player player, ItemStack targetStack) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HAND) {
                ItemStack stackInSlot = player.getItemBySlot(slot);
                if (stackInSlot == targetStack) {
                    return slot;
                }
            }
        }
        
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i) == targetStack) {
                return EquipmentSlot.MAINHAND;
            }
        }
        return EquipmentSlot.MAINHAND;
    }
}
