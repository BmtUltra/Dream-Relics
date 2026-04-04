package com.bmt.dream_relics.client;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.item.DreamTotem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DreamTotemTooltipEvent {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof DreamTotem) {
            int essence = DreamTotem.getDreamEssence(stack);
            int maxEssence = 4;

            addRainbowEssenceTooltip(event.getToolTip(), essence, maxEssence);
        }
    }

    private static void addRainbowEssenceTooltip(List<Component> tooltip, int essence, int maxEssence) {
        long time = System.currentTimeMillis();
        MutableComponent coloredText = Component.empty();

        String text = Component.translatable("item.dream_relics.tooltip.dream_totem.essence",
                essence, maxEssence).getString();
        double characterSpacing = 0.25;
        double preciseTime = (double) time / 250;

        double bluePhase = 0.0;
        double purplePhase = Math.PI * 2.0 / 3.0;
        double pinkPhase = Math.PI * 4.0 / 3.0;
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            double characterOffset = (double) i / text.length() * characterSpacing;
            double timeOffset = preciseTime + characterOffset * Math.PI * 2;
            
            double blue = (Math.sin(timeOffset + bluePhase) * 0.35 + 0.65) * 100 + 155;
            double red = (Math.sin(timeOffset + pinkPhase) * 0.25 + 0.55) * 80 + 140;
            double green = (Math.sin(timeOffset + purplePhase) * 0.3 + 0.5) * 70 + 110;
            
            double brightnessModulation = 0.9 + 0.1 * Math.sin(preciseTime + i * 0.15);
            red = Math.min(220, red * brightnessModulation);
            green = Math.min(180, green * brightnessModulation);
            blue = Math.min(255, blue * brightnessModulation);
            
            int finalRed = Math.max(140, Math.min(220, (int) Math.round(red)));
            int finalGreen = Math.max(110, Math.min(180, (int) Math.round(green)));
            int finalBlue = Math.max(155, Math.min(255, (int) Math.round(blue)));
            
            int color = (finalRed << 16) | (finalGreen << 8) | finalBlue;
            
            coloredText.append(Component.literal(String.valueOf(c))
                    .withStyle(Style.EMPTY.withColor(color)));
        }

        int insertIndex = 1;
        if (insertIndex < tooltip.size()) {
            tooltip.add(insertIndex, coloredText.withStyle(Style.EMPTY.withBold(true)));
        } else {
            tooltip.add(coloredText.withStyle(Style.EMPTY.withBold(true)));
        }
    }
}