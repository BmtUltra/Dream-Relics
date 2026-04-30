package com.bmt.dream_relics.item;

import com.bmt.dream_relics.client.DRClient;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SleepingStarSeedItem extends Item {
    private static final int COOLDOWN_TICKS = 1200;
    private static final int SLEEP_DURATION_TICKS = 200;

    public SleepingStarSeedItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        if (target instanceof Player) {
            return InteractionResult.FAIL;
        }

        if (SleepStateManager.isSleeping(target)) {
            return InteractionResult.FAIL;
        }

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        SleepStateManager.setSleeping(target, SLEEP_DURATION_TICKS);

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable("tooltip.dream_relics.sleeping_star_seed")
                .withStyle(ChatFormatting.GRAY));

        if (level != null && level.isClientSide) {
            Player player = DRClient.getLocalPlayer();
            if (player != null && player.getCooldowns().isOnCooldown(this)) {
                float cooldownPercent = player.getCooldowns().getCooldownPercent(this, 0.0F);
                int remainingTicks = (int) (cooldownPercent * COOLDOWN_TICKS);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                if (remainingSeconds > 0) {
                    tooltip.add(Component.translatable("item.dream_relics.sleeping_star_seed.cooldown", remainingSeconds)
                            .withStyle(ChatFormatting.BLUE));
                }
            }
        }
    }
}