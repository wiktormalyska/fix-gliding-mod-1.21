package com.l33tfox.gliding.items;

import com.l33tfox.gliding.Gliding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GliderItem extends ToolItem {

    private long ticksGlidingContinuously;
    private static final double GLIDE_DROP_SPEED = -0.15;
    private static final double GLIDE_SPEED_INCREASE_FACTOR = 1.06;

    public GliderItem(ToolMaterial material, Settings settings) {
        super(material, settings);

        ticksGlidingContinuously = 0;
    }

    // Called every tick for every GliderItem stack in player inventory on both client and server side
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // do nothing if entity is not a player
        if (!(entity instanceof PlayerEntity player))
            return;

        boolean gliderInOffHandOnly = player.getOffHandStack() == stack &&
                !(player.getMainHandStack().getItem() instanceof GliderItem);

        // if the current GliderItem is selected in main or off hand. statement is false if player is holding a
        // GliderItem stack in both hands, and the current stack is the one in the off hand (to prevent speed
        // multipliers stacking when holding two gliders)
        if ((selected && player.getMainHandStack() == stack) || gliderInOffHandOnly) {

            Vec3d velocity = player.getVelocity();
            boolean jumpKeyPressed = MinecraftClient.getInstance().options.jumpKey.isPressed();

            // ensures that player is holding jump key, in air, not in fluid, and falling to start or continue gliding
            if (jumpKeyPressed && !player.isOnGround() && !player.isInFluid() && velocity.y < 0) {
                startGliding(player);

                // on server side, increments tick counter and checks if a second (20 ticks) of gliding has passed
                if (!world.isClient() && ticksGlidingContinuously++ != 0 && ticksGlidingContinuously % 20 == 0) {
                    if (selected)
                        player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
                    else if (gliderInOffHandOnly)
                        player.getOffHandStack().damage(1, player, EquipmentSlot.OFFHAND);
                }

            } else ticksGlidingContinuously = 0; // resets tick counter if player stops trying to glide
        }
    }

    private void startGliding(PlayerEntity player) {
        player.setVelocity(player.getVelocity().x * GLIDE_SPEED_INCREASE_FACTOR, GLIDE_DROP_SPEED,
                player.getVelocity().z * GLIDE_SPEED_INCREASE_FACTOR);
        player.fallDistance = 0;
    }
}
