package net.turtleboi.turtlerpgclasses.client.ui.talenttrees;

import com.mojang.blaze3d.vertex.PoseStack;

public class RangerTalentTree extends TalentTree {
    public RangerTalentTree(int posX, int posY, int width, int height, TalentScreen talentScreen) {
        super(posX, posY, width, height, true, talentScreen);
    }
    @Override
    protected void setupTalentButtons() {

    }

    @Override
    protected void drawConnectionsTexture(PoseStack poseStack) {

    }
}

