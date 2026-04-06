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
import com.bmt.dream_relics.util.FlowStateManager;
import com.bmt.dream_relics.util.MuteStateManager;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
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
                    if (iCuriosItemHandler.isEquipped(DRItems.ECHO_EARRING.get())) {
                        if (player.getRandom().nextFloat() < 0.30f) {
                            LivingEntity target = event.getEntity();
                            float baseDamage = event.getAmount();
                            float extraDamage = baseDamage * 0.40f;

                            triggerSonicWave(player, target, extraDamage);
                        }
                    }
                });
            }

            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                if (MuteStateManager.isMuted(attacker)) {
                    float damageMultiplier = MuteStateManager.getDamageMultiplier(attacker);
                    event.setAmount(event.getAmount() * damageMultiplier);
                }
            }

            if (event.getSource().getEntity() instanceof Player player) {
                boolean hasDagger = player.getMainHandItem().getItem() == DRItems.DARK_WHISPER_DAGGER.get() ||
                        player.getOffhandItem().getItem() == DRItems.DARK_WHISPER_DAGGER.get();
                if (hasDagger) {
                    MuteStateManager.setMuted(event.getEntity());
                }
            }

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
                                if (player.getRandom().nextFloat() < 0.10f) {
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

            LivingEntity target = event.getEntity();

            if (target instanceof net.minecraft.world.entity.TamableAnimal tamable) {
                LivingEntity owner = tamable.getOwner();
                if (owner instanceof Player player) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_CROWN.get())) {
                            float reducedDamage = event.getAmount() * 0.6f;
                            event.setAmount(reducedDamage);
                        }
                    });
                }
            }

            if (event.getSource().getEntity() instanceof net.minecraft.world.entity.TamableAnimal tamableAttacker) {
                LivingEntity owner = tamableAttacker.getOwner();
                if (owner instanceof Player player) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_CROWN.get())) {
                            float boostedDamage = event.getAmount() * 1.2f;
                            event.setAmount(boostedDamage);
                        }
                    });
                }
            }
        }

        private static void triggerSonicWave(Player player, LivingEntity target, float extraDamage) {
            if (player.level().isClientSide) {
                return;
            }

            float range = 8.0f;
            float width = 0.4f;

            Vec3 start = player.getEyePosition();
            Vec3 end = start.add(player.getForward().scale(range));

            AABB boundingBox = player.getBoundingBox().expandTowards(end.subtract(start));
            List<LivingEntity> entities = player.level().getEntitiesOfClass(
                    LivingEntity.class, boundingBox,
                    entity -> entity != player && entity.isAlive()
            );

            player.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    net.minecraft.sounds.SoundEvents.ELDER_GUARDIAN_CURSE,
                    net.minecraft.sounds.SoundSource.PLAYERS, 0.8f, 1.2f);

            for (LivingEntity entity : entities) {
                net.minecraft.world.phys.HitResult hit = checkEntityIntersecting(entity, start, end, width);
                if (hit.getType() != net.minecraft.world.phys.HitResult.Type.MISS) {
                    entity.hurt(player.damageSources().sonicBoom(player), extraDamage);
                }
            }

            ServerLevel serverLevel = (ServerLevel) player.level();
            Vec3 direction = player.getLookAngle().normalize();

            for (int i = 2; i < range; i++) {
                Vec3 particlePos = direction.scale(i).add(player.getEyePosition());
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SONIC_BOOM,
                        particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
            }
        }

        private static net.minecraft.world.phys.HitResult checkEntityIntersecting(Entity entity, Vec3 start, Vec3 end, float width) {
            AABB entityBox = entity.getBoundingBox().inflate(width);

            java.util.Optional<Vec3> intersection = entityBox.clip(start, end);

            if (intersection.isPresent()) {
                Vec3 hitPos = intersection.get();
                return new net.minecraft.world.phys.EntityHitResult(entity, hitPos);
            } else {
                return net.minecraft.world.phys.BlockHitResult.miss(end, net.minecraft.core.Direction.UP, net.minecraft.core.BlockPos.containing(end));
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
            MuteStateManager.updateMuteState(entity);
            if (!entity.level().isClientSide && entity.tickCount % 20 == 0) {
                FlowStateManager.updateFlowStates(entity.level());
            }
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
        public static void onCriticalHit(CriticalHitEvent event) {
            Player player = event.getEntity();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_LENS.get())) {
                    float currentModifier = event.getDamageModifier();

                    if (Math.abs(currentModifier - 1.5F) < 0.01F) {
                        event.setDamageModifier(2.0F);
                    } else if (currentModifier > 1.0F) {
                        float relativeMultiplier = currentModifier / 1.5F;
                        event.setDamageModifier(relativeMultiplier * 2.0F);
                    }
                }
            });
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

        @SubscribeEvent
        public static void onLivingBreathe(LivingEvent.LivingTickEvent event) {
            if (event.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.OCEAN_CURRENT_BLESSING.get())) {
                        if (player.isEyeInFluid(FluidTags.WATER)) {
                            player.setAirSupply(player.getMaxAirSupply());
                        }
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
            Player player = event.getEntity();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.PURE_HOLY_GRAIL.get())) {
                    int originalXp = event.getAmount();
                    int increasedXp = (int) (originalXp * 1.5F);
                    event.setAmount(increasedXp);
                }
            });
        }

        @SubscribeEvent
        public static void onExperienceOrbPickup(PlayerXpEvent.PickupXp event) {
            Player player = event.getEntity();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.PURE_HOLY_GRAIL.get())) {
                    int originalValue = event.getOrb().getValue();
                    event.getOrb().value = (int) (originalValue * 1.5F);
                }
            });
        }

        @SubscribeEvent
        public static void onLivingSetTarget(LivingChangeTargetEvent event) {
            LivingEntity target = event.getNewTarget();

            if (target instanceof Player player) {
                if (isUndeadMob(event.getEntity())) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.DARK_WHISPER_RING.get())) {
                            event.setCanceled(true);
                        }
                    });
                }
            }
        }

        private static boolean isUndeadMob(LivingEntity entity) {
            return entity instanceof Zombie ||
                    entity instanceof Skeleton ||
                    entity instanceof WitherSkeleton ||
                    entity instanceof Stray ||
                    entity instanceof Husk ||
                    entity instanceof Drowned ||
                    entity instanceof ZombifiedPiglin ||
                    entity instanceof Phantom ||
                    entity instanceof WitherBoss;
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.TIME_HOURGLASS.get())) {
                    if (!player.level().isClientSide) {
                        if (player.tickCount % 10 == 0) {
                            accelerateBlockTicksAroundPlayer(player);
                        }
                    }
                }
            });
        }

        private static void accelerateBlockTicksAroundPlayer(Player player) {
            int radius = 8;
            int centerX = (int) player.getX();
            int centerY = (int) player.getY();
            int centerZ = (int) player.getZ();

            for (int x = centerX - radius; x <= centerX + radius; x++) {
                for (int y = Math.max(player.level().getMinBuildHeight(), centerY - radius);
                     y <= Math.min(player.level().getMaxBuildHeight(), centerY + radius); y++) {
                    for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                        double distance = Math.sqrt(
                                Math.pow(x - centerX, 2) +
                                        Math.pow(y - centerY, 2) +
                                        Math.pow(z - centerZ, 2)
                        );
                        if (distance <= radius) {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState state = player.level().getBlockState(pos);
                            BlockEntity blockEntity = player.level().getBlockEntity(pos);

                            double acceleration = 1.0 + (radius - distance) / radius * 2.0;

                            if (state.isRandomlyTicking()) {
                                if (player.level().random.nextDouble() < 0.3 * (acceleration - 1.0)) {
                                    state.randomTick((ServerLevel) player.level(), pos, player.level().random);
                                }
                            }

                            if (blockEntity != null && state.getBlock() instanceof EntityBlock entityBlock) {
                                @SuppressWarnings("unchecked")
                                BlockEntityTicker<BlockEntity> ticker = (BlockEntityTicker<BlockEntity>)
                                        entityBlock.getTicker(player.level(), state, blockEntity.getType());

                                if (ticker != null && !blockEntity.isRemoved()) {
                                    int tickCount = (int) Math.max(1, acceleration);
                                    for (int i = 0; i < tickCount; i++) {
                                        if (blockEntity.isRemoved()) {
                                            break;
                                        }
                                        ticker.tick(player.level(), pos, state, blockEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
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