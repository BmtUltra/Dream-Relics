package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.mixins.minecraft.accessor.InventoryAccessor;
import com.bmt.dream_relics.util.SoulboundCapture;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Shadow
    @Final
    public NonNullList<ItemStack> items;

    @Shadow
    public int selected;

    @Shadow
    @Final
    public Player player;

    @Unique
    private final Map<int[], ItemStack> dream_relics$reservedSoulMirrors = new HashMap<>();

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        boolean hasYearsAmber = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(DRItems.YEARS_AMBER.get()))
                .orElse(false);

        if (hasYearsAmber) {
            @Nullable Pair<Float, ItemStack> pair = YearsAmber.findBestCorrectTool(player, this.items.get(this.selected), blockState);
            if (pair != null) {
                cir.setReturnValue(pair.getFirst());
            }
        }
    }

    @Inject(method = "dropAll", at = @At("HEAD"))
    private void dream_relics$reserveSoulMirrors(CallbackInfo ci) {
        List<List<ItemStack>> compartments = ((InventoryAccessor) this).getCompartments();

        if (player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            return;
        }

        for (int listIndex = 0; listIndex < compartments.size(); listIndex++) {
            List<ItemStack> list = compartments.get(listIndex);
            for (int itemIndex = 0; itemIndex < list.size(); itemIndex++) {
                ItemStack itemstack = list.get(itemIndex);

                if (!itemstack.isEmpty()
                        && (itemstack.getItem() == DRItems.SOUL_MIRROR.get() || SoulboundCapture.isSoulbound(itemstack))) {
                    dream_relics$reservedSoulMirrors.put(new int[]{listIndex, itemIndex}, itemstack);
                    list.set(itemIndex, ItemStack.EMPTY);
                }
            }
        }
    }

    @Inject(method = "dropAll", at = @At("RETURN"))
    private void dream_relics$restoreSoulMirrors(CallbackInfo ci) {
        List<List<ItemStack>> compartments = ((InventoryAccessor) this).getCompartments();
        dream_relics$reservedSoulMirrors.forEach((position, stack) ->
                compartments.get(position[0]).set(position[1], stack));
        dream_relics$reservedSoulMirrors.clear();
    }
}