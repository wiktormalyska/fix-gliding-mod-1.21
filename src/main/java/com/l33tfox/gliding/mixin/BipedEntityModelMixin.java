package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.items.GliderItem;
import com.l33tfox.gliding.util.GliderClientUtil;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Inject(at = @At("HEAD"), method = "positionLeftArm", cancellable = true)
    private void setLeftArmGliding(T entity, CallbackInfo ci) {
        if (entity instanceof ClientPlayerEntity player && GliderClientUtil.isUsingGliderMoreThanOneJump(player) &&
                (player.getOffHandStack().isEmpty() || player.getOffHandStack().getItem() instanceof GliderItem)) {
            leftArm.pitch = (float) Math.PI;
            leftArm.roll = (float) Math.PI / 16;
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "positionRightArm", cancellable = true)
    private void setRightArmGliding(T entity, CallbackInfo ci) {
        if (entity instanceof ClientPlayerEntity player && GliderClientUtil.isUsingGliderMoreThanOneJump(player) &&
                (player.getMainHandStack().isEmpty() || player.getMainHandStack().getItem() instanceof GliderItem)) {
            rightArm.pitch = (float) Math.PI;
            rightArm.roll = (float) -Math.PI / 16;
            ci.cancel();
        }
    }

}
