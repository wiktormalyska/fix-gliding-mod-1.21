package com.l33tfox.gliding.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
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

public class GliderItem extends Item {

    public GliderItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && (player.getMainHandStack().getItem() instanceof GliderItem
                || player.getOffHandStack().getItem() instanceof GliderItem)) {

            if (MinecraftClient.getInstance().options.jumpKey.isPressed())
                startGliding(player);
        }
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        startGliding(user);
//
//        return TypedActionResult.pass(user.getStackInHand(hand));
//    }

    public static void startGliding(PlayerEntity player) {
        Vec3d velocity = player.getVelocity();

        if (!player.isOnGround() && !player.isSwimming() && velocity.y < 0) {

            player.setVelocity(velocity.x * 1.05, -0.15, velocity.z * 1.05);
        }
    }
}
