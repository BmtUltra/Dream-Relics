package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.common.capabilities.PlayerData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DRCapabilities {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DreamRelics.MODID);

    public static final Supplier<AttachmentType<PlayerData>> SERVER_SIDE_PLAYER_DATA =
            ATTACHMENT_TYPES.register(
                    "server_player_data",
                    () -> AttachmentType.builder(PlayerData::new).build()
            );
}