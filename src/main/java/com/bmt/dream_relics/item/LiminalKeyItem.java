package com.bmt.dream_relics.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class LiminalKeyItem extends DreamRelicItem {
    public LiminalKeyItem(Properties properties) {
        super(properties, "tooltip.dream_relics.liminal_key");
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack key, Slot slot, ClickAction action, Player player) {
        if (key.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack target = slot.getItem();
        if (target.isEmpty()) {
            return false;
        }

        Map<Enchantment, Integer> keyEnchantments = EnchantmentHelper.getEnchantments(key);
        if (keyEnchantments.isEmpty()) {
            return false;
        }


        Map<Enchantment, Integer> beforeEnchantments = EnchantmentHelper.getEnchantments(target);
        boolean transferred = false;

        for (Map.Entry<Enchantment, Integer> entry : keyEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            if (enchantment.canEnchant(target)) {
                boolean hasConflict = false;
                for (Enchantment existing : beforeEnchantments.keySet()) {
                    if (!enchantment.isCompatibleWith(existing)) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    target.enchant(enchantment, level);
                    transferred = true;
                }
            }
        }

        if (transferred) {
            Map<Enchantment, Integer> afterEnchantments = EnchantmentHelper.getEnchantments(slot.getItem());

            Map<Enchantment, Integer> remainingEnchantments = EnchantmentHelper.getEnchantments(key);
            for (Map.Entry<Enchantment, Integer> entry : keyEnchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                Integer afterLevel = afterEnchantments.get(enchantment);
                Integer beforeLevel = beforeEnchantments.get(enchantment);
                if (afterLevel != null && (beforeLevel == null || beforeLevel < afterLevel)) {
                    remainingEnchantments.remove(enchantment);
                }
            }
            EnchantmentHelper.setEnchantments(remainingEnchantments, key);
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F,
                    0.8F + player.level().getRandom().nextFloat() * 0.4F);
            return true;
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack key, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (key.getCount() != 1 || action != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        if (other.isEmpty()) {
            return false;
        }

        if (!other.is(Items.ENCHANTED_BOOK)) {
            return false;
        }

        Map<Enchantment, Integer> bookEnchantments = EnchantmentHelper.getEnchantments(other);
        if (bookEnchantments.isEmpty()) {
            return false;
        }

        Map<Enchantment, Integer> keyEnchantments = EnchantmentHelper.getEnchantments(key);

        boolean added = false;

        for (Map.Entry<Enchantment, Integer> entry : bookEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            boolean hasConflict = false;
            for (Enchantment existing : keyEnchantments.keySet()) {
                if (!enchantment.isCompatibleWith(existing)) {
                    hasConflict = true;
                    break;
                }
            }

            if (!hasConflict) {
                Integer existingLevel = keyEnchantments.get(enchantment);
                if (existingLevel == null || level > existingLevel) {
                    keyEnchantments.put(enchantment, level);
                }
                added = true;
            }
        }

        if (added) {
            EnchantmentHelper.setEnchantments(keyEnchantments, key);
            other.shrink(1);
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F,
                    0.8F + player.level().getRandom().nextFloat() * 0.4F);
            return true;
        }
        return false;
    }
}