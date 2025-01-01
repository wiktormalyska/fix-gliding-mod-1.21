package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.client.renderer.GliderModelFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

//    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math" +
//            "/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
//    private void addFirstPersonGlider(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g,
//                                      MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
//                                      int i, CallbackInfo ci) {
//        if (((PlayerEntityDuck) abstractClientPlayerEntity).gliding$isActivatingGlider())
//
//    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addGliderFeature(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        addFeature(new GliderModelFeatureRenderer(this, ctx.getModelLoader()));
    }

}
