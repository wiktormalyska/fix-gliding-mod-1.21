package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.Gliding;
import com.l33tfox.gliding.GlidingClient;
import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.items.GliderItem;
import com.l33tfox.gliding.util.GliderUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.item.ItemRenderer.getItemGlintConsumer;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @Shadow
    public abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @Shadow @Final private MinecraftClient client;

    // ugliest code ever but whatever
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/" +
            "ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/" +
            "VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel" +
                    "(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/" +
                    "client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"), cancellable = true)
    private void noGlintIfGliderWhileActivating(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded,
                                                MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                                int overlay, BakedModel model, CallbackInfo ci,
                                                @Local VertexConsumer vertexConsumer, @Local RenderLayer renderLayer) {
        if (stack.getItem() instanceof GliderItem && ((PlayerEntityDuck) client.player).gliding$isActivatingGlider()
                && renderMode.isFirstPerson()) {
            vertexConsumer = getItemGlintConsumer(vertexConsumers, renderLayer, true, false);
            renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            matrices.pop();
            ci.cancel();
        } else if (stack.getItem() instanceof GliderItem && ((PlayerEntityDuck) client.player).gliding$isActivatingGlider()
                && renderMode == ModelTransformationMode.GUI) {
            DiffuseLighting.disableGuiDepthLighting();
        }
    }

    // ugliest code ever but whatever v2
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/" +
                    "ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/" +
                    "VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), argsOnly = true)
    private BakedModel render2DGliderInInventoryWhileActivating(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack,
                                                                @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.getItem() instanceof GliderItem && renderMode == ModelTransformationMode.GUI &&
           ((PlayerEntityDuck) client.player).gliding$isActivatingGlider() &&
           (stack == client.player.getMainHandStack() || stack == client.player.getOffHandStack())) {
            ModelIdentifier glider2DModelId = GlidingClient.GLIDER_2D_MODELS.get(stack.getItem().toString());
            return getModels().getModelManager().getModel(glider2DModelId);
        }

        return bakedModel;
    }

    // ugliest code ever but whatever v3
    @ModifyVariable(method = "getModel", at = @At(value = "STORE"), ordinal = 1)
    private BakedModel add3DGliderFirstPersonWhenActivating(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof GliderItem && ((PlayerEntityDuck) client.player).gliding$isActivatingGlider()
            && (stack == client.player.getMainHandStack() ||
                (stack == client.player.getOffHandStack() && !GliderUtil.mainHandHoldingGlider(client.player)))) {
            ModelIdentifier glider3DModelId = GlidingClient.GLIDER_3D_MODELS_FIRST_PERSON.get(stack.getItem().toString());
            return this.models.getModelManager().getModel(glider3DModelId);
        }

        return bakedModel;
    }
}
