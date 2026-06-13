package com.bmt.dream_relics.integration;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ArtifactSyncer {
    void onItemChanged(LivingEntity entity, ItemStack removedItem, ItemStack addedItem);
    void refreshTickingAbilities(LivingEntity entity);
}
