package com.l33tfox.gliding.util;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.items.GliderItem;
import com.l33tfox.gliding.networking.packet.GliderDamageC2SPacket;
import com.l33tfox.gliding.networking.packet.GliderActivatedC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

/*
A bunch of client-side utility methods for using gliders.
 */

public class GliderClientUtil {

    public static int ticksUsingGlider = 0;

    public static boolean isActivatingGlider(ClientPlayerEntity player) {
        return GliderUtil.isHoldingGlider(player) && player.input.jumping && !player.getAbilities().flying;
    }

    public static boolean isUsingGliderMoreThanOneJump(ClientPlayerEntity player) {
        return ticksUsingGlider > 4;
    }

    public static boolean isGliding(ClientPlayerEntity player) {
        Vec3d velocity = player.getVelocity();

        return isActivatingGlider(player) && isUsingGliderMoreThanOneJump(player) && !player.isOnGround()
                && !player.isInFluid() && !player.isFallFlying() && velocity.y < 0;
    }

    // Called at the end of every tick on the client using a CLIENT_END_TICK event registered in GlidingClient class
    public static void glidingTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;

        // if player is in a world and is activating the glider
        if (player != null && GliderClientUtil.isActivatingGlider(player)) {
            ticksUsingGlider++;

            // if player is already gliding or in a state to glide
            if (GliderClientUtil.isGliding(player)) {
                // move the player on the client side
                GliderUtil.playerGliderMovement(player);
                GliderUtil.resetFallDamage(player);

                // update the player model on this client
                ((PlayerEntityDuck) player).setIsActivatingGlider(true);

                // send a packet to the server so that it can move the player on the server side as well, and also
                // send packets to nearby players' clients who are tracking this player to update the player model
                ClientPlayNetworking.send(new GliderActivatedC2SPacket(true, true));

                // check if the player has been using glider for 20 consecutive ticks (1 second)
                if (ticksUsingGlider % 20 == 0)
                    // send a packet to the server so that it can update the glider's durability
                    ClientPlayNetworking.send(new GliderDamageC2SPacket(true));
            }
        // if the player exists and was previously activating glider but not anymore, send packets to update
        } else if (player != null && ((PlayerEntityDuck) player).isActivatingGlider()) {
            ticksUsingGlider = 0;
            ((PlayerEntityDuck) player).setIsActivatingGlider(false);
            ClientPlayNetworking.send(new GliderActivatedC2SPacket(false, false));
        }
    }
}
