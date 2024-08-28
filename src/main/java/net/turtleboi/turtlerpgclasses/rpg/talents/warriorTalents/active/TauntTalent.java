package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.active;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlecore.util.TargetingUtils;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.rpg.talents.ActiveAbility;

import java.util.*;

public class TauntTalent extends ActiveAbility {
    private static final String Name = Component.translatable("talents.taunt").getString();

    @Override
    public void applyAttributes(Player player) {

    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of();
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        Map<String, Integer> costs = new HashMap<>();
        costs.put("stamina", 5);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 10;
    }

    @Override
    protected Set<MobCategory> getAllowedCategories() {
        return EnumSet.of(
                MobCategory.AMBIENT,
                MobCategory.CREATURE,
                MobCategory.AXOLOTLS,
                MobCategory.UNDERGROUND_WATER_CREATURE,
                MobCategory.WATER_CREATURE
        );
    }

    @Override
    public boolean activate(Player player) {
        final boolean[] activated = {false};
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            playerAbility.setTargetEntity(null);
            playerAbility.setTaunting(false);
            LivingEntity target = TargetingUtils.getTarget(player);
            if (target instanceof Mob mobTarget) {
                MobCategory category = target.getType().getCategory();
                if (!isAllowedCategory(category) && !playerAbility.isTaunting()) {
                    playerAbility.setTargetEntity(target);
                    playerAbility.setTaunting(true);
                    LivingEntity tauntTarget = playerAbility.getTargetEntity();
                    tauntTarget.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
                    mobTarget.goalSelector.addGoal(
                            0, new NearestAttackableTargetGoal<>(
                                    mobTarget,
                                    Player.class,
                                    1,
                                    false,
                                    false,
                                    (targetPlayer) -> targetPlayer.equals(player)));
                    mobTarget.setTarget(player);
                    VictoriousCryTalent victoriousCryTalent = new VictoriousCryTalent();
                    if (victoriousCryTalent.isActive(player)) {
                        victoriousCryTalent.applyTauntBonus(player, tauntTarget);
                    }
                    activated[0] = true;
                } else {
                    playerAbility.setTaunting(false);
                    player.sendSystemMessage(Component.literal("No target found"));
                }
            } else {
                player.sendSystemMessage(Component.literal("No target found"));
            }
        });
        return activated[0];
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[0];
    }

    @Override
    public void spawnAbilityEntity(Player player, Level level) {

    }

    @Override
    public int getDuration() {
        return getCooldownSeconds() * 20;
    }
}
