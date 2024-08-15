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

public class MarathonerTalentNode extends StatTalentButton {
    private final TalentTree talentTree;

    public MarathonerTalentNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
        this.talentTree = talentTree;
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        double currentPoints = getCurrentPoints();
        double[] staminaValues = {2.0, 4.0, 6.0, 10.0};
        int currentRankIndex = (int) Math.max(0, Math.min(currentPoints - 1, staminaValues.length - 1));
        double staminaValue = staminaValues[currentRankIndex];
        double nextRankStaminaValue = currentRankIndex < staminaValues.length - 1 ? staminaValues[currentRankIndex + 1] : staminaValue;

        boolean isShiftPressed = Screen.hasShiftDown();

        tooltip.add(Component.translatable("talents.marathoner")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.stat")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(Component.literal(" +2.0/4.0/6.0/10.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.stamina")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#AA0000"))))
                    .append(Component.translatable("talents.points")));
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints < maxPoints) {
            Component pointsComponent = Component.literal(" +" + (isShiftPressed ? nextRankStaminaValue : staminaValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(pointsComponent)
                    .append(Component.translatable("talents.stamina")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#AA0000"))))
                    .append(Component.translatable("talents.points")));
            if (!isShiftPressed) {
                tooltip.add(Component.translatable("talents.press_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            } else {
                tooltip.add(Component.translatable("talents.release_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            }
        } else if (currentPoints == maxPoints){
            Component pointsComponent = Component.literal(" +" + staminaValue + " ");
            tooltip.add(Component.translatable("talents.player_gains")
                    .append(pointsComponent)
                    .append(Component.translatable("talents.stamina")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#AA0000"))))
                    .append(Component.translatable("talents.points")));
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }
}
