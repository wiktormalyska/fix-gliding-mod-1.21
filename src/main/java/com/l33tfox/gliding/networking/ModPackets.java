package com.l33tfox.gliding.networking;

import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GliderActivatedC2SPacket;
import com.l33tfox.gliding.networking.packet.OtherPlayerGliderActivatedS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPackets {

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(GliderActivatedC2SPacket.ID, GliderActivatedC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(GliderDamageC2SPacket.ID, GliderDamageC2SPacket.CODEC);
    }

    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(GliderDamageC2SPacket.ID, GliderDamageC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GliderActivatedC2SPacket.ID, GliderActivatedC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(OtherPlayerGliderActivatedS2CPacket.ID, OtherPlayerGliderActivatedS2CPacket.CODEC);
    }

    public static void registerS2CReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(OtherPlayerGliderActivatedS2CPacket.ID, OtherPlayerGliderActivatedS2CPacket::receive);
    }
}
