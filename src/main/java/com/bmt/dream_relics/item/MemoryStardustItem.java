package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRDataComponents;
import com.bmt.dream_relics.init.DRDataComponents.MemoryStardustContainer;
import com.bmt.dream_relics.compat.artifacts.ArtifactsCompat;
import com.bmt.dream_relics.util.DRUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryStardustItem extends DreamRelicItem {
    private static final int MAX_SIZE = 9;

    public MemoryStardustItem(Properties properties) {
        super(properties, "tooltip.dream_relics.memory_stardust");
    }

    public static boolean hasItems(ItemStack stardust) {
        return stardust.has(DRDataComponents.MEMORY_STARDUST_ITEMS);
    }

    public static ItemStackHandler getItems(ItemStack stardust) {
        MemoryStardustContainer container = stardust.get(DRDataComponents.MEMORY_STARDUST_ITEMS);
        if (container != null) {
            return container.items();
        }
        return new ItemStackHandler(MAX_SIZE);
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
            stardust.remove(DRDataComponents.MEMORY_STARDUST_ITEMS);
        } else {
            stardust.set(DRDataComponents.MEMORY_STARDUST_ITEMS, MemoryStardustContainer.of(items));
        }
    }

    public static boolean canAdd(ItemStack relic) {
        if (relic.isEmpty()) {
            return false;
        }
        if (relic.is(DRUtil.Items.CANT_STORED_IN_MEMORY_STARDUST)) {
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
        for (int i = items.getSlots() - 1; i >= 0; i--) {
            ItemStack extractItem = items.extractItem(i, 1, false);
            if (!extractItem.isEmpty()) {
                setItems(stardust, items);
                return Optional.of(extractItem);
            }
        }
        return Optional.empty();
    }

    private static void triggerArtifactsSync(LivingEntity entity, ItemStack removedItem, ItemStack addedItem) {
        if (ArtifactsCompat.isArtifactsLoaded()) {
            ArtifactsCompat.syncItemChange(entity, removedItem, addedItem);
        }
    }

    private static void syncAllInternalItems(LivingEntity entity, ItemStack stardust, boolean equip) {
        if (!hasItems(stardust)) return;
        
        ItemStackHandler items = getItems(stardust);
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack relic = items.getStackInSlot(i);
            if (!relic.isEmpty()) {
                if (equip) {
                    triggerArtifactsSync(entity, ItemStack.EMPTY, relic);
                } else {
                    triggerArtifactsSync(entity, relic, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stardust, Slot slot, ClickAction action, Player player) {
        if (stardust.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        }

        ItemStack clickItem = slot.getItem();
        if (clickItem.isEmpty()) {
            Optional<ItemStack> removed = removeOne(stardust);
            if (removed.isPresent()) {
                ItemStack stack = removed.get();
                if (slot.mayPlace(stack)) {
                    playRemoveOneSound(player);
                    slot.safeInsert(stack);
                    triggerArtifactsSync(player, stack, ItemStack.EMPTY);
                } else {
                    add(stardust, stack);
                }
            }
            return true;
        } else if (canAdd(clickItem)) {
            int addCount = add(stardust, clickItem, true);
            if (addCount > 0) {
                ItemStack takeout = slot.safeTake(clickItem.getCount(), addCount, player);
                if (!takeout.isEmpty()) {
                    add(stardust, takeout);
                    triggerArtifactsSync(player, ItemStack.EMPTY, takeout);
                }
                playInsertSound(player);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stardust, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stardust.getCount() != 1) {
            return false;
        }

        if (action != ClickAction.SECONDARY || !slot.allowModification(player)) {
            return false;
        }

        if (other.isEmpty()) {
            Optional<ItemStack> removed = removeOne(stardust);
            if (removed.isPresent()) {
                ItemStack stack = removed.get();
                playRemoveOneSound(player);
                access.set(stack);
                triggerArtifactsSync(player, stack, ItemStack.EMPTY);
            }
            return true;
        } else if (canAdd(other)) {
            int added = add(stardust, other);
            if (added > 0) {
                playInsertSound(player);
                ItemStack toAdd = other.copy();
                toAdd.setCount(added);
                other.shrink(added);
                triggerArtifactsSync(player, ItemStack.EMPTY, toAdd);
            }
            return added > 0;
        }
        return false;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        toggleMethod(MethodName.EQ, stack, slotContext, prevStack);
        
        if (hasItems(stack)) {
            syncAllInternalItems(slotContext.entity(), stack, true);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        toggleMethod(MethodName.UN, stack, slotContext, newStack);
        
        if (hasItems(stack)) {
            syncAllInternalItems(slotContext.entity(), stack, false);
        }
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
    @SuppressWarnings("removal")
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> result = ArrayListMultimap.create();

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
                    Multimap<Holder<Attribute>, AttributeModifier> relicModifiers = curioItem.getAttributeModifiers(slotContext, slotUuid, relic);

                    if (relicModifiers != null) {
                        for (Holder<Attribute> attr : relicModifiers.keySet()) {
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

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