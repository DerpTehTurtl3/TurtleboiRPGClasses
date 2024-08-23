package net.turtleboi.turtlerpgclasses.client.ui.talenttrees;

import com.mojang.blaze3d.vertex.PoseStack;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.TalentButton;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.talents.ranger.rangerClassNode;
import net.turtleboi.turtlerpgclasses.rpg.classes.Ranger;

public class RangerTalentTree extends TalentTree {
    public RangerTalentTree(int posX, int posY, int width, int height, TalentScreen talentScreen) {
        super(posX, posY, width, height, true, talentScreen);
    }

    @Override
    protected void setupTalentButtons() {
        int centerX = posX + (width / 2) - 13;
        int startY = posY + 13;
        String className = ClientClassData.getPlayerClass();
        rangerClassNode rangerClassNode = new rangerClassNode(
                this,
                new Ranger(),
                centerX,
                startY,
                1,
                0,
                ("Ranger".equals(className)),
                button -> {});
        createTalentButton(rangerClassNode, TalentButton.TalentState.ACTIVE);
    }

    @Override
    protected void drawConnectionsTexture(PoseStack poseStack) {

    }
}

