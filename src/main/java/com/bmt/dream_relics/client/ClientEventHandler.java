package com.bmt.dream_relics.client;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.client.inventory.tooltip.MemoryStardustTooltip;
import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import org.lwjgl.glfw.GLFW;

public class ClientEventHandler {
    @EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(MemoryStardustTooltip.Component.class, MemoryStardustTooltip::new);
        }
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void InputEvent(InputEvent.Key event) {
            if (event.getKey() == GLFW.GLFW_KEY_LEFT_ALT) {
                if (event.getAction() == InputConstants.PRESS) {
                    DRClient.IS_ALT_DOWN = true;
                } else if (event.getAction() == InputConstants.RELEASE) {
                    DRClient.IS_ALT_DOWN = false;
                }
            }
        }
    }

    static {
        System.out.print("ClientEventHandler initialized");
    }
}