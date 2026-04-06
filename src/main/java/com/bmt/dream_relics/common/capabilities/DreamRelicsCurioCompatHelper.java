package com.bmt.dream_relics.common.capabilities;

import com.bmt.dream_relics.item.MemoryStardustItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public final class DreamRelicsCurioCompatHelper {

    private static final int MAX_RECURSION_DEPTH = 8;

    private DreamRelicsCurioCompatHelper() {
    }

    public static Optional<SlotResult> findFirstStoredCurio(LivingEntity wearer,
                                                            Map<String, ICurioStacksHandler> curios,
                                                            Predicate<ItemStack> filter) {
        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();
            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                Optional<ItemStack> found = findFirstStoredMatch(equipped, filter, 0);

                if (found.isPresent()) {
                    return Optional.of(new SlotResult(createParentSlotContext(identifier, wearer, i, stacksHandler), found.get()));
                }
            }
        }
        return Optional.empty();
    }

    public static List<SlotResult> findStoredCurios(LivingEntity wearer,
                                                    Map<String, ICurioStacksHandler> curios,
                                                    Predicate<ItemStack> filter) {
        List<SlotResult> results = new ArrayList<>();

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();
            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                List<ItemStack> matches = new ArrayList<>();
                collectStoredMatches(equipped, filter, matches, 0);

                if (!matches.isEmpty()) {
                    SlotContext slotContext = createParentSlotContext(identifier, wearer, i, stacksHandler);
                    for (ItemStack match : matches) {
                        results.add(new SlotResult(slotContext, match));
                    }
                }
            }
        }
        return results;
    }

    public static List<SlotResult> findStoredCuriosByIdentifiers(LivingEntity wearer,
                                                                 Map<String, ICurioStacksHandler> curios,
                                                                 String... identifiers) {
        Set<String> idSet = new HashSet<>();
        for (String identifier : identifiers) {
            idSet.add(identifier);
        }

        List<SlotResult> results = new ArrayList<>();

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();

            if (!idSet.contains(identifier)) {
                continue;
            }

            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                List<ItemStack> matches = new ArrayList<>();
                collectAllStoredItems(equipped, matches, 0);

                if (!matches.isEmpty()) {
                    SlotContext slotContext = createParentSlotContext(identifier, wearer, i, stacksHandler);
                    for (ItemStack match : matches) {
                        results.add(new SlotResult(slotContext, match));
                    }
                }
            }
        }
        return results;
    }

    public static Optional<SlotResult> findStoredCurioBySlot(LivingEntity wearer,
                                                             Map<String, ICurioStacksHandler> curios,
                                                             String identifier,
                                                             int index) {
        ICurioStacksHandler stacksHandler = curios.get(identifier);

        if (stacksHandler == null) {
            return Optional.empty();
        }

        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (index < 0 || index >= stackHandler.getSlots()) {
            return Optional.empty();
        }

        ItemStack equipped = stackHandler.getStackInSlot(index);
        Optional<ItemStack> found = findFirstStoredMatch(equipped, stack -> true, 0);

        return found.map(itemStack -> new SlotResult(createParentSlotContext(identifier, wearer, index, stacksHandler), itemStack));
    }

    public static Optional<ImmutableTriple<String, Integer, ItemStack>> findFirstStoredTriple(
            LivingEntity wearer,
            Map<String, ICurioStacksHandler> curios,
            Predicate<ItemStack> filter) {
        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();
            IDynamicStackHandler stackHandler = entry.getValue().getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                Optional<ItemStack> found = findFirstStoredMatch(equipped, filter, 0);

                if (found.isPresent()) {
                    return Optional.of(new ImmutableTriple<>(identifier, i, found.get()));
                }
            }
        }
        return Optional.empty();
    }

    public static List<ItemStack> collectVirtualEquippedStacks(Map<String, ICurioStacksHandler> curios) {
        List<ItemStack> results = new ArrayList<>();

        for (ICurioStacksHandler stacksHandler : curios.values()) {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                collectAllStoredItems(stackHandler.getStackInSlot(i), results, 0);
            }
        }
        return results;
    }

    private static Optional<ItemStack> findFirstStoredMatch(ItemStack stack,
                                                            Predicate<ItemStack> filter,
                                                            int depth) {
        if (depth > MAX_RECURSION_DEPTH || stack.isEmpty()) {
            return Optional.empty();
        }

        if (!(stack.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(stack)) {
            return Optional.empty();
        }

        ItemStackHandler handler = MemoryStardustItem.getItems(stack);

        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stored = handler.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            if (filter.test(stored)) {
                return Optional.of(stored);
            }

            Optional<ItemStack> nested = findFirstStoredMatch(stored, filter, depth + 1);
            if (nested.isPresent()) {
                return nested;
            }
        }
        return Optional.empty();
    }

    private static void collectStoredMatches(ItemStack stack,
                                             Predicate<ItemStack> filter,
                                             List<ItemStack> results,
                                             int depth) {
        if (depth > MAX_RECURSION_DEPTH || stack.isEmpty()) {
            return;
        }

        if (!(stack.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(stack)) {
            return;
        }

        ItemStackHandler handler = MemoryStardustItem.getItems(stack);

        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stored = handler.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            if (filter.test(stored)) {
                results.add(stored);
            }

            collectStoredMatches(stored, filter, results, depth + 1);
        }
    }

    private static void collectAllStoredItems(ItemStack stack,
                                              List<ItemStack> results,
                                              int depth) {
        if (depth > MAX_RECURSION_DEPTH || stack.isEmpty()) {
            return;
        }

        if (!(stack.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(stack)) {
            return;
        }

        ItemStackHandler handler = MemoryStardustItem.getItems(stack);

        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stored = handler.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            results.add(stored);
            collectAllStoredItems(stored, results, depth + 1);
        }
    }

    private static SlotContext createParentSlotContext(String identifier,
                                                       LivingEntity wearer,
                                                       int index,
                                                       ICurioStacksHandler stacksHandler) {
        NonNullList<Boolean> renderStates = stacksHandler.getRenders();
        boolean visible = renderStates.size() > index && renderStates.get(index);
        return new SlotContext(identifier, wearer, index, false, visible);
    }
}
