package com.bmt.dream_relics.client.event;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
public class GlowEffectHandler {
    private static final int RANGE = 16;
    private static final Set<LivingEntity> glowingEntities = new HashSet<>();
    private static boolean effectActive = false;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) {
            return;
        }

        boolean hasObserveSelfEye = CuriosApi.getCuriosHelper().findCurios(player, DRItems.OBSERVE_SELF_EYE.get()).stream()
                .anyMatch(slotResult -> !slotResult.stack().isEmpty());

        if (hasObserveSelfEye) {
            effectActive = true;
            updateGlowingEntities(player);
        } else {
            effectActive = false;
            glowingEntities.clear();
        }
    }

    private static void updateGlowingEntities(LocalPlayer player) {
        Set<LivingEntity> newGlowingEntities = new HashSet<>();

        AABB area = new AABB(
                player.getX() - RANGE, player.getY() - RANGE, player.getZ() - RANGE,
                player.getX() + RANGE, player.getY() + RANGE, player.getZ() + RANGE
        );

        for (Entity entity : player.level().getEntities(player, area)) {
            if (entity instanceof LivingEntity livingEntity && entity != player) {
                newGlowingEntities.add(livingEntity);
            }
        }
        glowingEntities.clear();
        glowingEntities.addAll(newGlowingEntities);
    }

    public static boolean isEffectActive() {
        return effectActive;
    }

    public static Set<LivingEntity> getGlowingEntities() {
        return glowingEntities;
    }

    public static boolean shouldEntityGlow(LivingEntity entity) {
        return glowingEntities.contains(entity);
    }
}