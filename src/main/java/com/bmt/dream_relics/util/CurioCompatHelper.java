package com.bmt.dream_relics.util;

import com.bmt.dream_relics.item.MemoryStardustItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public final class CurioCompatHelper {
    private static final int MAX_RECURSION_DEPTH = 8;
    private static final Map<UUID, CacheEntry> CACHE = new ConcurrentHashMap<>();
    private static final int CACHE_TTL = 20;
    private static final long CLEANUP_INTERVAL_NS = 5_000_000_000L;
    private static final AtomicLong LAST_CLEANUP_NS = new AtomicLong(0);

    private static class CacheEntry {
        final long timestamp;
        final Map<String, Optional<SlotResult>> firstMatchCache;
        final Map<String, List<SlotResult>> allMatchesCache;
        @Nullable
        volatile List<ItemStack> virtualStacksCache;

        CacheEntry(long timestamp) {
            this.timestamp = timestamp;
            this.firstMatchCache = new ConcurrentHashMap<>();
            this.allMatchesCache = new ConcurrentHashMap<>();
        }
    }

    private static void cleanupCacheThrottled(long currentTick) {
        long now = System.nanoTime();
        long last = LAST_CLEANUP_NS.get();
        if (now - last < CLEANUP_INTERVAL_NS) {
            return;
        }
        if (LAST_CLEANUP_NS.compareAndSet(last, now)) {
            CACHE.entrySet().removeIf(entry ->
                    currentTick - entry.getValue().timestamp > CACHE_TTL
            );
        }
    }

    private static CacheEntry getCacheEntry(LivingEntity wearer) {
        UUID uuid = wearer.getUUID();
        long tick = wearer.tickCount;
        cleanupCacheThrottled(tick);

        CacheEntry entry = CACHE.get(uuid);
        if (entry == null || tick - entry.timestamp > CACHE_TTL) {
            entry = new CacheEntry(tick);
            CACHE.put(uuid, entry);
        }
        return entry;
    }

    public static Optional<SlotResult> findFirstStoredCurio(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, Predicate<ItemStack> filter) {
        String cacheKey = "first:" + filter.hashCode();
        CacheEntry cacheEntry = getCacheEntry(wearer);

        if (cacheEntry.firstMatchCache.containsKey(cacheKey)) {
            return cacheEntry.firstMatchCache.get(cacheKey);
        }

        Optional<SlotResult> result = findFirstStoredCurioInternal(wearer, curios, filter);
        cacheEntry.firstMatchCache.put(cacheKey, result);
        return result;
    }

    private static Optional<SlotResult> findFirstStoredCurioInternal(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, Predicate<ItemStack> filter) {
        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();
            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                if (equipped.isEmpty()) continue;

                Optional<ItemStack> found = findFirstStoredMatch(equipped, filter, 0);

                if (found.isPresent()) {
                    return Optional.of(new SlotResult(createParentSlotContext(identifier, wearer, i, stacksHandler), found.get()));
                }
            }
        }
        return Optional.empty();
    }

    public static List<SlotResult> findStoredCurios(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, Predicate<ItemStack> filter) {
        String cacheKey = "all:" + filter.hashCode();
        CacheEntry cacheEntry = getCacheEntry(wearer);

        if (cacheEntry.allMatchesCache.containsKey(cacheKey)) {
            return cacheEntry.allMatchesCache.get(cacheKey);
        }

        List<SlotResult> results = findStoredCuriosInternal(wearer, curios, filter);
        cacheEntry.allMatchesCache.put(cacheKey, results);
        return results;
    }

    private static List<SlotResult> findStoredCuriosInternal(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, Predicate<ItemStack> filter) {
        List<SlotResult> results = new ArrayList<>();

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            String identifier = entry.getKey();
            ICurioStacksHandler stacksHandler = entry.getValue();
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack equipped = stackHandler.getStackInSlot(i);
                if (equipped.isEmpty()) continue;

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

    public static List<SlotResult> findStoredCuriosByIdentifiers(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, String... identifiers) {
        Set<String> idSet = new HashSet<>(Arrays.asList(identifiers));
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
                if (equipped.isEmpty()) continue;

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

    public static Optional<SlotResult> findStoredCurioBySlot(LivingEntity wearer, Map<String, ICurioStacksHandler> curios, String identifier, int index) {
        ICurioStacksHandler stacksHandler = curios.get(identifier);

        if (stacksHandler == null) {
            return Optional.empty();
        }

        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

        if (index < 0 || index >= stackHandler.getSlots()) {
            return Optional.empty();
        }

        ItemStack equipped = stackHandler.getStackInSlot(index);
        if (equipped.isEmpty()) return Optional.empty();

        Optional<ItemStack> found = findFirstStoredMatch(equipped, stack -> true, 0);

        return found.map(itemStack -> new SlotResult(createParentSlotContext(identifier, wearer, index, stacksHandler), itemStack));
    }

    public static List<ItemStack> collectVirtualEquippedStacks(LivingEntity wearer, Map<String, ICurioStacksHandler> curios) {
        if (wearer != null) {
            CacheEntry cacheEntry = getCacheEntry(wearer);
            List<ItemStack> cached = cacheEntry.virtualStacksCache;
            if (cached != null) {
                return cached;
            }
            List<ItemStack> computed = collectVirtualEquippedStacksInternal(curios);
            cacheEntry.virtualStacksCache = computed;
            return computed;
        }
        return collectVirtualEquippedStacksInternal(curios);
    }

    private static List<ItemStack> collectVirtualEquippedStacksInternal(Map<String, ICurioStacksHandler> curios) {
        List<ItemStack> results = new ArrayList<>();

        for (ICurioStacksHandler stacksHandler : curios.values()) {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();

            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    collectAllStoredItems(stack, results, 0);
                }
            }
        }
        return results;
    }

    private static Optional<ItemStack> findFirstStoredMatch(ItemStack stack, Predicate<ItemStack> filter, int depth) {
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

    private static void collectStoredMatches(ItemStack stack, Predicate<ItemStack> filter, List<ItemStack> results, int depth) {
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

    private static void collectAllStoredItems(ItemStack stack, List<ItemStack> results, int depth) {
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

    private static SlotContext createParentSlotContext(String identifier, LivingEntity wearer, int index, ICurioStacksHandler stacksHandler) {
        return new SlotContext(identifier, wearer, index, false, false);
    }
}