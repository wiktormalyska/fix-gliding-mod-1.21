package com.l33tfox.gliding;

import com.l33tfox.gliding.client.GliderModel;
import com.l33tfox.gliding.networking.ModPacketsRegistry;
import com.l33tfox.gliding.util.GliderClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import java.util.Map;

import static java.util.Map.entry;

public class GlidingClient implements ClientModInitializer {

    // Layer used by GliderModelFeatureRenderer to render glider above players' heads
    public static final EntityModelLayer GLIDER_LAYER = new EntityModelLayer
            (Identifier.of(Gliding.MOD_ID, "glidermodel"), "glidermodel");

    public static final Map<String, ModelIdentifier> GLIDER_2D_MODELS = Map.ofEntries(
            entry("gliding:wooden_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "wooden_glider"))),
            entry("gliding:stone_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "stone_glider"))),
            entry("gliding:iron_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "iron_glider"))),
            entry("gliding:golden_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "golden_glider"))),
            entry("gliding:diamond_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "diamond_glider"))),
            entry("gliding:netherite_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "netherite_glider")))
    );

    public static final Map<String, ModelIdentifier> GLIDER_3D_MODELS_FIRST_PERSON = Map.ofEntries(
            entry("gliding:wooden_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "wooden_glider_3d_first_person"))),
            entry("gliding:stone_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "stone_glider_3d_first_person"))),
            entry("gliding:iron_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "iron_glider_3d_first_person"))),
            entry("gliding:golden_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "golden_glider_3d_first_person"))),
            entry("gliding:diamond_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "diamond_glider_3d_first_person"))),
            entry("gliding:netherite_glider", ModelIdentifier.ofInventoryVariant(Identifier.of(Gliding.MOD_ID, "netherite_glider_3d_first_person")))
    );

    @Override
    public void onInitializeClient() {
        ModPacketsRegistry.registerS2CReceivers();

        EntityModelLayerRegistry.registerModelLayer(GLIDER_LAYER, GliderModel::getTexturedModelData);

        ClientTickEvents.END_CLIENT_TICK.register(GliderClientUtil::glidingTick);
    }
}
