package com.bmt.dream_relics.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class MuteStateManager {
    private static final String MUTE_STATE_KEY = "DreamRelics_MuteState";
    private static final String MUTE_DURATION_KEY = "MuteDuration";
    private static final String MUTE_TICKS_KEY = "MuteTicks";
    private static final float DAMAGE_REDUCTION = 0.4f;

    public static final int DEFAULT_MUTE_DURATION = 120;

    public static void setMuted(LivingEntity entity) {
        setMuted(entity, DEFAULT_MUTE_DURATION);
    }

    public static void setMuted(LivingEntity entity, int durationTicks) {
        CompoundTag data = entity.getPersistentData();
        CompoundTag muteData = new CompoundTag();
        muteData.putBoolean(MUTE_STATE_KEY, true);
        muteData.putInt(MUTE_DURATION_KEY, durationTicks);
        muteData.putInt(MUTE_TICKS_KEY, 0);
        data.put("DreamRelics_Mute", muteData);
    }

    public static boolean isMuted(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics_Mute")) {
            CompoundTag muteData = data.getCompound("DreamRelics_Mute");
            return muteData.getBoolean(MUTE_STATE_KEY);
        }
        return false;
    }

    public static void removeMute(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics_Mute")) {
            CompoundTag muteData = data.getCompound("DreamRelics_Mute");
            muteData.putBoolean(MUTE_STATE_KEY, false);
            data.put("DreamRelics_Mute", muteData);
        }
    }

    public static int getRemainingTicks(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics_Mute")) {
            CompoundTag muteData = data.getCompound("DreamRelics_Mute");
            if (muteData.getBoolean(MUTE_STATE_KEY)) {
                int duration = muteData.getInt(MUTE_DURATION_KEY);
                int currentTicks = muteData.getInt(MUTE_TICKS_KEY);
                return Math.max(0, duration - currentTicks);
            }
        }
        return 0;
    }

    public static float getDamageMultiplier(LivingEntity entity) {
        if (isMuted(entity)) {
            return DAMAGE_REDUCTION;
        }
        return 1.0f;
    }

    public static void updateMuteState(LivingEntity entity) {
        if (isMuted(entity)) {
            CompoundTag data = entity.getPersistentData();
            CompoundTag muteData = data.getCompound("DreamRelics_Mute");

            int currentTicks = muteData.getInt(MUTE_TICKS_KEY);
            int duration = muteData.getInt(MUTE_DURATION_KEY);

            currentTicks++;
            muteData.putInt(MUTE_TICKS_KEY, currentTicks);

            if (entity.level() instanceof ServerLevel serverLevel && currentTicks % 5 == 0) {
                double centerX = entity.getX();
                double centerY = entity.getY() + entity.getBbHeight() * 0.5;
                double centerZ = entity.getZ();

                float width = entity.getBbWidth();
                float height = entity.getBbHeight();
                float range = Math.max(width, height) * 0.8f;

                for (int i = 0; i < 3; i++) {
                    double offsetX = (serverLevel.random.nextDouble() - 0.5) * range;
                    double offsetY = (serverLevel.random.nextDouble() - 0.5) * height * 0.6;
                    double offsetZ = (serverLevel.random.nextDouble() - 0.5) * range;

                    double particleX = centerX + offsetX;
                    double particleY = centerY + offsetY;
                    double particleZ = centerZ + offsetZ;

                    serverLevel.sendParticles(ParticleTypes.SMOKE, particleX, particleY, particleZ,
                            1, 0.1, 0.1, 0.1, 0.01);
                }
            }
            if (currentTicks >= duration) {
                removeMute(entity);
            }
        }
    }

    public static int secondsToTicks(float seconds) {
        return (int) (seconds * 20);
    }
}