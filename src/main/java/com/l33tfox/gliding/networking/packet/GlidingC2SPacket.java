package com.l33tfox.gliding.networking.packet;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.util.GliderServerUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GlidingC2SPacket(boolean isGliding) implements CustomPayload {

    public static final Identifier GLIDING_ID = Identifier.of(Gliding.MOD_ID, "glidingc2s");

    public static final CustomPayload.Id<GlidingC2SPacket> ID =
            new CustomPayload.Id<>(GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, GlidingC2SPacket> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, GlidingC2SPacket::isGliding, GlidingC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ServerPlayNetworking.Context context) {
        if (isGliding) {
            GliderServerUtil.playerGliderMovement(context.player());
            GliderServerUtil.resetFallDamage(context.player());
        }
    }
}
