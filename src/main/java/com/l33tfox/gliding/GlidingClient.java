package com.l33tfox.gliding;

import com.l33tfox.gliding.client.GliderModel;
import com.l33tfox.gliding.networking.ModPackets;
import com.l33tfox.gliding.util.GliderClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class GlidingClient implements ClientModInitializer {

    public static final EntityModelLayer GLIDER_LAYER = new EntityModelLayer
            (Identifier.of(Gliding.MOD_ID, "glidermodel"), "glidermodel");

    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CReceivers();

        EntityModelLayerRegistry.registerModelLayer(GLIDER_LAYER, GliderModel::getTexturedModelData);

        ClientTickEvents.END_CLIENT_TICK.register(GliderClientUtil::glidingTick);
    }
}
