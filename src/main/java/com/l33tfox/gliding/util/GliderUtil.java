package com.l33tfox.gliding.util;

import com.l33tfox.gliding.items.GliderItem;
import net.minecraft.entity.player.PlayerEntity;

/*
A bunch of utility methods for using gliders.
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

    public static GliderItem getGliderItemInHand(PlayerEntity player) {
        GliderItem gliderItem = null;

        if (mainHandHoldingGlider(player))
            gliderItem = (GliderItem) player.getMainHandStack().getItem();
        else if (offHandHoldingGlider(player))
            gliderItem = (GliderItem) player.getOffHandStack().getItem();

        return gliderItem;
    }

    public static void playerGliderMovement(PlayerEntity player) {
        GliderItem gliderInHand = GliderUtil.getGliderItemInHand(player);
        double newYVelocity = gliderInHand.glideDropVelocity;

        // only clamp the y velocity if the player was falling faster than the set glider velocity
        if (player.getVelocity().y > gliderInHand.glideDropVelocity)
            newYVelocity = player.getVelocity().y;

        player.setVelocity(player.getVelocity().x * gliderInHand.glideSpeedIncreaseFactor,
        newYVelocity, player.getVelocity().z * gliderInHand.glideSpeedIncreaseFactor);
    }

    public static void resetFallDamage(PlayerEntity player) {
        player.fallDistance = 0;
    }

}
