package com.l33tfox.gliding.items;

import net.minecraft.item.*;

public class GliderItem extends Item {

    public double glideDropVelocity;
    public double glideSpeedIncreaseFactor;

    public GliderItem(double dropVelocity, double speedFactor, Settings settings) {
        super(settings);

        glideDropVelocity = dropVelocity;
        glideSpeedIncreaseFactor = speedFactor;
    }

}
