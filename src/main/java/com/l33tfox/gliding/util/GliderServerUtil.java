package com.l33tfox.gliding.util;

import com.l33tfox.gliding.items.GliderItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/*
A bunch of server-side utility methods for using gliders.
 */

public class GliderServerUtil {

    public static void playerGliderMovement(ServerPlayerEntity player) {
        player.setVelocity(player.getVelocity().x * GliderItem.GLIDE_SPEED_INCREASE_FACTOR,
                GliderItem.GLIDE_DROP_SPEED, player.getVelocity().z * GliderItem.GLIDE_SPEED_INCREASE_FACTOR);
    }

    public static void resetFallDamage(ServerPlayerEntity player) {
        player.fallDistance = 0;
    }
}
