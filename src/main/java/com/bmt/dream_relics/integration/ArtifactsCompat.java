package com.bmt.dream_relics.integration;

import artifacts.equipment.EquipmentSlotManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;

public class ArtifactsCompat {
    
    public static final String ARTIFACTS_MOD_ID = "artifacts";
    private static ArtifactsCompatProvider provider;

    public static boolean isArtifactsLoaded() {
        return ModList.get().isLoaded(ARTIFACTS_MOD_ID);
    }

    public static void init() {
        if (provider != null) {
            return;
        }
        provider = new ArtifactsCompatProviderImpl();
        provider.init();
    }

    public static void syncItemChange(LivingEntity entity, ItemStack removedItem, ItemStack addedItem) {
        if (provider != null) {
            provider.syncItemChange(entity, removedItem, addedItem);
        }
    }

    private interface ArtifactsCompatProvider {
        void init();
        void syncItemChange(LivingEntity entity, ItemStack removedItem, ItemStack addedItem);
    }

    private static class ArtifactsCompatProviderImpl implements ArtifactsCompatProvider {
        private ArtifactSyncer syncer;

        @Override
        public void init() {
            syncer = new ArtifactsSyncerImpl();
            EquipmentSlotManager.register(new ArtifactsCompatImpl.DreamRelicsSlotProvider());
            NeoForge.EVENT_BUS.register(new ArtifactsCompatImpl.ArtifactsCompatEvents(syncer));
        }

        @Override
        public void syncItemChange(LivingEntity entity, ItemStack removedItem, ItemStack addedItem) {
            if (syncer != null) {
                try {
                    if (!removedItem.isEmpty()) {
                        syncer.onItemChanged(entity, removedItem, ItemStack.EMPTY);
                    }
                    if (!addedItem.isEmpty()) {
                        syncer.onItemChanged(entity, ItemStack.EMPTY, addedItem);
                    }
                    if (removedItem.isEmpty() && addedItem.isEmpty()) {
                        syncer.refreshTickingAbilities(entity);
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }
}
