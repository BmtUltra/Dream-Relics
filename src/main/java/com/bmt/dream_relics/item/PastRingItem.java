package com.bmt.dream_relics.item;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PastRingItem extends DreamRelicItem {

    public PastRingItem(Properties properties) {
        super(properties, "tooltip.dream_relics.past_ring");
    }

    public static void repairPlayerItems(Player player) {
        if (player.level().isClientSide) {
            return;
        }

        if (!isEquipped(player)) {
            return;
        }

        int totalExperience = player.totalExperience;
        if (totalExperience <= 0) {
            return;
        }

        // 修复主手物品
        ItemStack mainHand = player.getMainHandItem();
        if (repairItemIfNeeded(player, mainHand)) {
            return;
        }

        // 修复副手物品
        ItemStack offHand = player.getOffhandItem();
        if (repairItemIfNeeded(player, offHand)) {
            return;
        }

        // 修复背包物品
        for (ItemStack stack : player.getInventory().items) {
            if (repairItemIfNeeded(player, stack)) {
                return;
            }
        }

        // 修复盔甲
        for (ItemStack stack : player.getInventory().armor) {
            if (repairItemIfNeeded(player, stack)) {
                return;
            }
        }
    }

    private static boolean repairItemIfNeeded(Player player, ItemStack stack) {
        if (!canRepairItem(stack)) {
            return false;
        }

        int repairCost = CommonConfig.pastRingXpCostPerDurability;
        if (repairCost <= 0) {
            return false;
        }

        if (player.totalExperience >= repairCost) {
            repairOneDurability(stack);
            player.giveExperiencePoints(-repairCost);
            return true;
        }

        return false;
    }

    private static boolean canRepairItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        if (!stack.isDamageableItem()) {
            return false;
        }
        return stack.getDamageValue() > 0;
    }

    private static void repairOneDurability(ItemStack stack) {
        int damage = stack.getDamageValue();
        if (damage > 0) {
            stack.setDamageValue(damage - 1);
        }
    }

    public static boolean isEquipped(Player player) {
        var capability = player.getCapability(
                top.theillusivec4.curios.api.CuriosCapability.INVENTORY
        );
        if (capability == null) {
            return false;
        }
        return capability.isEquipped(DRItems.PAST_RING.get());
    }
}