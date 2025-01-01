package com.l33tfox.gliding;

import com.l33tfox.gliding.client.GliderModel;
import com.l33tfox.gliding.networking.ModPackets;
import com.l33tfox.gliding.util.GliderClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class GlidingClient implements ClientModInitializer {

    public static final EntityModelLayer GLIDER_LAYER = new EntityModelLayer
            (Identifier.of(Gliding.MOD_ID, "glidermodel"), "glidermodel");
    public static final ModelIdentifier GLIDER_3D_FIRST_PERSON = ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "wooden_glider_3d_first_person"));
    public static final ModelIdentifier GLIDER_2D = ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "wooden_glider"));

    @Override
    public void onInitializeClient() {
        ModPackets.registerS2CReceivers();

        EntityModelLayerRegistry.registerModelLayer(GLIDER_LAYER, GliderModel::getTexturedModelData);

        ClientTickEvents.END_CLIENT_TICK.register(GliderClientUtil::glidingTick);
    }
}
