package com.bmt.dream_relics.common;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.common.capabilities.PlayerData;
import com.bmt.dream_relics.common.capabilities.YearsAmberItemHandler;
import com.bmt.dream_relics.item.DreamTotem;
import com.bmt.dream_relics.item.FlawlessGem;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.init.DRCapabilities;
import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.util.DRUtil;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void PlayerXpEvent(PlayerXpEvent.PickupXp event) {
            if (DRUtil.isEquippedNightmareBook(event.getEntity())) {
                event.getOrb().value = (int) (0.5 * event.getOrb().value);
            }
        }

        @SubscribeEvent
        public static void HarvestCheck(PlayerEvent.HarvestCheck event) {
            if (!event.canHarvest()) {
                if (YearsAmber.findBestCorrectTool(event.getEntity(), event.getEntity().getMainHandItem(), event.getTargetBlock()) != null) {
                    event.setCanHarvest(true);
                }
            }
        }

        @SubscribeEvent
        public static void LivingHurtEvent(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.NIGHTMARE_BOOK.get())) {
                        event.setAmount(event.getAmount() * 0.5F);
                    }
                });
            }

            if (event.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.HEART_VOICE_PENDANT.get())) {
                        float maxHealth = player.getMaxHealth();
                        float maxAllowedDamage = maxHealth * 0.25f;

                        if (event.getAmount() > maxAllowedDamage) {
                            event.setAmount(maxAllowedDamage);
                        }
                    }
                });
            }

            if (event.getSource().getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.ENDLESS_DREAM.get())) {
                        if (event.getEntity() instanceof LivingEntity) {
                            LivingEntity attackedEntity = event.getEntity();
                            if (!(attackedEntity instanceof Player)) {
                                float currentHealth = attackedEntity.getHealth();
                                float maxHealth = attackedEntity.getMaxHealth();
                                float healthPercentage = (currentHealth / maxHealth) * 100;

                                if (healthPercentage > 80.0f) {
                                    SleepStateManager.setSleeping(attackedEntity, 60);
                                }
                            }
                        }
                    }
                });
            }

            if (event.getSource().getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.TASSEL_RING.get())) {
                        if (event.getEntity() instanceof LivingEntity) {
                            float damageMultiplier = getDamageMultiplier(event);

                            event.setAmount(event.getAmount() * damageMultiplier);
                        }
                    }
                });
            }
        }

        private static float getDamageMultiplier(LivingHurtEvent event) {
            LivingEntity target = event.getEntity();
            float currentHealth = target.getHealth();
            float maxHealth = target.getMaxHealth();
            float healthPercentage = (currentHealth / maxHealth) * 100;

            return 1.0f + (healthPercentage / 100.0f) * 0.5f;
        }

        @SubscribeEvent
        public static void LivingAttackEvent(LivingAttackEvent event) {
            if (event.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.VOID_NECKLACE.get())) {
                        if (event.getSource().getEntity() == null) {
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }

        @SubscribeEvent
        public static void LivingTickEvent(LivingEvent.LivingTickEvent event) {
            LivingEntity entity = event.getEntity();
            SleepStateManager.updateSleepState(entity);
        }

        @SubscribeEvent
        public static void PlayerWakeUpEvent(PlayerWakeUpEvent event) {
            Player player = event.getEntity();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                List<SlotResult> results = iCuriosItemHandler.findCurios(DRItems.DREAM_TOTEM.get());
                if (!results.isEmpty()) {
                    ItemStack totem = results.get(0).stack();
                    DreamTotem.addDreamEssence(totem);
                }
            });

            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() == DRItems.DREAM_TOTEM.get()) {
                    DreamTotem.addDreamEssence(stack);
                }
            }
        }

        @SubscribeEvent
        public static void LivingDeathEvent(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player) {
                boolean prevented = DreamTotem.tryAutoActivate(player, player.level());
                if (prevented) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void MobEffectEvent$Add(MobEffectEvent.Added event) {
            if (event.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.NIGHTMARE_BOOK.get())) {
                        @NotNull MobEffectInstance effectInstance = event.getEffectInstance();
                        effectInstance.update(new MobEffectInstance(
                                effectInstance.getEffect(),
                                effectInstance.getDuration() * 2,
                                effectInstance.getAmplifier(),
                                effectInstance.isAmbient(),
                                effectInstance.isVisible(),
                                effectInstance.showIcon()
                        ));
                    }
                });
            }
        }

        @SubscribeEvent
        public static void AnvilUpdateEvent(AnvilUpdateEvent event) {
            Player player = event.getPlayer();

            if (DRUtil.isEquippedNightmareBook(player)) {
                event.setCost(event.getCost() * 2);
            }

            ItemStack leftItem = event.getLeft();
            ItemStack rightItem = event.getRight();

            if (!rightItem.isEmpty() && rightItem.getItem() == DRItems.FLAWLESS_GEM.get()) {
                if (!leftItem.isEmpty() && leftItem.isDamageableItem()) {
                    ItemStack result = leftItem.copy();
                    boolean modified = false;

                    if (FlawlessGem.hasNegativeEnchantments(result)) {
                        result = FlawlessGem.removeNegativeEnchantments(result);
                        modified = true;
                    }

                    if (FlawlessGem.needsRepair(result)) {
                        result = FlawlessGem.repairItem(result);
                        modified = true;
                    }

                    if (modified) {
                        event.setOutput(result);
                        event.setCost(10);
                        event.setMaterialCost(1);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (!player.isLocalPlayer()) {
                List<? extends Player> players = player.level().players();
                for (Player otherPlayer : players) {
                    if (player.distanceToSqr(otherPlayer) <= 25) {
                        player.getCapability(DRCapabilities.SERVER_SIDE_PLAYER_DATA).ifPresent(playerData -> {

                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void AttachItemStackCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event) {
            ItemStack itemStack = event.getObject();
            if (itemStack.is(DRItems.YEARS_AMBER.get())) {
                event.addCapability(DreamRelics.id("years_amber_item_handler"), new YearsAmberItemHandler(4));
            }
        }

        @SubscribeEvent
        public static void AttachPlayerCapabilitiesEvent(AttachCapabilitiesEvent<Player> event) {
            event.addCapability(DreamRelics.id("player_data"), new PlayerData());
        }
    }


    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
            event.register(YearsAmberItemHandler.class);
            event.register(PlayerData.class);
        }
    }
}