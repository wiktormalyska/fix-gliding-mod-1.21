package com.l33tfox.gliding.client.sound;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.PlayerEntityDuck;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.ElytraSoundInstance;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class GlidingWindSoundInstance extends MovingSoundInstance {

    public PlayerEntity glidingPlayer;
    private int tickCount;
    GliderSoundManager soundManager;

    public GlidingWindSoundInstance(PlayerEntity player, GliderSoundManager manager) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS, SoundInstance.createRandom());

        glidingPlayer = player;
        repeat = true;
        repeatDelay = 0;
        volume = 0.0F;
        soundManager = manager;
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    // almost the same as ElytraSoundInstance's tick method
    @Override
    public void tick() {
        tickCount++;

        if (!glidingPlayer.isRemoved() && ((PlayerEntityDuck) glidingPlayer).gliding$isGliding()) {
            setPositionToPlayer();
            float velocitySquared = (float) glidingPlayer.getVelocity().lengthSquared();
            if ((double) velocitySquared >= 1.0E-7) {
                this.volume = MathHelper.clamp(velocitySquared / 8.0F, 0.0F, 1.0F);
            } else {
                this.volume = 0.0F;
            }

            if (tickCount < 20) {
                volume = 0.0F;
            } else if (tickCount < 40) {
                volume = volume * ((float)(tickCount - 20) / 20.0F);
            }

            if (volume > 0.8F) {
                pitch = 1.0F + (volume - 0.8F);
            } else {
                pitch = 1.0F;
            }
        } else {
            setDone();
            soundManager.onSoundFinished(this);
        }
    }

    private void setPositionToPlayer() {
        x = glidingPlayer.getX();
        y = glidingPlayer.getY();
        z = glidingPlayer.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GlidingWindSoundInstance && ((GlidingWindSoundInstance) obj).glidingPlayer == glidingPlayer;
    }
}
