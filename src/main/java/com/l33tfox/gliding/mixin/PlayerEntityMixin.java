package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.PlayerEntityDuck;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityDuck {

    @Unique
    private boolean isGliding;
    @Unique
    private boolean isActivatingGlider;

    public boolean gliding$isGliding() {
        return isGliding;
    }

    public void gliding$setIsGliding(boolean isPlayerGliding) {
        isGliding = isPlayerGliding;
    }

    public boolean gliding$isActivatingGlider() {
        return isActivatingGlider;
    }

    public void gliding$setIsActivatingGlider(boolean isPlayerActivatingGlider) {
        isActivatingGlider = isPlayerActivatingGlider;
    }
}
