package com.l33tfox.gliding.client.sound;

import com.l33tfox.gliding.Gliding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Code (mostly) from Fabric documentation dynamic sounds page

public class GliderSoundManager {
    // An instance of the client to use Minecraft's default SoundManager
    private static final MinecraftClient client = MinecraftClient.getInstance();
    // static field to store the current instance for the Singleton Design Pattern
    private static GliderSoundManager instance;
    // The list which keeps track of all currently playing dynamic SoundInstances
    private final List<GlidingWindSoundInstance> activeSounds = new ArrayList<>();

    private GliderSoundManager() {
        // private constructor to make sure that the normal
        // instantiation of that object is not used externally
    }

    // when accessing this class for the first time a new instance
    // is created and stored. If this is called again only the already
    // existing instance will be returned, instead of creating a new instance
    public static GliderSoundManager getInstance() {
        if (instance == null) {
            instance = new GliderSoundManager();
        }

        return instance;
    }

    public void onSoundFinished(SoundInstance sound) {
        if (sound instanceof GlidingWindSoundInstance) {
            stop((GlidingWindSoundInstance) sound);
        }

    }

    // Plays a sound instance, if it doesn't already exist in the list
    public <T extends GlidingWindSoundInstance> void play(T soundInstance) {
        if (activeSounds.contains(soundInstance))
            return;

        client.getSoundManager().play(soundInstance);
        activeSounds.add(soundInstance);
    }

    // Stops a sound immediately. in most cases it is preferred to use
    // the sound's ending phase, which will clean it up after completion
    public <T extends GlidingWindSoundInstance> void stop(T soundInstance) {
        client.getSoundManager().stop(soundInstance);
        activeSounds.remove(soundInstance);
    }

    // Finds a SoundInstance from a SoundEvent, if it exists and is currently playing
    public Optional<GlidingWindSoundInstance> getPlayingSoundInstance(SoundEvent soundEvent) {
        for (var activeSound : activeSounds) {
            // SoundInstances use their SoundEvent's id by default
            if (activeSound.getId().equals(soundEvent.getId())) {
                return Optional.of(activeSound);
            }
        }

        return Optional.empty();
    }
}
