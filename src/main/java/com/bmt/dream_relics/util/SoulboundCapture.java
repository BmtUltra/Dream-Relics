package com.bmt.dream_relics.util;

import com.bmt.dream_relics.init.DRDataComponents;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SoulboundCapture {
    private static final Map<UUID, List<ItemStack>> CAPTURED = new ConcurrentHashMap<>();
    private static final Map<UUID, List<CurioEntry>> CAPTURED_CURIOS = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> CAPTURED_TIME = new ConcurrentHashMap<>();
    private static final long EXPIRE_MILLIS = 5 * 60 * 1000L;

    public record CurioEntry(String identifier, int index, ItemStack stack, boolean cosmetic) {}

    public static boolean isSoulbound(ItemStack stack) {
        return !stack.isEmpty() && stack.has(DRDataComponents.SOULBOUND.get());
    }

    public static void capture(UUID playerId, List<ItemStack> stacks) {
        if (stacks.isEmpty()) {
            return;
        }
        cleanup();
        CAPTURED.computeIfAbsent(playerId, id -> new ArrayList<>()).addAll(stacks);
        CAPTURED_TIME.put(playerId, System.currentTimeMillis());
    }

    public static void captureCurio(UUID playerId, CurioEntry entry) {
        cleanup();
        CAPTURED_CURIOS.computeIfAbsent(playerId, id -> new ArrayList<>()).add(entry);
        CAPTURED_TIME.put(playerId, System.currentTimeMillis());
    }

    public static List<ItemStack> take(UUID playerId) {
        CAPTURED_TIME.remove(playerId);
        return CAPTURED.remove(playerId);
    }

    public static List<CurioEntry> takeCurios(UUID playerId) {
        return CAPTURED_CURIOS.remove(playerId);
    }

    private static void cleanup() {
        long now = System.currentTimeMillis();
        CAPTURED_TIME.forEach((id, time) -> {
            if (now - time > EXPIRE_MILLIS) {
                CAPTURED.remove(id);
                CAPTURED_CURIOS.remove(id);
                CAPTURED_TIME.remove(id);
            }
        });
    }
}
