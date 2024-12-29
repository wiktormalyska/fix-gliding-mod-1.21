package com.l33tfox.gliding.networking.packet;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.PlayerEntityDuck;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OtherPlayerGliderActivatedS2CPacket(int otherPlayerID, boolean isActivatingGlider) implements CustomPayload {

    public static final Identifier OTHER_PLAYER_GLIDING_ID = Identifier.of(Gliding.MOD_ID, "otherplayerglidings2c");

    public static final CustomPayload.Id<OtherPlayerGliderActivatedS2CPacket> ID =
            new CustomPayload.Id<>(OTHER_PLAYER_GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, OtherPlayerGliderActivatedS2CPacket> CODEC =
            PacketCodec.tuple(PacketCodecs.INTEGER, OtherPlayerGliderActivatedS2CPacket::otherPlayerID, PacketCodecs.BOOL,
            OtherPlayerGliderActivatedS2CPacket::isActivatingGlider, OtherPlayerGliderActivatedS2CPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        OtherClientPlayerEntity otherPlayer = (OtherClientPlayerEntity) context.client().world.getEntityById(otherPlayerID);

        if (otherPlayer != null) {
            ((PlayerEntityDuck) otherPlayer).setIsActivatingGlider(isActivatingGlider);
        }
    }
}
