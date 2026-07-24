package com.bmt.dream_relics.client;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.client.inventory.tooltip.MemoryStardustTooltip;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
public class DRClient {
    public static boolean IS_ALT_DOWN = false;

    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MemoryStardustTooltip.Component.class, MemoryStardustTooltip::new);
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void onInputKey(InputEvent.Key event) {
            if (event.getKey() == GLFW.GLFW_KEY_LEFT_ALT) {
                if (event.getAction() == InputConstants.PRESS) {
                    DRClient.IS_ALT_DOWN = true;
                } else if (event.getAction() == InputConstants.RELEASE) {
                    DRClient.IS_ALT_DOWN = false;
                }
            }
        }
    }

    public static void init() {
        System.out.print("DRClient initialized");
    }
}