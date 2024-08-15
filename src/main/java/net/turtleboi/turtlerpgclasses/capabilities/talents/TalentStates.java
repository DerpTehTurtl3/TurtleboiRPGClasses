package net.turtleboi.turtlerpgclasses.capabilities.talents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.TalentButton;
import net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager;
import net.turtleboi.turtlerpgclasses.rpg.talents.*;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.*;

import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class TalentStates {
    private static Map<String, TalentButton.TalentState> states = new HashMap<>();
    private static Map<String, Integer> points = new HashMap<>();
    private static final Map<String, TalentButton> talentButtonRegistry = new HashMap<>();
    private static int purchasedPoints = 0;

    public static void setState(String identifier, TalentButton.TalentState state) {
        states.put(identifier, state);
    }

    public static TalentButton.TalentState getState(String identifier) {
        return states.getOrDefault(identifier, TalentButton.TalentState.LOCKED);
    }

    public static void setPoints(String identifier, int pointsValue) {
        if (!isTalentAlwaysActive(identifier)) {
            points.put(identifier, Math.max(pointsValue, 0));
        }
    }

    public static int getPoints(String identifier) {
        if (isTalentAlwaysActive(identifier)) {
            return 0;
        }
        return points.getOrDefault(identifier, 0);
    }

    public static void resetAllTalents(Player player) {
        ClassAttributeManager.applyTalentAttributes(player);
        ClassAttributeManager.resetAttributes(player);
        new VigorTalent().resetTalent(player);
        new MightyBlowsTalent().resetTalent(player);
        new BattleHardenedTalent().resetTalent(player);
        new SteadyFootingTalent().resetTalent(player);
        new SwiftHandsTalent().resetTalent(player);
        new PathOfTheBarbarianSubclass().resetTalent(player);
        new PathOfTheJuggernautSubclass().resetTalent(player);
        new PathOfThePaladinSubclass().resetTalent(player);
        new QuickRecoveryTalent().resetTalent(player);
        new SecondWindTalent().resetTalent(player);
        new FocusedStrikesTalent().resetTalent(player);
        new MarathonerTalent().resetTalent(player);
        new LifeLeechTalent().resetTalent(player);
        new StaminaMasteryTalent().resetTalent(player);
        new BrawlersTenacityTalent().resetTalent(player);
        new ChargeTalent().resetTalent(player);
        new TauntTalent().resetTalent(player);
        new StampedeTalent().resetTalent(player);
        new MomentumTalent().resetTalent(player);
        new VictoriousCryTalent().resetTalent(player);
        new IntimidatingPresenceTalent().resetTalent(player);
        new ColossusTalent().resetTalent(player);
        new CombatVeteranTalent().resetTalent(player);
        new ExecuteTalent().resetTalent(player);
        new WarlordsPresenceTalent().resetTalent(player);
        new GuardiansOathTalent().resetTalent(player);
    }

    public void copyFrom(TalentStates source) {
        this.states = new HashMap<>(source.states);
        this.points = new HashMap<>(source.points);
        this.purchasedPoints = source.purchasedPoints;
    }

    public static void addPurchasedTalentPoints(int pointsValue) {
        purchasedPoints += Math.max(pointsValue, 0);
    }

    public static int getPurchasedTalentPoints() {
        return purchasedPoints;
    }

    public static void setPurchasedTalentPoints(Player player, int pointsValue) {
        purchasedPoints = Math.max(pointsValue, 0);
    }

    public static int getTotalSpentTalentPoints() {
        return points.entrySet().stream()
                .filter(entry -> !isTalentAlwaysActive(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private static boolean isTalentAlwaysActive(String identifier) {
        TalentButton button = talentButtonRegistry.get(identifier);
        return button != null && button.isAlwaysActive();
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Map.Entry<String, TalentButton.TalentState> entry : states.entrySet()) {
            nbt.putInt(entry.getKey(), entry.getValue().ordinal());
        }
        for (Map.Entry<String, Integer> entry : points.entrySet()) {
            nbt.putInt(entry.getKey() + "_points", entry.getValue());
        }
        nbt.putInt("purchasedPoints", purchasedPoints);
    }

    public void loadNBTData(CompoundTag nbt) {
        TalentButton.TalentState[] values = TalentButton.TalentState.values();

        for (String key : nbt.getAllKeys()) {
            if (!key.endsWith("_points")) {
                int ordinal = nbt.getInt(key);
                if (ordinal >= 0 && ordinal < values.length) {
                    TalentButton.TalentState state = values[ordinal];
                    states.put(key, state);
                }
            }
        }

        for (String key : nbt.getAllKeys()) {
            if (key.endsWith("_points")) {
                String identifier = key.replace("_points", "");
                int pointsValue = nbt.getInt(key);
                points.put(identifier, Math.max(pointsValue, 0));
            }
        }
        purchasedPoints = Math.max(nbt.getInt("purchasedPoints"), 0);
    }

    public static void registerTalentButton(TalentButton button) {
        talentButtonRegistry.put(button.getIdentifier(), button);
    }
}
