package com.l33tfox.gliding.util;

import com.l33tfox.gliding.items.GliderItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/*
A bunch of server-side utility methods for using gliders.
 */

public class GliderUtil {

    public static boolean isHoldingGlider(PlayerEntity player) {
        return player.isHolding(itemStack -> itemStack.getItem() instanceof GliderItem);
    }

    public static boolean offHandHoldingGlider(PlayerEntity player) {
        return player.getOffHandStack().getItem() instanceof GliderItem;
    }

    public static boolean mainHandHoldingGlider(PlayerEntity player) {
        return player.getMainHandStack().getItem() instanceof GliderItem;
    }

    public static void playerGliderMovement(PlayerEntity player) {
        player.setVelocity(player.getVelocity().x * GliderItem.GLIDE_SPEED_INCREASE_FACTOR,
                GliderItem.GLIDE_DROP_SPEED, player.getVelocity().z * GliderItem.GLIDE_SPEED_INCREASE_FACTOR);
    }

    public static void resetFallDamage(PlayerEntity player) {
        player.fallDistance = 0;
    }

}