package com.bmt.dream_relics.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.*;

public class MemoryStardustItem extends DreamRelicItem {
    private static final int MAX_SIZE = 9;
    private static final String TAG_ITEMS = "StoredRelics";

    public MemoryStardustItem(Properties properties) {
        super(properties, "tooltip.dream_relics.memory_stardust");
    }

    public static boolean hasItems(ItemStack stardust) {
        CompoundTag tag = stardust.getTag();
        return tag != null && tag.contains(TAG_ITEMS, Tag.TAG_COMPOUND);
    }

    public static ItemStackHandler getItems(ItemStack stardust) {
        ItemStackHandler handler = new ItemStackHandler(MAX_SIZE);
        CompoundTag tag = stardust.getOrCreateTag();
        if (tag.contains(TAG_ITEMS, Tag.TAG_COMPOUND)) {
            handler.deserializeNBT(tag.getCompound(TAG_ITEMS));
        }
        return handler;
    }

    public static void setItems(ItemStack stardust, ItemStackHandler items) {
        boolean allEmpty = true;
        for (int i = 0; i < items.getSlots(); i++) {
            if (!items.getStackInSlot(i).isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        if (allEmpty) {
            stardust.removeTagKey(TAG_ITEMS);
        } else {
            CompoundTag tag = stardust.getOrCreateTag();
            tag.put(TAG_ITEMS, items.serializeNBT());
        }
    }

    public static boolean canAdd(ItemStack relic) {
        if (relic.isEmpty()) {
            return false;
        }
        return CuriosApi.getCurio(relic).isPresent();
    }

    public static int add(ItemStack stardust, ItemStack relic) {
        return add(stardust, relic, false);
    }

    private static int add(ItemStack stardust, ItemStack relic, boolean simulate) {
        if (relic.isEmpty() || !canAdd(relic)) {
            return 0;
        }

        int totalCount = relic.getCount();
        ItemStackHandler items = getItems(stardust);
        ItemStack remaining = ItemHandlerHelper.insertItemStacked(items, relic, simulate);

        int addCount = totalCount - (remaining.isEmpty() ? 0 : remaining.getCount());
        if (!simulate && addCount > 0) {
            setItems(stardust, items);
        }
        return addCount;
    }

    private static Optional<ItemStack> removeOne(ItemStack stardust) {
        if (!hasItems(stardust)) {
            return Optional.empty();
        }

        ItemStackHandler items = getItems(stardust);
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack extractItem = items.extractItem(i, 1, false);
            if (!extractItem.isEmpty()) {
                setItems(stardust, items);
                return Optional.of(extractItem);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stardust, Slot slot, ClickAction action, Player player) {
        if (stardust.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack clickItem = slot.getItem();
        if (clickItem.isEmpty()) {
            removeOne(stardust).ifPresent(stack -> {
                playRemoveOneSound(player);
                slot.safeInsert(stack);
            });
            return true;
        } else if (canAdd(clickItem)) {
            int addCount = add(stardust, clickItem, true);
            if (addCount > 0) {
                ItemStack takeout = slot.safeTake(clickItem.getCount(), addCount, player);
                if (!takeout.isEmpty()) {
                    add(stardust, takeout);
                }
                playInsertSound(player);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stardust, ItemStack other, Slot slot, ClickAction action,
                                            Player player, SlotAccess access) {
        if (stardust.getCount() != 1) {
            return false;
        }

        if (action != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        if (other.isEmpty()) {
            removeOne(stardust).ifPresent(stack -> {
                playRemoveOneSound(player);
                access.set(stack);
            });
            return true;
        } else {
            int added = add(stardust, other);
            if (added > 0) {
                playInsertSound(player);
                other.shrink(added);
            }
            return added > 0;
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        toggleMethod(MethodName.EQ, stack, slotContext, prevStack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        toggleMethod(MethodName.UN, stack, slotContext, newStack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        toggleMethod(MethodName.CT, stack, slotContext, ItemStack.EMPTY);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        ItemStackHandler storedItems = getItems(stack);
        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack relic = storedItems.getStackInSlot(i);
            if (!relic.isEmpty() && relic.getItem() instanceof ICurioItem curioItem) {
                if (!curioItem.canEquip(slotContext, relic)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        ItemStackHandler storedItems = getItems(stack);
        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack relic = storedItems.getStackInSlot(i);
            if (!relic.isEmpty() && relic.getItem() instanceof ICurioItem curioItem) {
                if (!curioItem.canUnequip(slotContext, relic)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> result = ArrayListMultimap.create();

        if (!hasItems(stack)) {
            return result;
        }

        ItemStackHandler items = getItems(stack);
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack relic = items.getStackInSlot(i);
            if (relic.isEmpty()) continue;

            if (relic.getItem() instanceof ICurioItem curioItem) {
                UUID slotUuid = new UUID(uuid.getMostSignificantBits() + i, uuid.getLeastSignificantBits() + i);

                try {
                    Multimap<
                            Attribute,
                            AttributeModifier> relicModifiers = curioItem.getAttributeModifiers(slotContext, slotUuid, relic);

                    if (relicModifiers != null) {
                        for (Attribute attr : relicModifiers.keySet()) {
                            result.putAll(attr, relicModifiers.get(attr));
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return result;
    }

    private void toggleMethod(MethodName methodName, ItemStack stardust, SlotContext slotContext, ItemStack otherStack) {
        if (!hasItems(stardust)) return;

        ItemStackHandler items = getItems(stardust);
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack relic = items.getStackInSlot(i);
            if (relic.isEmpty()) continue;

            if (relic.getItem() instanceof ICurioItem curioItem) {
                try {
                    switch (methodName) {
                        case EQ -> curioItem.onEquip(slotContext, otherStack, relic);
                        case UN -> curioItem.onUnequip(slotContext, otherStack, relic);
                        case CT -> curioItem.curioTick(slotContext, relic);
                    }
                } catch (Exception ignored) {

                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (hasItems(stack) && flag.isAdvanced()) {
            ItemStackHandler items = getItems(stack);
            tooltip.add(Component.empty());
            for (int i = 0; i < items.getSlots(); i++) {
                ItemStack relic = items.getStackInSlot(i);
                if (!relic.isEmpty()) {
                    tooltip.add(Component.literal("  " + (i + 1) + ". ")
                            .append(relic.getHoverName())
                            .withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if (hasItems(stack)) {
            ItemStackHandler items = getItems(stack);
            return Optional.of(new com.bmt.dream_relics.client.inventory.tooltip.MemoryStardustTooltip.Component(items));
        }
        return Optional.empty();
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    public enum MethodName {
        EQ, UN, CT
    }
}