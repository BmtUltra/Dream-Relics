package com.bmt.dream_relics.common;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.init.DRCapabilities;
import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.item.*;
import com.bmt.dream_relics.util.FlowStateManager;
import com.bmt.dream_relics.util.MuteStateManager;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EventHandler {
    @EventBusSubscriber(modid = DreamRelics.MODID)
    public static class GameEventHandler {

        @SubscribeEvent
        public static void HarvestCheck(PlayerEvent.HarvestCheck event) {
            if (!event.canHarvest()) {
                boolean hasYearsAmber = CuriosApi.getCuriosInventory(event.getEntity())
                        .map(handler -> handler.isEquipped(DRItems.YEARS_AMBER.get()))
                        .orElse(false);
                if (hasYearsAmber && YearsAmber.findBestCorrectTool(event.getEntity(), event.getEntity().getMainHandItem(), event.getTargetBlock()) != null) {
                    event.setCanHarvest(true);
                }
            }
        }

        @SubscribeEvent
        public static void LivingHurtEvent(LivingIncomingDamageEvent event) {
            DamageSource damageSource = event.getSource();

            if (event.getEntity() instanceof Player player) {
                var capability = player.getCapability(
                        top.theillusivec4.curios.api.CuriosCapability.INVENTORY
                );
                if (capability != null && capability.isEquipped(DRItems.HEART_VOICE_PENDANT.get())) {
                    float maxHealth = player.getMaxHealth();
                    float maxAllowedDamage = maxHealth * (float) CommonConfig.heartVoicePendantMaxDamagePercent;

                    if (event.getAmount() > maxAllowedDamage) {
                        event.setAmount(maxAllowedDamage);
                    }
                }
            }

            if (event.getEntity() instanceof Player player) {
                MemoryNecklaceItem.recordDamage(player, event.getAmount());
            }

            if (event.getSource().getEntity() instanceof Player player) {
                float originalDamage = event.getAmount();
                float newDamage = MemoryNecklaceItem.applyStoredDamage(player, originalDamage);

                if (newDamage != originalDamage) {
                    event.setAmount(newDamage);
                }
            }

            if (damageSource.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.ECHO_EARRING.get())) {
                        if (player.getRandom().nextFloat() < CommonConfig.echoEarringTriggerChance) {
                            LivingEntity target = event.getEntity();
                            float baseDamage = event.getAmount();
                            float extraDamage = baseDamage * (float) CommonConfig.echoEarringExtraDamageMultiplier;

                            triggerSonicWave(player, target, extraDamage);
                        }
                    }
                });
            }

            if (damageSource.getEntity() instanceof LivingEntity attacker) {
                if (MuteStateManager.isMuted(attacker)) {
                    float damageMultiplier = MuteStateManager.getDamageMultiplier(attacker);
                    event.setAmount(event.getAmount() * damageMultiplier);
                }
            }

            if (damageSource.getEntity() instanceof Player player) {
                boolean hasDagger = player.getMainHandItem().getItem() == DRItems.DARK_WHISPER_DAGGER.get() ||
                        player.getOffhandItem().getItem() == DRItems.DARK_WHISPER_DAGGER.get();
                if (hasDagger) {
                    MuteStateManager.setMuted(event.getEntity());
                }
            }

            if (damageSource.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.ENDLESS_DREAM.get())) {
                        if (event.getEntity() instanceof LivingEntity) {
                            LivingEntity attackedEntity = event.getEntity();
                            if (!(attackedEntity instanceof Player)) {
                                if (player.getRandom().nextFloat() < CommonConfig.endlessDreamTriggerChance) {
                                    SleepStateManager.setSleeping(attackedEntity, CommonConfig.endlessDreamSleepDuration);
                                }
                            }
                        }
                    }
                });
            }

            if (damageSource.getEntity() instanceof Player player) {
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

            if (target instanceof TamableAnimal tamable) {
                LivingEntity owner = tamable.getOwner();
                if (owner instanceof Player player) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_CROWN.get())) {
                            float reducedDamage = event.getAmount() * (float) CommonConfig.royalCrownPetDamageReduction;
                            event.setAmount(reducedDamage);
                        }
                    });
                }
            }

            if (damageSource.getEntity() instanceof TamableAnimal tamableAttacker) {
                LivingEntity owner = tamableAttacker.getOwner();
                if (owner instanceof Player player) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_CROWN.get())) {
                            float boostedDamage = event.getAmount() * (float) CommonConfig.royalCrownPetDamageBoost;
                            event.setAmount(boostedDamage);
                        }
                    });
                }
            }
        }

        private static float getDamageMultiplier(LivingIncomingDamageEvent event) {
            LivingEntity target = event.getEntity();
            float currentHealth = target.getHealth();
            float maxHealth = target.getMaxHealth();
            float healthPercentage = (currentHealth / maxHealth) * 100;

            return 1.0f + (healthPercentage / 100.0f) * (float) CommonConfig.tasselRingDamageMultiplierMax;
        }

        private static class BossKillRecord {
            long dragonKillTime = 0;
            long witherKillTime = 0;
        }

        private static final Map<UUID, BossKillRecord> BOSS_KILLS = new ConcurrentHashMap<>();

        @SubscribeEvent
        public static void onBossDeath(LivingDeathEvent event) {
            if (!(event.getEntity() instanceof EnderDragon) && !(event.getEntity() instanceof WitherBoss)) {
                return;
            }

            if (!(event.getSource().getEntity() instanceof Player player)) {
                return;
            }

            UUID playerId = player.getUUID();
            long now = System.currentTimeMillis();

            BossKillRecord record = BOSS_KILLS.computeIfAbsent(playerId, k -> new BossKillRecord());

            boolean shouldDrop = false;

            if (event.getEntity() instanceof EnderDragon) {
                record.dragonKillTime = now;
                if (record.witherKillTime != 0 && (now - record.witherKillTime) <= 10_000) {
                    shouldDrop = true;
                }
            } else if (event.getEntity() instanceof WitherBoss) {
                record.witherKillTime = now;
                if (record.dragonKillTime != 0 && (now - record.dragonKillTime) <= 10_000) {
                    shouldDrop = true;
                }
            }

            if (shouldDrop) {
                LivingEntity boss = event.getEntity();
                ItemEntity itemEntity = new ItemEntity(
                        boss.level(),
                        boss.getX(), boss.getY(), boss.getZ(),
                        new ItemStack(DRItems.NIGHTMARE_BOOK.get())
                );
                boss.level().addFreshEntity(itemEntity);
                record.dragonKillTime = 0;
                record.witherKillTime = 0;
            }

            if (record.dragonKillTime != 0 && (now - record.dragonKillTime) > 15_000) {
                record.dragonKillTime = 0;
            }
            if (record.witherKillTime != 0 && (now - record.witherKillTime) > 15_000) {
                record.witherKillTime = 0;
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (event.getEntity() instanceof Player player) {
                MemoryNecklaceItem.recordDamage(player, event.getAmount());
            }

            if (event.getSource().getEntity() instanceof Player player) {
                float originalDamage = event.getAmount();
                float newDamage = MemoryNecklaceItem.applyStoredDamage(player, originalDamage);

                if (newDamage != originalDamage) {
                    event.setAmount(newDamage);
                }
            }
        }

        private static void triggerSonicWave(Player player, LivingEntity target, float extraDamage) {
            if (player.level().isClientSide) {
                return;
            }

            float range = (float) CommonConfig.echoEarringRange;
            float width = (float) CommonConfig.echoEarringWidth;

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

        @SubscribeEvent
        public static void LivingAttackEvent(LivingIncomingDamageEvent event) {
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
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            Player player = event.getPlayer();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.RARE_GOLD_BRACELET.get())) {
                    if (isOreBlock(event.getState())) {
                        if (player.getRandom().nextFloat() < CommonConfig.rareGoldBraceletDoubleDropChance) {
                            List<ItemStack> drops = net.minecraft.world.level.block.Block.getDrops(
                                    event.getState(),
                                    (ServerLevel) event.getLevel(),
                                    event.getPos(),
                                    event.getLevel().getBlockEntity(event.getPos()),
                                    player,
                                    player.getMainHandItem()
                            );

                            for (ItemStack drop : drops) {
                                if (!drop.isEmpty()) {
                                    ItemStack copy = drop.copy();
                                    net.minecraft.world.entity.item.ItemEntity itemEntity =
                                            new net.minecraft.world.entity.item.ItemEntity(
                                                    player.level(),
                                                    event.getPos().getX() + 0.5,
                                                    event.getPos().getY() + 0.5,
                                                    event.getPos().getZ() + 0.5,
                                                    copy
                                            );
                                    player.level().addFreshEntity(itemEntity);
                                }
                            }

                            if (!player.level().isClientSide) {
                                ServerLevel serverLevel = (ServerLevel) player.level();

                                player.level().playSound(null, event.getPos(),
                                        net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_CHIME,
                                        net.minecraft.sounds.SoundSource.BLOCKS, 0.5f, 1.2f);

                                for (int i = 0; i < 10; i++) {
                                    double x = event.getPos().getX() + 0.5 + (player.getRandom().nextDouble() - 0.5);
                                    double y = event.getPos().getY() + 0.5 + (player.getRandom().nextDouble() - 0.5);
                                    double z = event.getPos().getZ() + 0.5 + (player.getRandom().nextDouble() - 0.5);

                                    serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.GLOW,
                                            x, y, z, 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                }
            });
        }

        private static boolean isOreBlock(net.minecraft.world.level.block.state.BlockState state) {
            return state.is(Tags.Blocks.ORES);
        }

        @SubscribeEvent
        public static void LivingTickEvent(EntityTickEvent.Pre event) {
            if (event.getEntity() instanceof LivingEntity entity) {
                SleepStateManager.updateSleepState(entity);
                MuteStateManager.updateMuteState(entity);
                if (!entity.level().isClientSide && entity.tickCount % 20 == 0) {
                    FlowStateManager.updateFlowStates(entity.level());
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
        public static void AnvilUpdateEvent(AnvilUpdateEvent event) {
            ItemStack leftItem = event.getLeft();
            ItemStack rightItem = event.getRight();

            if (!rightItem.isEmpty() && rightItem.getItem() == DRItems.FLAWLESS_GEM.get()) {
                if (!leftItem.isEmpty() && leftItem.isDamageableItem()) {
                    ItemStack result = leftItem.copy();
                    boolean modified = false;

                    if (FlawlessGem.hasNegativeEnchantments(result)) {
                        FlawlessGem.removeNegativeEnchantments(result);
                        modified = true;
                    }

                    if (FlawlessGem.needsRepair(result)) {
                        FlawlessGem.repairItem(result);
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
        public static void PlayerTickEvent(PlayerTickEvent.Pre event) {
            Player player = event.getEntity();
            if (!player.isLocalPlayer()) {
                List<? extends Player> players = player.level().players();
                for (Player otherPlayer : players) {
                    if (player.distanceToSqr(otherPlayer) <= 25) {
                        player.getData(DRCapabilities.SERVER_SIDE_PLAYER_DATA);
                    }
                }
            }

            if (!player.level().isClientSide && player.tickCount % 10 == 0) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.TIME_HOURGLASS.get())) {
                        accelerateBlockTicksAroundPlayer(player);
                    }
                });
            }

            if (!player.level().isClientSide && player.tickCount % 20 == 0) {
                PastRingItem.repairPlayerItems(player);
            }
        }

        @SubscribeEvent
        public static void onCriticalHit(CriticalHitEvent event) {
            Player player = event.getEntity();

            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                if (iCuriosItemHandler.isEquipped(DRItems.ROYAL_LENS.get())) {
                    float currentModifier = event.getDamageMultiplier();

                    if (Math.abs(currentModifier - 1.5F) < 0.01F) {
                        event.setDamageMultiplier((float) CommonConfig.royalLensCritDamageMultiplier);
                    } else if (currentModifier > 1.0F) {
                        float relativeMultiplier = currentModifier / 1.5F;
                        event.setDamageMultiplier(relativeMultiplier * (float) CommonConfig.royalLensCritDamageMultiplier);
                    }
                }
            });
        }

        @SubscribeEvent
        public static void onLivingBreathe(LivingBreatheEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(DRItems.OCEAN_CURRENT_BLESSING.get())) {
                        if (player.isEyeInFluid(FluidTags.WATER)) {
                            event.setCanBreathe(true);
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
                    int increasedXp = (int) (originalXp * CommonConfig.pureHolyGrailXpMultiplier);
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
                    event.getOrb().value = (int) (originalValue * CommonConfig.pureHolyGrailXpMultiplier);
                }
            });
        }

        @SubscribeEvent
        public static void onLivingSetTarget(LivingChangeTargetEvent event) {
            LivingEntity target = event.getNewAboutToBeSetTarget();
            if (target instanceof Player player) {
                if (event.getEntity().getType().is(EntityTypeTags.UNDEAD)) {
                    CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                        if (iCuriosItemHandler.isEquipped(DRItems.DARK_WHISPER_RING.get())) {
                            if (event.getEntity().getLastHurtByMob() != player) {
                                event.setCanceled(true);
                            }
                        }
                    });
                }
            }
        }

        private static void accelerateBlockTicksAroundPlayer(Player player) {
            int radius = CommonConfig.timeHourglassRadius;
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

                            double acceleration = 1.0 + (radius - distance) / radius * CommonConfig.timeHourglassAccelerationFactor;

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
}