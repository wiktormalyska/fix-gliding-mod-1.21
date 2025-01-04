package com.l33tfox.gliding.client.renderer;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.GlidingClient;
import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.client.GliderModel;
import com.l33tfox.gliding.util.GliderUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class GliderModelFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final GliderModel gliderModel;

    public GliderModelFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context,
                                      EntityModelLoader loader) {
        super(context);
        gliderModel = new GliderModel(loader.getModelPart(GlidingClient.GLIDER_LAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta,
                       float animationProgress, float headYaw, float headPitch) {
        if (((PlayerEntityDuck) player).gliding$isActivatingGlider() && GliderUtil.isHoldingGlider(player)) {
            String gliderID = Registries.ITEM.getId(GliderUtil.getGliderItemInHand(player)).getPath();
            Identifier gliderCutout = Identifier.of(Gliding.MOD_ID, "textures/item/" + gliderID + "_model.png");

            matrices.push();

            gliderModel.setAngles(player, limbAngle, limbDistance, tickDelta, headYaw, headPitch);
            matrices.translate(0.0, -1.75, 0.0);

            //VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(GLIDER_CUTOUT));
            VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(gliderCutout), true, false);
            gliderModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

            matrices.pop();
        }

    }
}
