package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

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

        for (ItemStack stack : player.getInventory().items) {
            repairItemIfNeeded(player, stack);
            if (player.totalExperience <= 0) {
                return;
            }
        }

        for (ItemStack stack : player.getInventory().armor) {
            repairItemIfNeeded(player, stack);
            if (player.totalExperience <= 0) {
                return;
            }
        }
        ItemStack offhand = player.getInventory().offhand.get(0);
        repairItemIfNeeded(player, offhand);
    }

    private static void repairItemIfNeeded(Player player, ItemStack stack) {
        if (canRepairItem(stack)) {
            int repairCost = calculateRepairCostForOneDurability(stack);

            if (repairCost > 0 && player.totalExperience >= repairCost) {
                repairOneDurability(stack);
                player.giveExperiencePoints(-repairCost);
            }
        }
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

    private static int calculateRepairCostForOneDurability(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();

        if (maxDamage <= 0) {
            return 0;
        }
        return 1;
    }

    private static void repairOneDurability(ItemStack stack) {
        int damage = stack.getDamageValue();

        if (damage > 0) {
            stack.setDamageValue(damage - 1);
        }
    }

    public static boolean isEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player).resolve()
                .map(inventory -> inventory.findFirstCurio(DRItems.PAST_RING.get()).isPresent())
                .orElse(false);
    }
}