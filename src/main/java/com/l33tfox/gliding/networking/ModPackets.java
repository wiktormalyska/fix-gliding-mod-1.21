package com.l33tfox.gliding.networking;

import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GlidingC2SPacket;
import com.l33tfox.gliding.networking.packet.OtherPlayerGlidingS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ModPackets {

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(GlidingC2SPacket.ID, GlidingC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(GliderDamageC2SPacket.ID, GliderDamageC2SPacket.CODEC);
    }

    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(GliderDamageC2SPacket.ID, GliderDamageC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GlidingC2SPacket.ID, GlidingC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(OtherPlayerGlidingS2CPacket.ID, OtherPlayerGlidingS2CPacket.CODEC);
    }

    public static void registerS2CReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(OtherPlayerGlidingS2CPacket.ID, OtherPlayerGlidingS2CPacket::receive);
    }
}
