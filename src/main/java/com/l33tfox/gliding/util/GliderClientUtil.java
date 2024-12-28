package com.l33tfox.gliding.util;

import com.l33tfox.gliding.IPlayerGlidingMixin;
import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GlidingC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

/*
A bunch of client-side utility methods for using gliders.
 */

public class GliderClientUtil {

    public static int ticksGlidingContinuously = 0;
    public static int ticksUsingGlider = 0;

    public static boolean isActivatingGlider(ClientPlayerEntity player) {
        return GliderUtil.isHoldingGlider(player) && player.input.jumping;
    }

    public static boolean isUsingGliderMoreThanOneJump(PlayerEntity player) {
        return ticksUsingGlider > 4;
    }

    public static boolean isGliding(ClientPlayerEntity player) {
        Vec3d velocity = player.getVelocity();

        return isActivatingGlider(player) && !player.isOnGround() && !player.isInFluid() && !player.isFallFlying()
                && velocity.y < 0;
    }

    // Called at the end of every tick on the client using a CLIENT_END_TICK event registered in GlidingClient class
    public static void glidingTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;

        if (player != null && GliderClientUtil.isActivatingGlider(player)) {
            ticksUsingGlider++;
            ((IPlayerGlidingMixin) player).setIsActivatingGlider(true);
        }
        else {
            ticksUsingGlider = 0;
            if (player != null)
                ((IPlayerGlidingMixin) player).setIsActivatingGlider(false);
        }

        // if player is in a world and is already gliding or in a state to glide
        if (player != null && GliderClientUtil.isGliding(player)) {
            // move the player on the client side
            GliderUtil.playerGliderMovement(player);
            GliderUtil.resetFallDamage(player);

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
