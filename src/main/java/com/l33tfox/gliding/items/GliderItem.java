package com.l33tfox.gliding.items;

import com.l33tfox.gliding.mechanics.GlidingMechanic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GliderItem extends Item {

    public GliderItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        GlidingMechanic.toggleGliding(user);

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
