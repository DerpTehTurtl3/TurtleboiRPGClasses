package net.turtleboi.turtlerpgclasses.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.turtleboi.turtlecore.event.CooldownResetEvent;
import net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.active.ExecuteTalent;

public class CooldownResetListener {

    @SubscribeEvent
    public void onCooldownReset(CooldownResetEvent event) {
        Player player = event.getPlayer();
        ExecuteTalent executeTalent = new ExecuteTalent();
        executeTalent.resetAbilityCooldown(player);
    }
}
