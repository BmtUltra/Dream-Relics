package com.bmt.dream_relics.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DRUtil {
    public static class Items {
        public static final TagKey<Item> CANT_STORED_IN_MEMORY_STARDUST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("dream_relics", "memory_stardust_blacklist"));
    }
}