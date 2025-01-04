package com.l33tfox.gliding.networking;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.networking.payload.GliderActivatedC2SPayload;
import com.l33tfox.gliding.networking.payload.OtherPlayerGliderActivatedS2CPayload;
import com.l33tfox.gliding.util.GliderUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class C2SPacketHandler {

    // Method for receiving GliderActivatedC2SPackets (on server side)
    public static void receiveGliderActivated(GliderActivatedC2SPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();

        if (payload.isGliding()) {
            ((PlayerEntityDuck) player).gliding$setIsGliding(true);
            GliderUtil.playerGliderMovement(player);
            GliderUtil.resetFallDamage(player);
        } else {
            ((PlayerEntityDuck) player).gliding$setIsGliding(false);
        }

        // send a packet to the clients of all other players tracking the player gliding
        for (ServerPlayerEntity otherPlayer : PlayerLookup.tracking(context.player()))
            ServerPlayNetworking.send(otherPlayer, new OtherPlayerGliderActivatedS2CPayload(player.getId(),
            payload.isGliderActivated(), payload.isGliding()));
    }

}
