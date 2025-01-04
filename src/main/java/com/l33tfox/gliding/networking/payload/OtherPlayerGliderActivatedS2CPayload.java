package com.l33tfox.gliding.networking.payload;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.client.sound.GliderSoundManager;
import com.l33tfox.gliding.client.sound.GlidingWindSoundInstance;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OtherPlayerGliderActivatedS2CPayload(int otherPlayerID, boolean isActivatingGlider, boolean isPlayerGliding) implements CustomPayload {

    public static final Identifier OTHER_PLAYER_GLIDING_ID = Identifier.of(Gliding.MOD_ID, "otherplayerglidings2c");

    public static final CustomPayload.Id<OtherPlayerGliderActivatedS2CPayload> ID =
            new CustomPayload.Id<>(OTHER_PLAYER_GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, OtherPlayerGliderActivatedS2CPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.INTEGER, OtherPlayerGliderActivatedS2CPayload::otherPlayerID, PacketCodecs.BOOL,
            OtherPlayerGliderActivatedS2CPayload::isActivatingGlider, PacketCodecs.BOOL,
            OtherPlayerGliderActivatedS2CPayload::isPlayerGliding, OtherPlayerGliderActivatedS2CPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
