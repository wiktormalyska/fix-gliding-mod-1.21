package com.l33tfox.gliding.mechanics;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.items.GliderItem;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;

public class GlidingMechanic {

    public static void toggleGliding(PlayerEntity player) {
        Vec3d velocity = player.getVelocity();

        if (!player.isOnGround() && !player.isSwimming() && velocity.y < 0)
            player.setVelocity(velocity.x, -0.2 * velocity.y, velocity.z);
        else player.setVelocity(velocity.x, velocity.y, velocity.z);
    }
}
