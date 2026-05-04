package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlawlessGem extends DreamRelicItemBase {

    public FlawlessGem(Properties properties) {
        super(properties.stacksTo(16));
    }

    public static void removeNegativeEnchantments(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isEnchanted()) {
            return;
        }

        ItemEnchantments enchantments = itemStack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) {
            return;
        }

        Set<Holder<Enchantment>> curses = enchantments.keySet().stream()
                .filter(holder -> holder.is(EnchantmentTags.CURSE))
                .collect(Collectors.toSet());

        if (curses.isEmpty()) {
            return;
        }

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
        curses.forEach(curse -> mutable.removeIf(h -> h.is(curse)));
        itemStack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());

    }

    public static void repairItem(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isDamaged()) {
            return;
        }

        itemStack.setDamageValue(0);
    }

    public static boolean hasNegativeEnchantments(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isEnchanted()) {
            return false;
        }

        ItemEnchantments enchantments = itemStack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null) {
            return false;
        }

        return enchantments.keySet().stream()
                .anyMatch(holder -> holder.is(EnchantmentTags.CURSE));
    }

    public static boolean needsRepair(ItemStack itemStack) {
        return !itemStack.isEmpty() &&
                itemStack.isDamaged() &&
                itemStack.getDamageValue() > 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipComponents, flag);
        tooltipComponents.add(Component.translatable("item.dream_relics.tooltip.item.flawless_gem.restore")
                .withStyle(ChatFormatting.GRAY));
    }
}