package net.turtleboi.turtlerpgclasses.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.capabilities.party.PlayerPartyProvider;

public class PartyUtils {
    public static void invitePlayer(ServerPlayer inviter, ServerPlayer invitee) {
        invitee.sendSystemMessage(Component.literal(inviter.getName().getString() + " has invited you to their party."));
    }

    public static void acceptInvite(ServerPlayer invitee, ServerPlayer inviter) {
        inviter.getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(party -> {
            party.addMember(invitee.getUUID());
            invitee.getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(inviteeParty -> {
                inviteeParty.addMember(inviter.getUUID());
                inviteeParty.addMember(invitee.getUUID());
            });
            invitee.sendSystemMessage(Component.literal("You have joined " + inviter.getName().getString() + "'s party."));
            inviter.sendSystemMessage(Component.literal(invitee.getName().getString() + " has joined your party."));
        });
    }

    public static void leaveParty(ServerPlayer player) {
        player.getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(party -> {
            party.getPartyMembers().forEach(memberUUID -> {
                ServerPlayer member = player.server.getPlayerList().getPlayer(memberUUID);
                if (member != null) {
                    member.getCapability(PlayerPartyProvider.PLAYER_PARTY).ifPresent(memberParty -> {
                        memberParty.removeMember(player.getUUID());
                    });
                    member.sendSystemMessage(Component.literal(player.getName().getString() + " has left the party."));
                }
            });
            party.getPartyMembers().clear();
        });
    }

    public static boolean isAlly(Player player, Entity target) {
        if (target instanceof Player) {
            return player.getCapability(PlayerPartyProvider.PLAYER_PARTY)
                    .map(party -> party.isMember(target.getUUID()))
                    .orElse(false);
        }
        return false;
    }

}
