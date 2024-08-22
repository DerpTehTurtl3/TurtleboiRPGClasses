package net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.talents.warrior;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentTree;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.PassiveTalentButton;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.ArrayList;
import java.util.List;

public class FocusedStrikesTalentNode extends PassiveTalentButton {

    public FocusedStrikesTalentNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        double currentPoints = getCurrentPoints();
        int[] damageIncreaseValues = {1, 1, 1, 2, 2};
        int currentRankIndex = (int) Math.max(0, Math.min(currentPoints - 1, damageIncreaseValues.length - 1));
        int damageIncreaseValue = damageIncreaseValues[currentRankIndex];
        int nextRankDamageIncreaseValue = currentRankIndex < damageIncreaseValues.length - 1 ? damageIncreaseValues[currentRankIndex + 1] : damageIncreaseValue;
        int[] hitCountValues = {4, 3, 3, 2, 2};
        int hitCountValue = hitCountValues[currentRankIndex];
        int nextRankHitCountValue = currentRankIndex < hitCountValues.length - 1 ? hitCountValues[currentRankIndex + 1] : hitCountValue;
        int[] damageMaximumValues = {4, 6, 8, 12, 16};
        int damageMaxValue = damageMaximumValues[currentRankIndex];
        int nextRankDamageMaxValue = currentRankIndex < damageMaximumValues.length - 1 ? damageMaximumValues[currentRankIndex + 1] : damageMaxValue;
        boolean isShiftPressed = Screen.hasShiftDown();

        tooltip.add(Component.translatable("talents.focused_strikes")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.passive")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.focused_strikes.description1")//Change this value for each talent
                    .append(Component.literal("1.0/1.0/1.0/2.0/2.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.focused_strikes.description2"))//Change this value for each talent
                    .append(Component.literal("4.0/3.0/3.0/2.0/2.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.focused_strikes.description3"))
                    .append(Component.literal("+4.0/6.0/8.0/12.0/16.0")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00")))));
            tooltip.add(Component.literal(" "));
            tooltip.add(Component.translatable("talents.focused_strikes.description4"));
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints < maxPoints) {
            Component damageComponent = Component.literal((isShiftPressed ? nextRankDamageIncreaseValue : damageIncreaseValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component hitComponent = Component.literal((isShiftPressed ? nextRankHitCountValue : hitCountValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component damageMaxComponent = Component.literal("+" + (isShiftPressed ? nextRankDamageMaxValue : damageMaxValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            tooltip.add(Component.translatable("talents.focused_strikes.description1")//Change this value for each talent
                    .append(damageComponent)
                    .append(Component.translatable("talents.focused_strikes.description2"))//Change this value for each talent
                    .append(hitComponent)
                    .append(Component.translatable("talents.focused_strikes.description3"))
                    .append(damageMaxComponent));
            tooltip.add(Component.literal(" "));
            tooltip.add(Component.translatable("talents.focused_strikes.description4"));
            if (!isShiftPressed) {
                tooltip.add(Component.translatable("talents.press_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            } else {
                tooltip.add(Component.translatable("talents.release_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            }
        } else if (currentPoints == maxPoints){
            Component damageComponent = Component.literal((damageIncreaseValue) + " ");
            Component hitComponent = Component.literal((hitCountValue) + " ");
            Component damageMaxComponent = Component.literal("+" + (damageMaxValue) + " ");
            tooltip.add(Component.translatable("talents.focused_strikes.description1")//Change this value for each talent
                    .append(damageComponent)
                    .append(Component.translatable("talents.focused_strikes.description2"))//Change this value for each talent
                    .append(hitComponent)
                    .append(Component.translatable("talents.focused_strikes.description3"))
                    .append(damageMaxComponent));
            tooltip.add(Component.literal(" "));
            tooltip.add(Component.translatable("talents.focused_strikes.description4"));
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }
}
