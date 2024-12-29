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

    public void receive(ServerPlayNetworking.Context context) {
        if (isGliding) {
            ((PlayerEntityDuck) context.player()).setIsActivatingGlider(true);
            GliderUtil.playerGliderMovement(context.player());
            GliderUtil.resetFallDamage(context.player());
            Gliding.LOGGER.info("GlidingC2SPacket received. player: " + context.player());
        }

        for (ServerPlayerEntity player : PlayerLookup.tracking(context.player()))
            ServerPlayNetworking.send(player, new OtherPlayerGliderActivatedS2CPacket(context.player().getId(), isGliderActivated));
    }
}
