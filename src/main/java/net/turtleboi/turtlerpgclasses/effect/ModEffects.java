package net.turtleboi.turtlerpgclasses.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.effect.effects.*;
import net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents.EvasiveManeuversTalent;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<MobEffect> STEEL_BARBS = MOB_EFFECTS.register("steelbarbs",
            () -> new SteelBarbsEffect(MobEffectCategory.BENEFICIAL, 8231));

    public static final RegistryObject<MobEffect> SECOND_WIND = MOB_EFFECTS.register("secondwind",
            () -> new SecondWindEffect(MobEffectCategory.BENEFICIAL, 10266062));

    public static final RegistryObject<MobEffect> WINDED = MOB_EFFECTS.register("winded",
            () -> new WindedEffect(MobEffectCategory.HARMFUL, 9466043));

    public static final RegistryObject<MobEffect> DEFEATED = MOB_EFFECTS.register("defeated",
            () -> new DefeatedEffect(MobEffectCategory.HARMFUL, 4727712));

    public static final RegistryObject<MobEffect> COLOSSUS = MOB_EFFECTS.register("colossus",
            () -> new ColossusEffect(MobEffectCategory.BENEFICIAL, 9507860));

    public static final RegistryObject<MobEffect> BRAWLERS_TENACITY = MOB_EFFECTS.register("brawlerstenacity",
            () -> new BrawlersTenacityEffect(MobEffectCategory.BENEFICIAL, 8153691));

    public static final RegistryObject<MobEffect> FOCUSED_STRIKES = MOB_EFFECTS.register("focusedstrikes",
            () -> new FocusedStrikesEffect(MobEffectCategory.BENEFICIAL, 15942698));

    public static final RegistryObject<MobEffect> MOMENTUM = MOB_EFFECTS.register("momentum",
            () -> new MomentumEffect(MobEffectCategory.BENEFICIAL, 7995449));

    public static final RegistryObject<MobEffect> WARLORDS_PRESENCE = MOB_EFFECTS.register("warlordspresence",
            () -> new WarlordsPresenceEffect(MobEffectCategory.BENEFICIAL, 15355184));

    public static final RegistryObject<MobEffect> GUARDIANS_OATH = MOB_EFFECTS.register("guardiansoath",
            () -> new GuardiansOathEffect(MobEffectCategory.BENEFICIAL, 14788155));

    public static final RegistryObject<MobEffect> RALLY = MOB_EFFECTS.register("rally",
            () -> new RallyEffect(MobEffectCategory.BENEFICIAL, 14495792));

    public static final RegistryObject<MobEffect> WRATH = MOB_EFFECTS.register("wrath",
            () -> new WrathOfTheWarlordEffect(MobEffectCategory.BENEFICIAL, 11546672));

    public static final RegistryObject<MobEffect> BASTION = MOB_EFFECTS.register("bastion",
            () -> new BastionEffect(MobEffectCategory.BENEFICIAL, 16765289));

    public static final RegistryObject<MobEffect> ENERGY_RESTORE = MOB_EFFECTS.register("energyrestore",
            () -> new EnergyRestoreEffect(MobEffectCategory.BENEFICIAL, 6882560));

    public static final RegistryObject<MobEffect> MANA_RESTORE = MOB_EFFECTS.register("manarestore",
            () -> new ManaRestoreEffect(MobEffectCategory.BENEFICIAL, 6882560));

    public static final RegistryObject<MobEffect> STAMINA_RESTORE = MOB_EFFECTS.register("staminarestore",
            () -> new StaminaRestoreEffect(MobEffectCategory.BENEFICIAL, 6882560));

    public static final RegistryObject<MobEffect> STEALTHED = MOB_EFFECTS.register("stealthed",
            () -> new StealthedEffect(MobEffectCategory.BENEFICIAL, 6918259));

    public static final RegistryObject<MobEffect> EVASIVE_MANEUVERS = MOB_EFFECTS.register("evasivemaneuvers",
            () -> new EvasiveManeuversEffect(MobEffectCategory.BENEFICIAL, 11353590));

    public static final RegistryObject<MobEffect> STEADY_BREATHING = MOB_EFFECTS.register("steadybreathing",
            () -> new SteadyBreathingEffect(MobEffectCategory.BENEFICIAL, 11353590));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
