package com.bmt.dream_relics;

import com.bmt.dream_relics.config.MainConfig;
import com.bmt.dream_relics.init.DRCreativeTabs;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DreamRelics.MODID)
public class DreamRelics {
    public static final String MODID = "dream_relics";

    public DreamRelics(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        DRItems.ITEMS.register(modEventBus);
        DRCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, MainConfig.SPEC);
    }

    public static ResourceLocation id(String s) {
        return ResourceLocation.fromNamespaceAndPath(MODID, s);
    }
}