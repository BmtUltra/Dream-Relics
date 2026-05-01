package com.bmt.dream_relics.init;

import com.bmt.dream_relics.common.capabilities.PlayerData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class DRCapabilities {
    public static Capability<PlayerData> SERVER_SIDE_PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
}
