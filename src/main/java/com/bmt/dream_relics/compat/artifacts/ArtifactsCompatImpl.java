package com.bmt.dream_relics.compat.artifacts;

import artifacts.equipment.EquipmentSlotProvider;
import com.bmt.dream_relics.api.ArtifactSyncer;
import com.bmt.dream_relics.compat.curios.CuriosCompat;
import com.bmt.dream_relics.item.MemoryStardustItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ArtifactsCompatImpl {

    public static class DreamRelicsSlotProvider implements EquipmentSlotProvider {
        private static final Map<UUID, CachedEquipment> EQUIPMENT_CACHE = new ConcurrentHashMap<>();
        private static final int CACHE_TTL = 10;

        private static class CachedEquipment {
            final long timestamp;
            final List<ItemStack> equipment;

            CachedEquipment(long timestamp, List<ItemStack> equipment) {
                this.timestamp = timestamp;
                this.equipment = equipment;
            }
        }

        @Override
        public <T> T reduceEquipment(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
            List<ItemStack> equipment = getCachedEquipment(entity);

            for (ItemStack stack : equipment) {
                init = f.apply(stack, init);
            }
            return init;
        }

        private List<ItemStack> getCachedEquipment(LivingEntity entity) {
            UUID uuid = entity.getUUID();
            CachedEquipment cached = EQUIPMENT_CACHE.get(uuid);

            if (cached != null && entity.tickCount - cached.timestamp <= CACHE_TTL) {
                return cached.equipment;
            }

            List<ItemStack> equipment = buildEquipmentList(entity);
            EQUIPMENT_CACHE.put(uuid, new CachedEquipment(entity.tickCount, equipment));

            cleanupCacheDelayed(entity.tickCount);
            return equipment;
        }

        private void cleanupCacheDelayed(long currentTick) {
            EQUIPMENT_CACHE.entrySet().removeIf(entry -> currentTick - entry.getValue().timestamp > CACHE_TTL);
        }

        private List<ItemStack> buildEquipmentList(LivingEntity entity) {
            List<ItemStack> result = new ArrayList<>();
            var curios = CuriosCompat.getCurios(entity);

            if (curios == null || curios.isEmpty()) {
                return result;
            }

            for (var entry : curios.entrySet()) {
                var stacksHandler = entry.getValue();
                if (stacksHandler == null) continue;

                var stackHandler = stacksHandler.getStacks();
                if (stackHandler == null) continue;

                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack equipped = stackHandler.getStackInSlot(i);
                    if (equipped.isEmpty()) continue;

                    boolean isMemoryStardust = equipped.getItem() instanceof MemoryStardustItem;

                    if (isMemoryStardust) {
                        if (MemoryStardustItem.hasItems(equipped)) {
                            var items = MemoryStardustItem.getItems(equipped);

                            for (int j = 0; j < items.getSlots(); j++) {
                                ItemStack relic = items.getStackInSlot(j);
                                if (!relic.isEmpty()) {
                                    result.add(relic);
                                }
                            }
                        }
                    } else {
                        result.add(equipped);
                    }
                }
            }
            return result;
        }

        @Override
        public boolean tryEquipItem(LivingEntity entity, ItemStack stack) {
            return false;
        }

        public static void invalidateCache() {
            EQUIPMENT_CACHE.clear();
        }
    }

    public static class ArtifactsCompatEvents {
        private final ArtifactSyncer syncer;
        private long lastRefreshTick = 0;
        private static final int REFRESH_INTERVAL = 20;

        public ArtifactsCompatEvents(ArtifactSyncer syncer) {
            this.syncer = syncer;
        }

        @SubscribeEvent
        public void onEntityJoin(EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof Player player) {
                long currentTick = player.tickCount;
                if (currentTick - lastRefreshTick >= REFRESH_INTERVAL) {
                    syncer.refreshTickingAbilities(player);
                    lastRefreshTick = currentTick;
                    DreamRelicsSlotProvider.invalidateCache();
                }
            }
        }
    }
}