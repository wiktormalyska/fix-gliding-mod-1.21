package com.l33tfox.gliding.items;

import net.minecraft.item.*;

public class GliderItem extends ToolItem {

    public static final double GLIDE_DROP_SPEED = -0.15;
    public static final double GLIDE_SPEED_INCREASE_FACTOR = 1.06;

    public GliderItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

}
