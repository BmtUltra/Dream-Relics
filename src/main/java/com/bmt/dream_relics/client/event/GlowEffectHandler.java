package com.bmt.dream_relics.client.event;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.client.DRClient;
import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
public class GlowEffectHandler {
    private static final Set<LivingEntity> glowingEntities = new HashSet<>();
    private static boolean effectActive = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Player player = DRClient.getLocalPlayer();

        if (player == null) {
            return;
        }

        boolean hasObserveSelfEye = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.findCurios(DRItems.OBSERVE_SELF_EYE.get()).stream()
                        .anyMatch(slotResult -> !slotResult.stack().isEmpty()))
                .orElse(false);

        if (hasObserveSelfEye) {
            effectActive = true;
            updateGlowingEntities(player);
        } else {
            effectActive = false;
            glowingEntities.clear();
        }
    }

    private static void updateGlowingEntities(Player player) {
        Set<LivingEntity> newGlowingEntities = new HashSet<>();

        int range = CommonConfig.observeSelfEyeRange;

        AABB area = new AABB(
                player.getX() - range, player.getY() - range, player.getZ() - range,
                player.getX() + range, player.getY() + range, player.getZ() + range
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

    static {
        System.out.print("GlowEffectHandler initialized");
    }
}