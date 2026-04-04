package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DRCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DreamRelics.MODID);

    public static final RegistryObject<CreativeModeTab> DREAM_RELICS_TAB = CREATIVE_MODE_TABS.register("dream_relics_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> DRItems.MEMORY_STARDUST.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup.dream_relics.dream_relics_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(DRItems.SOUL_MIRROR.get());
                        output.accept(DRItems.DREAM_TOTEM.get());
                        output.accept(DRItems.MOMENT_STONE.get());
                        output.accept(DRItems.ENDLESS_DREAM.get());
                        output.accept(DRItems.YEARS_AMBER.get());
                        output.accept(DRItems.MEMORY_STARDUST.get());
                        output.accept(DRItems.TIME_HOURGLASS.get());
                        output.accept(DRItems.ASTRAL_NECKLACE.get());
                        output.accept(DRItems.TASSEL_RING.get());
                        output.accept(DRItems.OBSERVE_SELF_EYE.get());
                        output.accept(DRItems.DREAM_BALANCE.get());
                        output.accept(DRItems.PAST_RING.get());
                        output.accept(DRItems.ECHO_EARRING.get());
                        output.accept(DRItems.MEMORY_NECKLACE.get());
                        output.accept(DRItems.SLEEPING_STAR_SEED.get());
                        output.accept(DRItems.LIMINAL_KEY.get());
                        output.accept(DRItems.HEART_VOICE_PENDANT.get());
                        output.accept(DRItems.AWAKEN_DREAM_BRACELET.get());
                        output.accept(DRItems.MIST_VEIL_RING.get());
                        output.accept(DRItems.NIGHTMARE_BOOK.get());
                        output.accept(DRItems.VOID_NECKLACE.get());
                        output.accept(DRItems.DARK_WHISPER_RING.get());
                        output.accept(DRItems.DARK_WHISPER_DAGGER.get());
                    }).build());
}