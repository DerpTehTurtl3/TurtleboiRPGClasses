package net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.talents.warrior;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentTree;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.StatTalentButton;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.ArrayList;
import java.util.List;

public class CombatVeteranTalentNode extends StatTalentButton {
    private final TalentTree talentTree;

    public CombatVeteranTalentNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
        this.talentTree = talentTree;
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        double currentPoints = getCurrentPoints();
        double[] meleeDamageValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] attackSpeedValues = {6.0, 12.0, 18.0, 24.0, 30.0};
        double[] staminaValues = {4.0, 8.0, 12.0, 16.0, 20.0};
        double[] staminaRechargeValues = {2.0, 3.0, 4.0, 5.0, 6.0};
        int currentRankIndex = (int) Math.max(0, Math.min(currentPoints - 1, meleeDamageValues.length - 1));
        double meleeDamageValue = meleeDamageValues[currentRankIndex];
        double nextRankMeleeDamageValue = currentRankIndex < meleeDamageValues.length - 1 ? meleeDamageValues[currentRankIndex + 1] : meleeDamageValue;
        double attackSpeedValue = attackSpeedValues[currentRankIndex];
        double nextRankAttackSpeedValue = currentRankIndex < attackSpeedValues.length - 1 ? attackSpeedValues[currentRankIndex + 1] : attackSpeedValue;
        double staminaValue = staminaValues[currentRankIndex];
        double nextRankStaminaValue = currentRankIndex < staminaValues.length - 1 ? staminaValues[currentRankIndex + 1] : staminaValue;
        double staminaRechargeValue = staminaRechargeValues[currentRankIndex];
        double nextRankStaminaRechargeValue = currentRankIndex < staminaRechargeValues.length - 1 ? staminaRechargeValues[currentRankIndex + 1] : staminaRechargeValue;
        boolean isShiftPressed = Screen.hasShiftDown();

        tooltip.add(Component.translatable("talents.combat_veteran")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.stat")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(Component.literal(" +1.0/2.0/3.0/4.0/5.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.melee_damage"))
                    .append(Component.literal(" +6.0%/12.0%/18.0%/24.0%/30.0% ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.attack_speed"))
                    .append(Component.literal(" +4.0/8.0/12.0/16.0/20.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.health_points"))
                    .append(Component.literal(" +2.0/3.0/4.0/5.0/6.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.armor_points")));
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints < maxPoints) {
            Component meleeComponent = Component.literal(" +" + (isShiftPressed ? nextRankMeleeDamageValue : meleeDamageValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component attackSpeedComponent = Component.literal(" +" + (isShiftPressed ? nextRankAttackSpeedValue : attackSpeedValue) + "% ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component staminaComponent = Component.literal(" +" + (isShiftPressed ? nextRankStaminaValue : staminaValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component staminaRechargeComponent = Component.literal(" +" + (isShiftPressed ? nextRankStaminaRechargeValue : staminaRechargeValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(meleeComponent)
                    .append(Component.translatable("talents.melee_damage"))
                    .append(attackSpeedComponent)
                    .append(Component.translatable("talents.attack_speed"))
                    .append(staminaComponent)
                    .append(Component.translatable("talents.health_points"))
                    .append(staminaRechargeComponent)
                    .append(Component.translatable("talents.armor_points")));
            if (!isShiftPressed) {
                tooltip.add(Component.translatable("talents.press_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            } else {
                tooltip.add(Component.translatable("talents.release_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            }
        } else if (currentPoints == maxPoints){
            Component meleeComponent = Component.literal(" +" + (meleeDamageValue) + " ");
            Component attackSpeedComponent = Component.literal(" +" + (attackSpeedValue) + "% ");
            Component staminaComponent = Component.literal(" +" + (staminaValue) + " ");
            Component staminaRechargeComponent = Component.literal(" +" + (staminaRechargeValue) + " ");
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(meleeComponent)
                    .append(Component.translatable("talents.melee_damage"))
                    .append(attackSpeedComponent)
                    .append(Component.translatable("talents.attack_speed"))
                    .append(staminaComponent)
                    .append(Component.translatable("talents.health_points"))
                    .append(staminaRechargeComponent)
                    .append(Component.translatable("talents.armor_points")));
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }
}
