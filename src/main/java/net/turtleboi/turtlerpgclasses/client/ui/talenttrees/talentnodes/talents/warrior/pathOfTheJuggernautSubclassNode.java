package net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.talents.warrior;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.BarbarianTalentTree;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.JuggernautTalentTree;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentScreen;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentTree;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.ActiveTalentButton;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.SteelBarbsTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.UnleashFuryTalent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class pathOfTheJuggernautSubclassNode extends ActiveTalentButton {
    private final TalentTree talentTree;

    public pathOfTheJuggernautSubclassNode(TalentTree talentTree, Talent talent, int x, int y, int maxPoints, int requiredPoints, boolean alwaysActive, OnPress onPress) {
        super(talentTree, talent, x, y, maxPoints, requiredPoints, alwaysActive, onPress);
        this.talentTree = talentTree;
    }

    @Override
    public List<Component> generateDynamicTooltip() {
        List<Component> tooltip = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        double currentPoints = getCurrentPoints();
        SteelBarbsTalent talent = new SteelBarbsTalent();

        double armorValue = talent.getArmor();
        double durationValue = talent.getDurationSeconds();

        assert player != null;
        double cooldownReduction = player.getAttributeValue(ModAttributes.COOLDOWN_REDUCTION.get());
        double baseCooldown = talent.getCooldownSeconds(); // Base cooldown value in seconds
        double adjustedCooldown = baseCooldown * (cooldownReduction / 100.0);

        double baseCost = 0.0;
        Map<String, Integer> costs = talent.getResourceCosts(player);
        if (costs.containsKey("stamina")) {
            baseCost = costs.get("stamina").doubleValue();
        }

        MutableComponent costComponent = Component.literal((baseCost) + " ")
                .withStyle(currentPoints == 0 ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) :
                        Style.EMPTY.withColor(TextColor.parseColor("#AA0000")));
        MutableComponent cooldownComponent = Component.literal(String.format("%.1f", adjustedCooldown) + "s")
                .withStyle(currentPoints == 0 ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) :
                        Style.EMPTY.withColor(TextColor.parseColor("#00AAAA")));

        MutableComponent armorComponent = Component.literal((armorValue) + " ")
                .withStyle(currentPoints == 0 ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) :
                        Style.EMPTY);
        MutableComponent durationComponent = Component.literal((durationValue) + " ")
                .withStyle(currentPoints == 0 ? Style.EMPTY.withColor(TextColor.parseColor("#00FF00")) :
                        Style.EMPTY);

        tooltip.add(Component.translatable("talents.subclass.juggernaut")//Change this value for each talent
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.subclass")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        tooltip.add(Component.translatable("talents.subclass.juggernaut.description"));
        tooltip.add(Component.literal(" "));
        tooltip.add(Component.translatable("talents.subclass.juggernaut.steel_barbs")
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#FFD52B")))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.talent_type.active")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#808080")))
                ));
        tooltip.add(Component.translatable("talents.cost_stamina")
                .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#AA0000")))
                .append(costComponent)
                .append(Component.translatable("talents.cooldown")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00AAAA")))
                        .append(cooldownComponent)
                ));
        tooltip.add(Component.translatable("talents.subclass.juggernaut.steel_barbs1")
                .append(armorComponent)
                .append(Component.translatable("talents.armor_points"))
                .append(Component.literal(" "))
                .append(Component.translatable("for"))
                .append(Component.literal(" "))
                .append(durationComponent)
                .append(Component.translatable("seconds"))
                .append(Component.translatable("talents.subclass.juggernaut.steel_barbs2"))
                .append(Component.translatable("talents.spell_effect.thorns")
                        .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#00AA00"))))
                .append(Component.literal(" "))
                .append(Component.translatable("talents.subclass.juggernaut.steel_barbs3")
                ));
        if (currentPoints == 0) {
            tooltip.add(Component.translatable("talents.not_learned")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        } else if (currentPoints == maxPoints){
            tooltip.add(Component.translatable("talents.max_rank")
                    .withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555"))));
        }
        return tooltip;
    }

    @Override
    public void onLeftClick(double mouseX, double mouseY) {
        super.onLeftClick(mouseX, mouseY);
        Minecraft minecraft = Minecraft.getInstance();
            if (!(minecraft.screen instanceof TalentScreen talentScreen)) {
                return;
            }
        talentScreen.initializeTalentTree(JuggernautTalentTree.class, false);
    }

    @Override
    public void onRightClick(double mouseX, double mouseY) {
        super.onRightClick(mouseX, mouseY);
        Minecraft minecraft = Minecraft.getInstance();
            if (!(minecraft.screen instanceof TalentScreen talentScreen)) {
                return;
            }
        talentScreen.clearTalentTree(JuggernautTalentTree.class);
    }
}