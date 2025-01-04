package com.l33tfox.gliding.networking;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.client.sound.GliderSoundManager;
import com.l33tfox.gliding.client.sound.GlidingWindSoundInstance;
import com.l33tfox.gliding.networking.payload.OtherPlayerGliderActivatedS2CPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.OtherClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class S2CPacketHandler {

    // Method for receiving OtherPlayerGliderActivatedS2CPackets (on client side)
    public static void receiveOtherGliderActivated(OtherPlayerGliderActivatedS2CPayload payload, ClientPlayNetworking.Context context) {
        OtherClientPlayerEntity otherPlayer = (OtherClientPlayerEntity) context.client().world.getEntityById(payload.otherPlayerID());

        if (otherPlayer != null) {
            ((PlayerEntityDuck) otherPlayer).gliding$setIsActivatingGlider(payload.isActivatingGlider());
            ((PlayerEntityDuck) otherPlayer).gliding$setIsGliding(payload.isPlayerGliding());
        }
    }
}
