package com.l33tfox.gliding.networking.packet;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.util.GliderUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record GliderActivatedC2SPacket(boolean isGliderActivated, boolean isGliding) implements CustomPayload {

    public static final Identifier GLIDING_ID = Identifier.of(Gliding.MOD_ID, "glidingc2s");

    public static final CustomPayload.Id<GliderActivatedC2SPacket> ID =
            new CustomPayload.Id<>(GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, GliderActivatedC2SPacket> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, GliderActivatedC2SPacket::isGliderActivated, PacketCodecs.BOOL,
                    GliderActivatedC2SPacket::isGliding, GliderActivatedC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    // Method for receiving GliderActivatedC2SPackets (on server side)
    public void receive(ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();

        if (isGliding) {
            ((PlayerEntityDuck) player).gliding$setIsActivatingGlider(true);
            GliderUtil.playerGliderMovement(player);
            GliderUtil.resetFallDamage(player);
        }

        // send a packet to the clients of all other players tracking the player gliding
        for (ServerPlayerEntity otherPlayer : PlayerLookup.tracking(context.player()))
            ServerPlayNetworking.send(otherPlayer, new OtherPlayerGliderActivatedS2CPacket(player.getId(), isGliderActivated, isGliding));
    }
}
