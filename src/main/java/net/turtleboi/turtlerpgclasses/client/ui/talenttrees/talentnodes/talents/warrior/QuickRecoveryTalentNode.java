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

public class QuickRecoveryTalentNode extends StatTalentButton {
    public QuickRecoveryTalentNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        double currentPoints = getCurrentPoints();
        double[] cooldownReductionValues = {5.0, 10.0, 15.0, 20.0};
        int currentRankIndex = (int) Math.max(0, Math.min(currentPoints - 1, cooldownReductionValues.length - 1));
        double cooldownReductionValue = cooldownReductionValues[currentRankIndex];
        double nextRankCooldownReductionValue = currentRankIndex < cooldownReductionValues.length - 1 ? cooldownReductionValues[currentRankIndex + 1] : cooldownReductionValue;
        boolean isShiftPressed = Screen.hasShiftDown();

        tooltip.add(Component.translatable("talents.quick_recovery")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.stat")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(Component.literal(" +5.0%/10.0%/15.0%/20.0% ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.quick_recovery.description")));//Change this value for each talent
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints < maxPoints) {
            Component pointsComponent = Component.literal(" +" + (isShiftPressed ? nextRankCooldownReductionValue : cooldownReductionValue) + "% ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(pointsComponent)
                    .append(Component.translatable("talents.quick_recovery.description")));//Change this value for each talent
            if (!isShiftPressed) {
                tooltip.add(Component.translatable("talents.press_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            } else {
                tooltip.add(Component.translatable("talents.release_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            }
        } else if (currentPoints == maxPoints){
            Component pointsComponent = Component.literal(" +" + cooldownReductionValue + "% ");
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(pointsComponent)
                    .append(Component.translatable("talents.quick_recovery.description")));//Change this value for each talent
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }
}
