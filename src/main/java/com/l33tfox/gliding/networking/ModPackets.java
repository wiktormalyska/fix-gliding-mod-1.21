package com.l33tfox.gliding.networking;

import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GlidingC2SPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPackets {

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(GlidingC2SPacket.ID, GlidingC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(GlidingC2SPacket.ID, GlidingC2SPacket::receive);

        PayloadTypeRegistry.playC2S().register(GliderDamageC2SPacket.ID, GliderDamageC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(GliderDamageC2SPacket.ID, GliderDamageC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
