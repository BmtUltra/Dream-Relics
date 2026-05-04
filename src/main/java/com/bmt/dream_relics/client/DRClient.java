package com.bmt.dream_relics.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class DRClient {
    public static boolean IS_ALT_DOWN = false;

    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
    public static void init() {
    }
}
