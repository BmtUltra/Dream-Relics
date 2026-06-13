package com.bmt.dream_relics.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class SleepStateManager {
    private static final String SLEEP_STATE_KEY = "DreamRelics_SleepState";
    private static final String SLEEP_DURATION_KEY = "SleepDuration";
    private static final String SLEEP_TICKS_KEY = "SleepTicks";

    public static void setSleeping(LivingEntity entity, int duration) {
        CompoundTag data = entity.getPersistentData();
        CompoundTag sleepData = new CompoundTag();
        sleepData.putBoolean(SLEEP_STATE_KEY, true);
        sleepData.putInt(SLEEP_DURATION_KEY, duration);
        sleepData.putInt(SLEEP_TICKS_KEY, 0);
        data.put("DreamRelics", sleepData);

        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }
        entity.setDeltaMovement(0, 0, 0);
    }

    public static boolean isSleeping(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics")) {
            CompoundTag sleepData = data.getCompound("DreamRelics");
            return sleepData.getBoolean(SLEEP_STATE_KEY);
        }
        return false;
    }

    public static void removeSleep(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics")) {
            CompoundTag sleepData = data.getCompound("DreamRelics");
            sleepData.putBoolean(SLEEP_STATE_KEY, false);
            data.put("DreamRelics", sleepData);
        }

        if (entity instanceof Mob mob) {
            mob.setNoAi(false);
        }
    }

    public static void updateSleepState(LivingEntity entity) {
        if (isSleeping(entity)) {
            CompoundTag data = entity.getPersistentData();
            CompoundTag sleepData = data.getCompound("DreamRelics");

            int currentTicks = sleepData.getInt(SLEEP_TICKS_KEY);
            int duration = sleepData.getInt(SLEEP_DURATION_KEY);

            currentTicks++;
            sleepData.putInt(SLEEP_TICKS_KEY, currentTicks);

            if (entity instanceof Mob mob) {
                mob.setNoAi(true);
            }
            entity.setDeltaMovement(0, 0, 0);

            if (entity.level() instanceof ServerLevel serverLevel && currentTicks % 10 == 0) {
                double centerX = entity.getX();
                double centerY = entity.getY() + entity.getBbHeight() * 0.5;
                double centerZ = entity.getZ();

                float width = entity.getBbWidth();
                float height = entity.getBbHeight();
                float innerRange = Math.max(width, height) * 0.4f;
                float outerRange = Math.max(width, height) * 1.2f;

                for (int i = 0; i < 4; i++) {
                    float currentRange;
                    if (i == 0) {
                        currentRange = innerRange;
                    } else {
                        currentRange = outerRange;
                    }

                    double offsetX = (serverLevel.random.nextDouble() - 0.5) * currentRange;
                    double offsetY = (serverLevel.random.nextDouble() - 0.5) * height * 0.8;
                    double offsetZ = (serverLevel.random.nextDouble() - 0.5) * currentRange;

                    double particleX = centerX + offsetX;
                    double particleY = centerY + offsetY;
                    double particleZ = centerZ + offsetZ;

                    if (i % 2 == 0) {
                        serverLevel.sendParticles(ParticleTypes.WAX_ON, particleX, particleY, particleZ,
                                1, 0.15, 0.15, 0.15, 0.03);
                    } else {
                        serverLevel.sendParticles(ParticleTypes.WAX_OFF, particleX, particleY, particleZ,
                                1, 0.15, 0.15, 0.15, 0.03);
                    }
                }
            }
            if (currentTicks >= duration) {
                removeSleep(entity);
            }
        }
    }
}