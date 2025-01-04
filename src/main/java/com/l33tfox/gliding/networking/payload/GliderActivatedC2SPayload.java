package com.l33tfox.gliding.networking.payload;

import com.l33tfox.gliding.Gliding;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GliderActivatedC2SPayload(boolean isGliderActivated, boolean isGliding) implements CustomPayload {

    public static final Identifier GLIDING_ID = Identifier.of(Gliding.MOD_ID, "glidingc2s");

    public static final CustomPayload.Id<GliderActivatedC2SPayload> ID =
            new CustomPayload.Id<>(GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, GliderActivatedC2SPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, GliderActivatedC2SPayload::isGliderActivated, PacketCodecs.BOOL,
                    GliderActivatedC2SPayload::isGliding, GliderActivatedC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
