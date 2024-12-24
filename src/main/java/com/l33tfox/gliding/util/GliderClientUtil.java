package com.l33tfox.gliding.util;

import com.l33tfox.gliding.items.GliderItem;
import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GlidingC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

/*
A bunch of client-side utility methods for using gliders.
 */

public class GliderClientUtil {

    public static int ticksGlidingContinuously = 0;

    public static boolean isHoldingGlider(ClientPlayerEntity player) {
        return player.isHolding(itemStack -> itemStack.getItem() instanceof GliderItem);
    }

    public static boolean isUsingGlider(ClientPlayerEntity player) {
        return isHoldingGlider(player) && player.input.jumping;
    }

    public static boolean isGliding(ClientPlayerEntity player) {
        Vec3d velocity = player.getVelocity();

        return isUsingGlider(player) && !player.isOnGround() && !player.isInFluid() && velocity.y < 0;
    }

    public static void playerGliderMovement(PlayerEntity player) {
        player.setVelocity(player.getVelocity().x * GliderItem.GLIDE_SPEED_INCREASE_FACTOR,
                GliderItem.GLIDE_DROP_SPEED, player.getVelocity().z * GliderItem.GLIDE_SPEED_INCREASE_FACTOR);
    }

    public static void resetFallDamage(ServerPlayerEntity player) {
        player.fallDistance = 0;
    }

    // Called at the end of every tick on the client using a CLIENT_END_TICK event registered in GlidingClient class
    public static void glidingTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;

        // if player is in a world and is already gliding or in a state to glide
        if (player != null && GliderClientUtil.isGliding(player)) {
            // move the player on the client side
            GliderClientUtil.playerGliderMovement(player);

            // send a packet to the server so that it can move the player on the server side as well
            ClientPlayNetworking.send(new GlidingC2SPacket(true));
            ticksGlidingContinuously++;

            // check if the player has been gliding for 20 consecutive ticks (1 second)
            if (ticksGlidingContinuously % 20 == 0)
                // send a packet to the server so that it can update the glider's durability
                ClientPlayNetworking.send(new GliderDamageC2SPacket(true));
        } else ticksGlidingContinuously = 0;
    }
}
