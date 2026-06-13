package com.bmt.dream_relics.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

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

        ItemEnchantments keyEnchantments = key.get(DataComponents.ENCHANTMENTS);
        if (keyEnchantments == null || keyEnchantments.isEmpty()) {
            return false;
        }

        ItemEnchantments targetEnchantments = target.get(DataComponents.ENCHANTMENTS);
        ItemEnchantments.Mutable targetMutable = new ItemEnchantments.Mutable(
                targetEnchantments != null ? targetEnchantments : ItemEnchantments.EMPTY
        );

        boolean transferred = false;

        for (var entry : keyEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment enchantment = enchantmentHolder.value();
            int level = entry.getIntValue();

            if (enchantment.canEnchant(target)) {
                boolean hasConflict = false;
                if (targetEnchantments != null) {
                    for (Holder<Enchantment> existing : targetEnchantments.keySet()) {
                        if (!Enchantment.areCompatible(enchantmentHolder, existing)) {
                            hasConflict = true;
                            break;
                        }
                    }
                }

                if (!hasConflict) {
                    targetMutable.set(enchantmentHolder, level);
                    transferred = true;
                }
            }
        }

        if (transferred) {
            target.set(DataComponents.ENCHANTMENTS, targetMutable.toImmutable());

            ItemEnchantments newTargetEnchantments = target.get(DataComponents.ENCHANTMENTS);
            ItemEnchantments.Mutable keyMutable = new ItemEnchantments.Mutable(keyEnchantments);

            for (var entry : keyEnchantments.entrySet()) {
                Holder<Enchantment> enchantmentHolder = entry.getKey();
                int afterLevel = newTargetEnchantments != null ?
                        newTargetEnchantments.getLevel(enchantmentHolder) : 0;
                int beforeLevel = targetEnchantments != null ?
                        targetEnchantments.getLevel(enchantmentHolder) : 0;

                if (afterLevel > 0 && (beforeLevel == 0 || beforeLevel < afterLevel)) {
                    keyMutable.removeIf(h -> h.is(enchantmentHolder));
                }
            }

            key.set(DataComponents.ENCHANTMENTS, keyMutable.toImmutable());

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

        ItemEnchantments bookEnchantments = other.get(DataComponents.STORED_ENCHANTMENTS);
        if (bookEnchantments == null || bookEnchantments.isEmpty()) {
            return false;
        }

        ItemEnchantments keyEnchantments = key.get(DataComponents.ENCHANTMENTS);
        ItemEnchantments.Mutable keyMutable = new ItemEnchantments.Mutable(
                keyEnchantments != null ? keyEnchantments : ItemEnchantments.EMPTY
        );

        boolean added = false;

        for (var entry : bookEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            int level = entry.getIntValue();

            boolean hasConflict = false;
            if (keyEnchantments != null) {
                for (Holder<Enchantment> existing : keyEnchantments.keySet()) {
                    if (!Enchantment.areCompatible(enchantmentHolder, existing)) {
                        hasConflict = true;
                        break;
                    }
                }
            }

            if (!hasConflict) {
                int existingLevel = keyMutable.getLevel(enchantmentHolder);
                if (existingLevel == 0 || level > existingLevel) {
                    keyMutable.set(enchantmentHolder, level);
                }
                added = true;
            }
        }

        if (added) {
            key.set(DataComponents.ENCHANTMENTS, keyMutable.toImmutable());
            other.shrink(1);
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F,
                    0.8F + player.level().getRandom().nextFloat() * 0.4F);
            return true;
        }
        return false;
    }
}