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

public class BrawlersTenacityTalentNode extends PassiveTalentButton {
    public BrawlersTenacityTalentNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        double currentPoints = getCurrentPoints();
        double[] armorGainPerHitValues = {1.0, 1.0, 2.0, 2.0, 3.0};
        double[] maxArmorValues = {3.0, 4.0, 8.0, 12.0, 18.0};
        int currentRankIndex = (int) Math.max(0, Math.min(currentPoints - 1, armorGainPerHitValues.length - 1));
        double armorGainPerHitValue = armorGainPerHitValues[currentRankIndex];
        double maxArmorValue = maxArmorValues[currentRankIndex];
        double nextRankArmorGainPerHitValue = currentRankIndex < armorGainPerHitValues.length - 1 ? armorGainPerHitValues[currentRankIndex + 1] : armorGainPerHitValue;
        double nextRankMaxArmorValue = currentRankIndex < maxArmorValues.length - 1 ? maxArmorValues[currentRankIndex + 1] : maxArmorValue;
        boolean isShiftPressed = Screen.hasShiftDown();

        tooltip.add(Component.translatable("talents.brawlers_tenacity")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.passive")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.brawlers_tenacity1")
                    .append(Component.literal(" +1.0/1.0/2.0/2.0/3.0 ")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.brawlers_tenacity2"))
                    .append(Component.literal(" +5.0/6.0/8.0/12.0/18.0")
                            .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00FF00"))))
                    .append(Component.translatable("talents.brawlers_tenacity3")));
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints < maxPoints) {
            Component armorComponent = Component.literal(" +" + (isShiftPressed ? nextRankArmorGainPerHitValue : armorGainPerHitValue) + " ")
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            Component armorMaxComponent = Component.literal(" +" + (isShiftPressed ? nextRankMaxArmorValue : maxArmorValue))
                    .withStyle(isShiftPressed ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) : Style.EMPTY);
            tooltip.add(Component.translatable("talents.brawlers_tenacity1")
                    .append(armorComponent)
                    .append(Component.translatable("talents.brawlers_tenacity2"))
                    .append(armorMaxComponent)
                    .append(Component.translatable("talents.brawlers_tenacity3")));
            if (!isShiftPressed) {
                tooltip.add(Component.translatable("talents.press_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            } else {
                tooltip.add(Component.translatable("talents.release_shift")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
            }
        } else if (currentPoints == maxPoints){
            Component armorComponent = Component.literal((" +" + armorGainPerHitValue) + " ");
            Component armorMaxComponent = Component.literal((" +" + maxArmorValue));
            tooltip.add(Component.translatable("talents.brawlers_tenacity1")
                    .append(armorComponent)
                    .append(Component.translatable("talents.brawlers_tenacity2"))
                    .append(armorMaxComponent)
                    .append(Component.translatable("talents.brawlers_tenacity3")));
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }
}
