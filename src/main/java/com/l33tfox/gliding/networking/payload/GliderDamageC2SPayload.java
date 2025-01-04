package com.l33tfox.gliding.networking.payload;

import com.l33tfox.gliding.Gliding;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GliderDamageC2SPayload(boolean damageGlider) implements CustomPayload {

    public static final Identifier GLIDING_ID = Identifier.of(Gliding.MOD_ID, "gliderdamagec2s");

    public static final Id<GliderDamageC2SPayload> ID =
            new Id<>(GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, GliderDamageC2SPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, GliderDamageC2SPayload::damageGlider, GliderDamageC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
