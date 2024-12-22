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

    public GliderItem(ToolMaterial material, Settings settings) {
        super(material, settings);

        ticksGlidingContinuously = 0;
    }

    // Called every tick for every GliderItem in player inventory on both client and server side
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // if the current GliderItem is selected and the entity is a player
        if (selected && entity instanceof PlayerEntity player) {
            boolean gliderInMainHand = player.getMainHandStack().getItem() instanceof GliderItem;
            boolean gliderInOffHand = player.getOffHandStack().getItem() instanceof GliderItem;

            Vec3d velocity = player.getVelocity();
            boolean jumpKeyPressed = MinecraftClient.getInstance().options.jumpKey.isPressed();

            // ensures that player is holding jump key, in air, not in fluid, and falling to start or continue gliding
            if (jumpKeyPressed && !player.isOnGround() && !player.isInFluid() && velocity.y < 0) {
                startGliding(player);

                // on server side, increments tick counter and checks if a second (20 ticks) of gliding has passed
                if (!world.isClient() && ticksGlidingContinuously++ != 0 && ticksGlidingContinuously % 20 == 0) {
                    if (gliderInMainHand)
                        player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
                    else if (gliderInOffHand)
                        player.getOffHandStack().damage(1, player, EquipmentSlot.OFFHAND);
                }

            } else ticksGlidingContinuously = 0; // resets tick counter if player stops trying to glide
        }
    }

    private void startGliding(PlayerEntity player) {
        player.setVelocity(player.getVelocity().x * 1.025, -0.15, player.getVelocity().z * 1.025);
        player.fallDistance = 0;
    }
}
