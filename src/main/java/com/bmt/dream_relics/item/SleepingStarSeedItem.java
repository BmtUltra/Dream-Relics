package com.bmt.dream_relics.item;

import com.bmt.dream_relics.client.DRClient;
import com.bmt.dream_relics.config.CommonConfig;
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

import java.util.List;

public class SleepingStarSeedItem extends Item {

    public SleepingStarSeedItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        if (target instanceof Player) {
            return InteractionResult.FAIL;
        }

        if (SleepStateManager.isSleeping(target)) {
            return InteractionResult.FAIL;
        }

        player.getCooldowns().addCooldown(this, CommonConfig.sleepingStarSeedCooldown);
        SleepStateManager.setSleeping(target, CommonConfig.sleepingStarSeedSleepDuration);

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipComponents, flag);

        tooltipComponents.add(Component.translatable("tooltip.dream_relics.sleeping_star_seed")
                .withStyle(ChatFormatting.GRAY));

        Level level = context.level();
        if (level != null && level.isClientSide) {
            Player player = DRClient.getLocalPlayer();
            if (player != null && player.getCooldowns().isOnCooldown(this)) {
                float cooldownPercent = player.getCooldowns().getCooldownPercent(this, 0.0F);
                int remainingTicks = (int) (cooldownPercent * CommonConfig.sleepingStarSeedCooldown);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                if (remainingSeconds > 0) {
                    tooltipComponents.add(Component.translatable("item.dream_relics.sleeping_star_seed.cooldown", remainingSeconds)
                            .withStyle(ChatFormatting.BLUE));
                }
            }
        }
    }
}