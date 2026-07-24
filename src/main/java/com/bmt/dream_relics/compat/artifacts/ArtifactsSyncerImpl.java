package com.bmt.dream_relics.compat.artifacts;

import artifacts.event.ArtifactHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ArtifactsSyncerImpl implements ArtifactSyncer {
    
    @Override
    public void onItemChanged(LivingEntity entity, ItemStack removedItem, ItemStack addedItem) {
        if (!entity.level().isClientSide()) {
            ArtifactHooks.onItemChanged(entity, removedItem, addedItem);
        }
    }
    
    @Override
    public void refreshTickingAbilities(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            ArtifactHooks.refreshTickingAbilities(entity);
        }
    }
}
