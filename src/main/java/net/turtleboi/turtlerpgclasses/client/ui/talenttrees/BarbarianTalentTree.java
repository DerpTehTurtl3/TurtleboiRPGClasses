package net.turtleboi.turtlerpgclasses.client.ui.talenttrees;

import com.mojang.blaze3d.vertex.PoseStack;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.talents.barbarian.barbarianSubclassNode;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.TalentButton;
import net.turtleboi.turtlerpgclasses.rpg.classes.subclasses.Barbarian;

public class BarbarianTalentTree extends TalentTree {
    public BarbarianTalentTree(int posX, int posY, int width, int height, TalentScreen talentScreen) {
        super(posX, posY, width, height, true, talentScreen);
    }

    @Override
    protected void drawConnectionsTexture(PoseStack poseStack) {

    }

    @Override
    protected void setupTalentButtons() {
        int centerX = posX + (width / 2) - 13;
        int startY = posY + 13;
        String subclassName = ClientClassData.getPlayerSubclass();
        barbarianSubclassNode barbarianSubclassNode = new barbarianSubclassNode(
                this,
                new Barbarian(),
                centerX,
                startY,
                1,
                0,
                ("Barbarian".equals(subclassName)),
                button -> {});
        createTalentButton(barbarianSubclassNode, TalentButton.TalentState.ACTIVE);
    }
}
