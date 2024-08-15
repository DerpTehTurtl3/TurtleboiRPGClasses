package net.turtleboi.turtlerpgclasses.event;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.capabilities.party.PlayerPartyProvider;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.capabilities.PlayerClassProvider;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStates;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStatesProvider;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.effect.effects.*;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.ClassSelectionS2CPacket;
import net.turtleboi.turtlerpgclasses.network.packet.OpenClassSelectionScreenPacket;
import net.turtleboi.turtlerpgclasses.network.packet.resources.PlayerResourcesS2CPacket;
import net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager;
import net.turtleboi.turtlerpgclasses.rpg.talents.*;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.*;
import net.turtleboi.turtlerpgclasses.util.PartyUtils;
import net.turtleboi.turtlerpgclasses.event.ClientEvents;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!event.getObject().getCapability(PlayerClassProvider.PLAYER_RPGCLASS).isPresent()) {
                event.addCapability(new ResourceLocation(TurtleRPGClasses.MOD_ID, "player_class"), new PlayerClassProvider());
            }
            if (!event.getObject().getCapability(TalentStatesProvider.TALENT_STATES).isPresent()) {
                event.addCapability(new ResourceLocation(TurtleRPGClasses.MOD_ID, "talent_states"), new TalentStatesProvider());
            }
            if (!event.getObject().getCapability(PlayerResourceProvider.PLAYER_RESOURCE).isPresent()) {
                event.addCapability(new ResourceLocation(TurtleRPGClasses.MOD_ID, "player_resource"), new PlayerResourceProvider(player));
            }
            if (!event.getObject().getCapability(PlayerAbilityProvider.PLAYER_ABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(TurtleRPGClasses.MOD_ID, "player_ability"), new PlayerAbilityProvider());
            }
            if (!event.getObject().getCapability(PlayerPartyProvider.PLAYER_PARTY).isPresent()) {
                event.addCapability(new ResourceLocation(TurtleRPGClasses.MOD_ID, "player_party"), new PlayerPartyProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
            event.getOriginal().getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
            event.getOriginal().getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(oldStore ->
                    event.getEntity().getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
            event.getOriginal().getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
            event.getOriginal().getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(oldStore ->
                    event.getEntity().getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        player.reviveCaps();
        player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(talentStates -> {
            CompoundTag compound = new CompoundTag();
            talentStates.saveNBTData(compound);
            player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(newTalentStates -> {
                newTalentStates.loadNBTData(compound);
            });
        });
        player.invalidateCaps();
        if (player.level.isClientSide()) {
            CooldownOverlay.initializeSlots(player);
            ResourceOverlay.initializeResourceBars();
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        player.reviveCaps();
        player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(talentStates -> {
            CompoundTag compound = new CompoundTag();
            talentStates.saveNBTData(compound);
            player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(newTalentStates -> {
                newTalentStates.loadNBTData(compound);
            });
        });
        player.invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass ->
                        ModNetworking.sendToPlayer(new ClassSelectionS2CPacket(playerClass.getRpgClass(), playerClass.getRpgSubclass()), player));
                player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource ->
                        ModNetworking.sendToPlayer(
                        new PlayerResourcesS2CPacket(
                                playerResource.getStamina(),
                                playerResource.getMaxStamina(),
                                playerResource.getEnergy(),
                                playerResource.getMaxEnergy(),
                                playerResource.getMana(),
                                playerResource.getMaxMana()), player));
                CooldownOverlay.initializeSlots(player);
                ResourceOverlay.initializeResourceBars();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorldClassCheck(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass -> {
                    String currentClass = playerClass.getRpgClass();
                    if(currentClass == null || currentClass.equals("No Class")) {
                        TalentStates.resetAllTalents(player);
                        ActiveAbility.resetAllAbilityCooldowns();
                        ModNetworking.sendToPlayer(new OpenClassSelectionScreenPacket(), player);
                    }
                });
                CooldownOverlay.initializeSlots(player);
                ResourceOverlay.initializeResourceBars();
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity targetEntity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        if (targetEntity instanceof Player player) {
            if (player.hasEffect(ModEffects.STEEL_BARBS.get()) && sourceEntity instanceof LivingEntity attacker) {
                float thornsDamage = 1.0F + (player.getArmorValue() / 3.0F);
                attacker.hurt(DamageSource.thorns(player), thornsDamage);
            }

            SecondWindTalent secondWindTalent = new SecondWindTalent();
            if (secondWindTalent.isActive(player)) {
                player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                    if (player.getHealth() - event.getAmount() <= 0) {
                        if (!player.hasEffect(ModEffects.WINDED.get())) {
                            secondWindTalent.applyAttributes(player);
                            event.setCanceled(true);
                        }
                    }
                });
            }

            BrawlersTenacityTalent brawlersTenacityTalent = new  BrawlersTenacityTalent();
            if (brawlersTenacityTalent.isActive(player)) {
                player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                    int armorIncrease = playerAbility.getBrawlerArmorBonus();
                    int maxArmor = playerAbility.getMaxBrawlerArmorBonus();
                    int hitCounter = playerAbility.getBrawlerHitCounter();
                    int currentArmorBonus = hitCounter * armorIncrease;

                    brawlersTenacityTalent.applyValues(player);

                    if (!player.hasEffect(ModEffects.BRAWLERS_TENACITY.get())) {
                        player.addEffect(new MobEffectInstance(ModEffects.BRAWLERS_TENACITY.get(), 120, 0));
                        playerAbility.setBrawlerHitCounter(1);
                        playerAbility.setBrawlerArmorBonus(armorIncrease);
                        new BrawlersTenacityTalent().applyAttributes(player);
                        new BrawlersTenacityTalent().playHitSound(player, armorIncrease);
                    } else if (player.hasEffect(ModEffects.BRAWLERS_TENACITY.get())) {
                        // Increment armor bonus on subsequent hits
                        if (currentArmorBonus < maxArmor) {
                            playerAbility.incrementBrawlerHitCounter();
                            currentArmorBonus = playerAbility.getBrawlerHitCounter() * armorIncrease;
                            playerAbility.setBrawlerArmorBonus(currentArmorBonus);
                            BrawlersTenacityEffect.updateEffectDuration(player, 120);
                            brawlersTenacityTalent.applyAttributes(player);
                            brawlersTenacityTalent.playHitSound(player, currentArmorBonus);
                            //player.sendSystemMessage(Component.literal("Armor gained: " + armorIncrease + " Total Armor: " + currentArmorBonus)); //Debug code
                        } else {
                            // Max armor reached
                            BrawlersTenacityEffect.updateEffectDuration(player, 120);
                            //player.sendSystemMessage(Component.literal("Max Armor!" + " Total Armor: " + currentArmorBonus)); //Debug code
                        }
                    }
                });
            }

            WarlordsPresenceTalent warlordsPresenceTalent = new WarlordsPresenceTalent();
            if (warlordsPresenceTalent.isActive(player)) {
                Level level = player.level;
                AABB warlordAABB = new AABB(player.blockPosition()).inflate(warlordsPresenceTalent.getWarlordsRadius());
                AABB wrathAABB = new AABB(player.blockPosition()).inflate(warlordsPresenceTalent.getWrathRadius());

                level.getEntitiesOfClass(Player.class, warlordAABB).forEach(ally -> {
                    if (PartyUtils.isAlly(player, ally) && ally != player) {
                        float damage = event.getAmount();
                        player.hurt(DamageSource.GENERIC, damage * 0.5f);
                        event.setAmount(damage * 0.5f);
                    }
                    if (PartyUtils.isAlly(player, ally) || ally == player){
                        if (player.getHealth() <= player.getMaxHealth() * 0.3) {
                            player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility ->{
                                if (!ally.hasEffect(ModEffects.DEFEATED.get()) && !playerAbility.isRallyTriggered()) {
                                    ally.addEffect(new MobEffectInstance(ModEffects.RALLY.get(), 20 * 5, 0));
                                    ally.addEffect(new MobEffectInstance(ModEffects.DEFEATED.get(), 20 * 120, 0));
                                }
                            });
                        }
                    }
                });
                level.getEntitiesOfClass(Player.class, wrathAABB).forEach(ally -> {
                    if (PartyUtils.isAlly(player, ally) || ally == player){
                        if (player.hasEffect(ModEffects.WRATH.get())) {
                            float damage = event.getAmount();
                            event.setAmount(damage * 0.5f);
                            assert sourceEntity != null;
                            sourceEntity.hurt(DamageSource.GENERIC, damage * 0.25f);
                        }
                    }
                });
            }

            if (player.hasEffect(ModEffects.RALLY.get())) {
                event.setCanceled(true);
            }
        }

        if (sourceEntity instanceof Player player) {
             //Logic for if player hurts something, but that also handled by onPlayerAttack
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerAttack(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity trueSource = source.getEntity();
        if (!(trueSource instanceof Player player)) return;

        LivingEntity target = event.getEntity();
        if (!(target instanceof LivingEntity)) return;

        //Focused Strikes Talent Logic
        FocusedStrikesTalent focusedStrikesTalent = new FocusedStrikesTalent();
        if (focusedStrikesTalent.isActive(player)) {
            double ComboTime = (player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED) * 20);

            player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                int threshold = playerAbility.getFocusedStrikesThreshold();
                int counter = playerAbility.getFocusedStrikesCounter();
                int currentBonusDamage = playerAbility.getCurrentBonusDamage();
                int currentBonusMax = playerAbility.getFocusedStrikesMaxDamage();
                int hitsLeft = threshold - counter;

                focusedStrikesTalent.applyValues(player);

                if (!player.hasEffect(ModEffects.FOCUSED_STRIKES.get())) {
                    playerAbility.resetFocusedStrikes();
                    playerAbility.incrementFocusedStrikesCounter();
                    new FocusedStrikesTalent().playHitSound(player, currentBonusDamage);
                    player.addEffect(new MobEffectInstance(ModEffects.FOCUSED_STRIKES.get(), (int) ComboTime + 40, 0));
                    //player.sendSystemMessage(Component.literal(hitsLeft + " hits left until next damage bonus")); //Debug code
                } else if (player.hasEffect(ModEffects.FOCUSED_STRIKES.get())) {
                    MobEffectInstance effectInstance = player.getEffect(ModEffects.FOCUSED_STRIKES.get());
                    assert effectInstance != null;
                    double timer = effectInstance.getDuration();
                    if (counter >= threshold && timer >= ComboTime) {
                        playerAbility.setFocusedStrikesCounter(0);
                        if (currentBonusDamage < currentBonusMax) {
                            playerAbility.addBonusDamage(playerAbility.getFocusedStrikesDamage());
                            focusedStrikesTalent.applyAttributes(player);
                            focusedStrikesTalent.playUpgradeSound(player, currentBonusDamage);
                        }
                    } else if (timer >= ComboTime && currentBonusDamage < currentBonusMax) {
                        playerAbility.incrementFocusedStrikesCounter();
                        //player.sendSystemMessage(Component.literal(hitsLeft + " hits left until next damage bonus")); //Debug code
                        focusedStrikesTalent.playHitSound(player, currentBonusDamage);
                    } else if (currentBonusDamage >= currentBonusMax) {
                        focusedStrikesTalent.playHitSound(player, currentBonusDamage);
                        //player.sendSystemMessage(Component.literal("Max damage!")); //Debug code
                    }
                    FocusedStrikesEffect.updateEffectDuration(player, (int) (ComboTime + 40)); // Reset the timer on successful hit
                }
            });
        }

        VictoriousCryTalent victoriousCryTalent = new VictoriousCryTalent();
        if (victoriousCryTalent.isActive(player)){
            victoriousCryTalent.handleDamageBoost(player, target, event.getAmount());
        }

        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            if (playerAbility.isExecuteActive()) {
                float damage = event.getAmount();
                float missingHealthPercent = 1 - (target.getHealth() / target.getMaxHealth());
                float bonusDamage = 8 * missingHealthPercent;
                float totalDamage = damage + bonusDamage;
                event.setAmount(totalDamage);

                // Spawn red sword sweep particles
                ExecuteTalent.spawnExecuteParticle(target);

                if (target.hasEffect(ModEffects.BLEEDING.get())) {
                    target.addEffect(new MobEffectInstance(ModEffects.STUNNED.get(), 100, 1)); // Stun for 5 seconds
                }

                playerAbility.setExecuteActive(false);

                if (totalDamage >= target.getHealth()) {
                    // Refresh cooldown of Execute
                    ExecuteTalent executeTalent = new ExecuteTalent();
                    executeTalent.resetAbilityCooldown(player);
                    //player.sendSystemMessage(Component.literal("Execute refreshed!"));//Debug code
                }
            }
        });

        WarlordsPresenceTalent warlordsPresenceTalent = new WarlordsPresenceTalent();
        if (warlordsPresenceTalent.isActive(player)) {
            Level level = player.level;
            AABB wrathAABB = new AABB(player.blockPosition()).inflate(warlordsPresenceTalent.getWrathRadius());

            if (player.hasEffect(ModEffects.WRATH.get())) {
                level.getEntitiesOfClass(Player.class, wrathAABB).forEach(ally -> {
                    if (PartyUtils.isAlly(player, ally) || ally == player) {
                        if (target != player && target != ally) {
                            target.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 20 * 10, 0));
                            Random random = new Random();
                            if (random.nextInt(100) < 20) {
                                target.addEffect(new MobEffectInstance(ModEffects.STUNNED.get()));
                            }
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        //Player target logic
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity target) {
            Entity source = event.getSource().getEntity();
            if (source instanceof Player player) {
                VictoriousCryTalent victoriousCryTalent = new VictoriousCryTalent();
                if (victoriousCryTalent.isActive(player)) {
                    victoriousCryTalent.onTargetDeath(player, target);
                }

                player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                    if (target == playerAbility.getTargetEntity()){
                        playerAbility.setTaunting(false);
                    }
                });

                if (player.hasEffect(ModEffects.GUARDIANS_OATH.get())) {
                    GuardiansOathTalent talent = new GuardiansOathTalent();
                    GuardiansOathEffect.onEnemyDefeated(player, target, talent);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            serverPlayer.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass -> {
                String className = playerClass.getRpgClass();
                String subclassName = playerClass.getRpgSubclass();
                ModNetworking.sendToPlayer(new ClassSelectionS2CPacket(className, subclassName), serverPlayer);
                ClassAttributeManager.applyClassAttributes(serverPlayer, className);
                ClassAttributeManager.applyTalentAttributes(serverPlayer);
            });
            String playerClass = ClientClassData.getPlayerClass();
            String playerSubclass = ClientClassData.getPlayerSubclass();
            serverPlayer.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource -> {
                //Stamina active declarations
                playerResource.setStaminaActive("Warrior".equals(playerClass));

                //Energy active declarations
                playerResource.setEnergyActive("Ranger".equals(playerClass));

                //Mana active declarations
                if ("Mage".equals(playerClass)){
                    playerResource.setManaActive(true);
                } else playerResource.setManaActive("Paladin".equals(playerSubclass) && new PathOfThePaladinSubclass().isActive(serverPlayer));

                double deltaTime = 1.0 / 40.0; //1 second is 40 ticks due to double counting
                playerResource.updateRechargeRates(deltaTime);

                ModNetworking.sendToPlayer(
                        new PlayerResourcesS2CPacket(
                                playerResource.getStamina(),
                                playerResource.getMaxStamina(),
                                playerResource.getEnergy(),
                                playerResource.getMaxEnergy(),
                                playerResource.getMana(),
                                playerResource.getMaxMana()), serverPlayer);
            });

            SecondWindTalent secondWindTalent = new SecondWindTalent();
            if (secondWindTalent.isActive(serverPlayer)) {
                serverPlayer.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                    if (!serverPlayer.hasEffect(ModEffects.WINDED.get())) {
                        if (playerAbility.getWindedDuration() >= 20) { //Anything below 1 second (20 ticks) shouldn't matter
                            MobEffectInstance windedEffect = new MobEffectInstance(
                                    ModEffects.WINDED.get(),
                                    playerAbility.getWindedDuration(),
                                    0,
                                    false,
                                    true,
                                    true);
                            serverPlayer.addEffect(windedEffect);
                        }
                    }
                });
            }

            if (event.phase == TickEvent.Phase.END) {

                //Charge logic
                serverPlayer.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbilities -> {
                    if (playerAbilities.isCharging()) {
                        if (serverPlayer.isShiftKeyDown()) {
                            playerAbilities.setCharging(false);
                            return;
                        }

                        ClientEvents.showChargeCancelMessage = true;

                        Entity targetEntity = playerAbilities.getTargetEntity();
                        if (targetEntity == null) {
                            playerAbilities.setCharging(false);
                            ClientEvents.showChargeCancelMessage = false;
                            return;
                        }

                        Vec3 targetPos = targetEntity.position();
                        Vec3 targetEyePos = targetEntity.getEyePosition();
                        Vec3 playerPos = serverPlayer.position();
                        Vec3 direction = targetPos.subtract(playerPos).normalize();
                        Vec3 lookDirection = serverPlayer.getLookAngle();
                        double distance = playerPos.distanceTo(targetPos);
                        int timeout_ticks = 60;
                        double strafeDistance = 0.5;
                        double stoppingDistance = 2.0;

                        if (distance > stoppingDistance) {
                            ((Player) serverPlayer).lookAt(EntityAnchorArgument.Anchor.EYES, targetEyePos);
                            double speed = Math.max(0.1, Math.min(1.5, distance / 5.0));
                            Vec3 motion = direction.scale(speed);

                            double playerY = playerPos.y;
                            double targetY = targetPos.y;

                            boolean isOnGround = serverPlayer.isOnGround();

                            if (isOnGround) {
                                motion = motion.add(0, -0.1, 0);
                                serverPlayer.hurtMarked = true;
                            } else if (playerY > targetY) {
                                motion = motion.add(0, -0.25, 0);
                                serverPlayer.hurtMarked = true;
                            }

                            boolean obstacleAbove = checkObstacles(serverPlayer, playerPos, lookDirection, 2, 0);
                            boolean obstacleBelow = checkObstacles(serverPlayer, playerPos, lookDirection, -1, 0);
                            boolean obstacleFrontFeet = checkObstacles(serverPlayer, playerPos, lookDirection, 0, 0);
                            boolean obstacleFrontEyes = checkObstacles(serverPlayer, playerPos, lookDirection, 1, 0);
                            boolean obstacleLeft = checkObstacles(serverPlayer, playerPos, lookDirection, 0, 1, true);
                            boolean obstacleRight = checkObstacles(serverPlayer, playerPos, lookDirection, 0, -1, true);
                            boolean obstacleFence = checkFencesAndGates(serverPlayer, playerPos, lookDirection);

                            if (obstacleFence) {
                                //serverPlayer.sendSystemMessage(Component.literal("Fence!")); // Debug code
                                motion = handleFenceJumping(serverPlayer, motion, isOnGround);
                                playerAbilities.setLastObstacle("Fence");
                            } else if (obstacleBelow || obstacleFrontFeet) {
                                motion = motion.add(0, 0.2, 0);
                                playerAbilities.setLastObstacle("Below");
                            } else if (obstacleAbove) {
                                motion = motion.add(0, -0.2, 0);
                                playerAbilities.setLastObstacle("Above");
                            } else if (obstacleFrontEyes) {
                                Vec3 strafeDirection = playerAbilities.getStrafeDirection();
                                if (strafeDirection == null) {
                                    Vec3 leftStrafe = lookDirection.cross(new Vec3(0, 1, 0)).normalize().scale(strafeDistance);
                                    Vec3 rightStrafe = lookDirection.cross(new Vec3(0, -1, 0)).normalize().scale(strafeDistance);
                                    if (obstacleRight) {
                                        strafeDirection = leftStrafe;
                                    } else if (obstacleLeft) {
                                        strafeDirection = rightStrafe;
                                    }
                                    playerAbilities.setStrafeDirection(strafeDirection);
                                }
                                if (strafeDirection != null) {
                                    motion = strafeDirection.add(direction.scale(speed));
                                }
                                playerAbilities.setLastObstacle("FrontEyes");
                            } else {
                                motion = motion.add(0, -0.1, 0);
                                playerAbilities.setStrafeDirection(null);
                                if ("Fence".equals(playerAbilities.getLastObstacle()) && isAirInFront(serverPlayer, playerPos, lookDirection)) {
                                    motion = motion.add(0, 0.5, 0);
                                } else {
                                    playerAbilities.setLastObstacle("None");
                                }
                            }
                            //if (isOnGround) {
                                spawnSmokeParticles(serverPlayer);
                            //}

                            serverPlayer.setDeltaMovement(motion);
                        } else {
                            serverPlayer.setPos(targetPos.x - direction.x, targetPos.y - direction.y, targetPos.z - direction.z);
                            serverPlayer.setDeltaMovement(Vec3.ZERO);
                            playerAbilities.setCharging(false);
                            playerAbilities.setStrafeDirection(null);
                        }
                        serverPlayer.hurtMarked = true;

                        if (playerAbilities.getChargeTicks() >= timeout_ticks) {
                            playerAbilities.setCharging(false);
                            playerAbilities.setChargeTicks(0);
                            ((Player) serverPlayer).sendSystemMessage(Component.literal("No path found..."));
                        } else {
                            if (playerPos.equals(playerAbilities.getLastChargePosition())) {
                                playerAbilities.incrementChargeTicks();
                            } else {
                                playerAbilities.setChargeTicks(0);
                            }
                            playerAbilities.setLastChargePosition(playerPos);
                        }
                    } else {
                        ClientEvents.showChargeCancelMessage = false;
                    }
                });

                //Stampede Logic
                serverPlayer.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                    StampedeTalent stampedeTalent = new StampedeTalent();
                    if (stampedeTalent.isActive(serverPlayer)) {
                        int talentPoints = stampedeTalent.getPoints(serverPlayer);
                        double damage = 5 + (talentPoints == 3 ? 5 : (talentPoints == 2 ? 2 : 0));
                        Vec3 playerPos = serverPlayer.position();

                        float yaw = serverPlayer.getYRot();
                        double xDirection = -Math.sin(Math.toRadians(yaw));
                        double zDirection = Math.cos(Math.toRadians(yaw));
                        Vec3 direction = new Vec3(xDirection, 0, zDirection).normalize();

                        if (playerAbility.isStampeding()) {
                            Vec3 motion = direction.scale(1.0);
                            AABB boundingBox = serverPlayer.getBoundingBox().move(direction.scale(0.5)).inflate(1.0);
                            List<LivingEntity> targets = serverPlayer.level.getEntitiesOfClass(LivingEntity.class, boundingBox);

                            for (LivingEntity target : targets) {
                                if (target != serverPlayer) {
                                    target.hurt(DamageSource.playerAttack(serverPlayer), (float) damage);
                                    Vec3 knockback = new Vec3(direction.x, 0.1, direction.z).normalize().scale(1.5);
                                    target.setDeltaMovement(knockback);
                                }
                            }
                            boolean obstacleAbove = checkObstacles(serverPlayer, playerPos, direction, 2, 0);
                            boolean obstacleFrontFeet = checkObstacles(serverPlayer, playerPos, direction, 0, 0);
                            boolean obstacleFrontEyes = checkObstacles(serverPlayer, playerPos, direction, 1, 0);
                            BlockPos pos1 = new BlockPos(playerPos.add(direction.scale(0.5)).add(-0.5, 0, 0));
                            BlockPos pos2 = new BlockPos(playerPos.add(direction.scale(0.5)).add(0.5, 0, 0));
                            BlockPos pos3 = new BlockPos(playerPos.add(direction.scale(0.5)).add(0, 0, -0.5));
                            BlockPos pos4 = new BlockPos(playerPos.add(direction.scale(0.5)).add(0, 0, 0.5));

                            boolean cornerObstacle1 = isObstacle(serverPlayer.level.getBlockState(pos1));
                            boolean cornerObstacle2 = isObstacle(serverPlayer.level.getBlockState(pos2));
                            boolean cornerObstacle3 = isObstacle(serverPlayer.level.getBlockState(pos3));
                            boolean cornerObstacle4 = isObstacle(serverPlayer.level.getBlockState(pos4));

                            if (obstacleFrontFeet || cornerObstacle1 || cornerObstacle2 || cornerObstacle3 || cornerObstacle4 && !obstacleFrontEyes) {
                                BlockPos topBlockPos = new BlockPos(playerPos.add(direction.scale(0.5)).add(0, 1, 0));
                                if (serverPlayer.level.getBlockState(topBlockPos).isAir()) {
                                    Vec3 nextPos = new Vec3(playerPos.x + direction.x, topBlockPos.getY(), playerPos.z + direction.z);
                                    serverPlayer.teleportTo(nextPos.x, nextPos.y, nextPos.z);
                                }
                            } else {
                                if (obstacleAbove) {
                                    motion = motion.add(0, -0.2, 0);
                                } else {
                                    motion = motion.add(0, -0.1, 0);
                                }
                                serverPlayer.setDeltaMovement(motion);
                            }
                            spawnSmokeParticles(serverPlayer);
                            serverPlayer.hurtMarked = true;
                            playerAbility.incrementStampedeTicks();
                            if (playerAbility.getStampedeTicks() >= 16) {
                                playerAbility.setStampeding(false);
                                playerAbility.setStampedeTicks(0);
                            }
                        }
                    }
                });

                //Intimidating Presence Logic
                IntimidatingPresenceTalent.tickPlayer(serverPlayer);

                GuardiansOathTalent guardiansOathTalent = new GuardiansOathTalent();
                if (guardiansOathTalent.isActive(serverPlayer)){
                    guardiansOathTalent.applyGuardianOathEffects(serverPlayer);
                }

                WarlordsPresenceTalent warlordsPresenceTalent = new WarlordsPresenceTalent();
                if (warlordsPresenceTalent.isActive(serverPlayer)){
                    warlordsPresenceTalent.applyWarlordsPresenceEffects(serverPlayer);
                    serverPlayer.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                        if (!serverPlayer.hasEffect(ModEffects.DEFEATED.get())) {
                            if (playerAbility.getExhaustedDuration()>= 20) { //Anything below 1 second (20 ticks) shouldn't matter
                                MobEffectInstance exhaustedEffect = new MobEffectInstance(
                                        ModEffects.DEFEATED.get(),
                                        playerAbility.getExhaustedDuration(),
                                        0,
                                        false,
                                        true,
                                        true);
                                serverPlayer.addEffect(exhaustedEffect);
                            }
                        }
                    });
                }
            }
        }
    }

    // Static methods to check obstacles
    private static boolean checkObstacles(ServerPlayer serverPlayer, Vec3 playerPos, Vec3 lookDirection, int yOffset, int zOffset) {
        return checkObstacles(serverPlayer, playerPos, lookDirection, yOffset, zOffset, false);
    }

    private static boolean checkObstacles(ServerPlayer serverPlayer, Vec3 playerPos, Vec3 lookDirection, int yOffset, int zOffset, boolean lateral) {
        int[][] offsets = lateral ? new int[][]{{1, 1}, {-1, 1}} : new int[][]{{1, 0}, {0, 0}, {-1, 0}};
        for (int[] offset : offsets) {
            BlockPos checkPos = new BlockPos(playerPos.add(lookDirection.scale(offset[0])).add(0, yOffset, zOffset * offset[1]));
            //serverPlayer.sendSystemMessage(Component.literal("Checking block at " + checkPos + ": " + serverPlayer.level.getBlockState(checkPos).getBlock().getName().getString())); // Debug code
            if (isObstacle(serverPlayer.level.getBlockState(checkPos))) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkFencesAndGates(ServerPlayer serverPlayer, Vec3 playerPos, Vec3 lookDirection) {
        int[][] offsets = new int[][]{{1, 0}, {1, 1}, {1, 2}, {0, 1}, {0, 2}, {-1, 0}, {-1, 1}, {-1, 2}};
        for (int[] offset : offsets) {
            BlockPos checkPos = new BlockPos(playerPos.add(lookDirection.scale(offset[0])).add(0, offset[1], 0));
            //serverPlayer.sendSystemMessage(Component.literal("Checking fence or gate at " + checkPos + ": " + serverPlayer.level.getBlockState(checkPos).getBlock().getName().getString())); // Debug code
            if (isFenceOrGate(serverPlayer.level.getBlockState(checkPos))) {
                return true;
            }
        }
        return false;
    }


    private static Vec3 handleFenceJumping(ServerPlayer serverPlayer, Vec3 motion, boolean isOnGround) {
        if (!isOnGround) {
            motion = motion.add(0, 0.5, 0);
        } else {
            motion = motion.add(0, 1.0, 0);
        }
        return motion;
    }

    private static boolean isObstacle(BlockState blockState) {
        return blockState.getMaterial().isSolid() && !blockState.getMaterial().isReplaceable() && !(blockState.getBlock() instanceof FenceBlock) && !(blockState.getBlock() instanceof FenceGateBlock);
    }

    private static boolean isFenceOrGate(BlockState state) {
        return state.getBlock() instanceof FenceBlock || state.getBlock() instanceof FenceGateBlock;
    }

    private static boolean isAirInFront(ServerPlayer serverPlayer, Vec3 playerPos, Vec3 lookDirection) {
        BlockPos checkPos = new BlockPos(playerPos.add(lookDirection.scale(1)));
        return serverPlayer.level.getBlockState(checkPos).isAir();
    }

    private static void spawnSmokeParticles(ServerPlayer player) {
        Level level = player.level;
        if (level instanceof ServerLevel) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            double offsetX = 0.2;
            double offsetY = 0.1;
            double offsetZ = 0.2;
            double speed = 0.02;

            for (int i = 0; i < 5; i++) {
                double particleX = x + (level.random.nextDouble() - 0.5) * offsetX;
                double particleY = y + (level.random.nextDouble() - 0.5) * offsetY;
                double particleZ = z + (level.random.nextDouble() - 0.5) * offsetZ;
                ((ServerLevel) level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, particleX, particleY, particleZ, 1, 0, 0, 0, speed);
            }
        }
    }
}
