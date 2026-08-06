package com.bmt.dream_relics.compat.artifacts;

import artifacts.equipment.EquipmentSlotProvider;
import com.bmt.dream_relics.compat.curios.CuriosCompat;
import com.bmt.dream_relics.item.MemoryStardustItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.function.BiFunction;

public class ArtifactsCompatImpl {
    public static class DreamRelicsSlotProvider implements EquipmentSlotProvider {
        
        @Override
        public <T> T reduceEquipment(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
            var curios = CuriosCompat.getCurios(entity);

            for (var entry : curios.entrySet()) {
                var stacksHandler = entry.getValue();
                var stackHandler = stacksHandler.getStacks();
                
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack equipped = stackHandler.getStackInSlot(i);
                    if (equipped.isEmpty()) {
                        continue;
                    }

                    boolean isMemoryStardust = equipped.getItem() instanceof MemoryStardustItem;
                    
                    if (isMemoryStardust) {
                        if (MemoryStardustItem.hasItems(equipped)) {
                            var items = MemoryStardustItem.getItems(equipped);
                            
                            for (int j = 0; j < items.getSlots(); j++) {
                                ItemStack relic = items.getStackInSlot(j);
                                if (relic.isEmpty()) {
                                    continue;
                                }
                                init = f.apply(relic, init);
                            }
                        }
                    } else {
                        init = f.apply(equipped, init);
                    }
                }
            }
            return init;
        }
        
        @Override
        public boolean tryEquipItem(LivingEntity entity, ItemStack stack) {
            return false;
        }
    }

    public static class ArtifactsCompatEvents {
        private final ArtifactSyncer syncer;

        public ArtifactsCompatEvents(ArtifactSyncer syncer) {
            this.syncer = syncer;
        }

        @SubscribeEvent
        public void onEntityJoin(EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof Player player) {
                syncer.refreshTickingAbilities(player);
            }
        }
    }
}
