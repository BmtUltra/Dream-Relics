package com.bmt.dream_relics.item;

import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DreamRelicItem extends DreamRelicItemBase implements ICurioItem {
    public DreamRelicItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }
}