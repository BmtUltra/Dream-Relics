package com.bmt.dream_relics;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRCapabilities;
import com.bmt.dream_relics.init.DRCreativeTabs;
import com.bmt.dream_relics.init.DRDataComponents;
import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.integration.ArtifactsCompat;
import com.bmt.dream_relics.loot.DRGlobalLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(DreamRelics.MODID)
public class DreamRelics {
    public static final String MODID = "dream_relics";

    public DreamRelics(IEventBus modEventBus, ModContainer modContainer) {
        DRItems.ITEMS.register(modEventBus);
        DRCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DRDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        DRGlobalLootModifiers.register(modEventBus);
        DRCapabilities.ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.register(CommonConfig.class);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "dream_relics/dream_relics-common.toml");
        modContainer.registerConfig(ModConfig.Type.COMMON, LootConfig.SPEC, "dream_relics/dream_relics-loot.toml");

        if (ModList.get().isLoaded("artifacts")) {
            ArtifactsCompat.init();
        }
    }
}