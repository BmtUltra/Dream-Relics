package com.bmt.dream_relics.util;

import net.minecraft.nbt.CompoundTag;
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

    public static int getRemainingTicks(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (data.contains("DreamRelics")) {
            CompoundTag sleepData = data.getCompound("DreamRelics");
            if (sleepData.getBoolean(SLEEP_STATE_KEY)) {
                int duration = sleepData.getInt(SLEEP_DURATION_KEY);
                int currentTicks = sleepData.getInt(SLEEP_TICKS_KEY);
                return Math.max(0, duration - currentTicks);
            }
        }
        return 0;
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

            if (currentTicks >= duration) {
                removeSleep(entity);
            }
        }
    }
}