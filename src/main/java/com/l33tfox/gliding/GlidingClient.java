package com.l33tfox.gliding;

import com.l33tfox.gliding.networking.ModPackets;
import com.l33tfox.gliding.util.GliderClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class GlidingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CPackets();

        ClientTickEvents.END_CLIENT_TICK.register(GliderClientUtil::glidingTick);
    }
}
