package com.l33tfox.gliding.networking.packet;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.items.GliderItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record GliderDamageC2SPacket(boolean damageGlider) implements CustomPayload {

    public static final Identifier GLIDING_ID = Identifier.of(Gliding.MOD_ID, "gliderdamagec2s");

    public static final Id<GliderDamageC2SPacket> ID =
            new Id<>(GLIDING_ID);

    public static final PacketCodec<RegistryByteBuf, GliderDamageC2SPacket> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, GliderDamageC2SPacket::damageGlider, GliderDamageC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    // Method for receiving GliderDamageC2SPackets (on server side)
    public void receive(ServerPlayNetworking.Context context) {
        if (damageGlider) {
            ServerPlayerEntity player = context.player();

            if (player.getMainHandStack().getItem() instanceof GliderItem)
                player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
            else if (player.getOffHandStack().getItem() instanceof GliderItem)
                player.getOffHandStack().damage(1, player, EquipmentSlot.OFFHAND);
        }
    }
}
