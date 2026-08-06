package com.bmt.dream_relics.compat.jade;

import com.bmt.dream_relics.DreamRelics;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class DRJadePlugin implements IWailaPlugin {
    public static final ResourceLocation MUTE_STATE = ResourceLocation.fromNamespaceAndPath(DreamRelics.MODID, "mute_state");
    public static final ResourceLocation SLEEP_STATE = ResourceLocation.fromNamespaceAndPath(DreamRelics.MODID, "sleep_state");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(MuteStateProvider.INSTANCE, net.minecraft.world.entity.LivingEntity.class);
        registration.registerEntityDataProvider(SleepStateProvider.INSTANCE, net.minecraft.world.entity.LivingEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(MuteStateProvider.INSTANCE, net.minecraft.world.entity.LivingEntity.class);
        registration.registerEntityComponent(SleepStateProvider.INSTANCE, net.minecraft.world.entity.LivingEntity.class);
    }
}