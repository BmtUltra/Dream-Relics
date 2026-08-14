package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.util.YearsAmberDurabilityTracker;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    protected ServerLevel level;

    @Final
    @Shadow
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", 
            at = @At("HEAD"))
    private void storeBlockStateBeforeMine(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = level.getBlockState(pos);
        YearsAmberDurabilityTracker.setBlock(blockState);
    }

    @Inject(method = "destroyBlock",
            at = @At("RETURN"))
    private void applyDurabilityToBestTool(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            YearsAmberDurabilityTracker.clear();
            return;
        }

        boolean hasYearsAmber = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(DRItems.YEARS_AMBER.get()))
                .orElse(false);

        if (!hasYearsAmber) {
            YearsAmberDurabilityTracker.clear();
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        @Nullable Pair<Float, ItemStack> bestToolPair = YearsAmber.findBestCorrectToolFromTracker(player, mainHand);

        if (bestToolPair != null) {
            ItemStack bestTool = bestToolPair.getSecond();
            
            if (bestTool != mainHand || mainHand.isEmpty()) {
                bestTool.hurtAndBreak(1, level, player, (Item p_348383_) -> player.onEquippedItemBroken(p_348383_, EquipmentSlot.MAINHAND));
            }
        }
        YearsAmberDurabilityTracker.clear();
    }
}
