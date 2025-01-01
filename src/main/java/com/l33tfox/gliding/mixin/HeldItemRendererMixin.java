package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.items.GliderItem;
import com.l33tfox.gliding.util.GliderUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;" +
            "Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void hideFirstPersonGliderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode,
                                           boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                           int light, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player && ((PlayerEntityDuck) player).gliding$isActivatingGlider()) {
            if (GliderUtil.mainHandHoldingGlider(player) && GliderUtil.offHandHoldingGlider(player) &&
                stack == player.getOffHandStack())
                ci.cancel();
        }
    }
    @ModifyVariable(at = @At("STORE"), method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;" +
            "Lnet/minecraft/client/network/ClientPlayerEntity;I)V")
    private HeldItemRenderer.HandRenderType hideFirstPersonHand(HeldItemRenderer.HandRenderType handRenderType, @Local(argsOnly = true) ClientPlayerEntity player) {
        if (((PlayerEntityDuck) player).gliding$isActivatingGlider() && player.getMainHandStack().isEmpty())
            return HeldItemRenderer.HandRenderType.shouldOnlyRender(Hand.OFF_HAND);

        else if (((PlayerEntityDuck) player).gliding$isActivatingGlider() && GliderUtil.mainHandHoldingGlider(player) &&
                GliderUtil.offHandHoldingGlider(player))
            return HeldItemRenderer.HandRenderType.shouldOnlyRender(Hand.MAIN_HAND);

        return handRenderType;
    }
}