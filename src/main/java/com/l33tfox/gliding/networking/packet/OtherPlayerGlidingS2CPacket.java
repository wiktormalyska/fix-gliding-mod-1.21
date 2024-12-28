package com.l33tfox.gliding.networking.packet;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.IPlayerGlidingMixin;
import com.l33tfox.gliding.util.GliderUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OtherPlayerGlidingS2CPacket(int otherPlayerID, boolean isActivatingGlider) implements CustomPayload {

    public static final Identifier OTHER_PLAYER_GLIDING_ID = Identifier.of(Gliding.MOD_ID, "otherplayerglidings2c");

    public static final CustomPayload.Id<OtherPlayerGlidingS2CPacket> ID =
            new CustomPayload.Id<>(OTHER_PLAYER_GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, OtherPlayerGlidingS2CPacket> CODEC =
            PacketCodec.tuple(PacketCodecs.INTEGER, OtherPlayerGlidingS2CPacket::otherPlayerID, PacketCodecs.BOOL,
            OtherPlayerGlidingS2CPacket::isActivatingGlider, OtherPlayerGlidingS2CPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        OtherClientPlayerEntity otherPlayer = (OtherClientPlayerEntity) context.client().world.getEntityById(otherPlayerID);

        if (otherPlayer != null) {
            if (isActivatingGlider)
                ((IPlayerGlidingMixin) otherPlayer).setIsActivatingGlider(true);
            else ((IPlayerGlidingMixin) otherPlayer).setIsActivatingGlider(false);
        }
    }
}
