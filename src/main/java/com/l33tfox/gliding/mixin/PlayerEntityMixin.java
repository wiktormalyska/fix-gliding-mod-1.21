package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.IPlayerGlidingMixin;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerGlidingMixin {

    @Unique
    private boolean isGliding;
    @Unique
    private boolean isActivatingGlider;

    public boolean isGliding() {
        return isGliding;
    }

    public void setIsGliding(boolean isPlayerGliding) {
        isGliding = isPlayerGliding;
    }

    public boolean isActivatingGlider() {
        return isActivatingGlider;
    }

    public void setIsActivatingGlider(boolean isPlayerActivatingGlider) {
        isActivatingGlider = isPlayerActivatingGlider;
    }
}
