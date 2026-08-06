package com.bmt.dream_relics.compat.jade;

import com.bmt.dream_relics.util.MuteStateManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum MuteStateProvider implements IEntityComponentProvider, StreamServerDataProvider<EntityAccessor, Integer> {
    INSTANCE;

    private static final StreamCodec<RegistryFriendlyByteBuf, Integer> STREAM_CODEC =
            ByteBufCodecs.VAR_INT.cast();

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        Integer remainingTicks = decodeFromData(accessor).orElse(null);
        if (remainingTicks != null && remainingTicks > 0) {
            String duration = StringUtil.formatTickDuration(remainingTicks, accessor.tickRate());
            Component name = Component.translatable("jade.dream_relics.mute_state_name");
            Component s = Component.translatable("jade.potion", name, duration);
            tooltip.add(IThemeHelper.get().danger(s));
        }
    }

    @Override
    public boolean shouldRequestData(EntityAccessor accessor) {
        return accessor.getEntity() instanceof LivingEntity;
    }

    @Override
    public @Nullable Integer streamData(EntityAccessor accessor) {
        if (accessor.getEntity() instanceof LivingEntity living && MuteStateManager.isMuted(living)) {
            var persistentData = living.getPersistentData();
            if (persistentData.contains("DreamRelics_Mute")) {
                var muteData = persistentData.getCompound("DreamRelics_Mute");
                int currentTicks = muteData.getInt("MuteTicks");
                int duration = muteData.getInt("MuteDuration");
                int remaining = duration - currentTicks;
                if (remaining > 0) {
                    return remaining;
                }
            }
        }
        return null;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Integer> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
        return DRJadePlugin.MUTE_STATE;
    }
}