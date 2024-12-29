package com.l33tfox.gliding.mixin;

import com.l33tfox.gliding.PlayerEntityDuck;
import com.l33tfox.gliding.items.GliderItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

    @Inject(at = @At("TAIL"), method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V")
    private void setArmsGliding(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity player) {
            if (((PlayerEntityDuck) player).isActivatingGlider() &&
                (player.getOffHandStack().isEmpty() || player.getOffHandStack().getItem() instanceof GliderItem)) {
                leftArm.pitch = (float) Math.PI;
                leftArm.roll = (float) Math.PI / 16;
            }
            if (((PlayerEntityDuck) player).isActivatingGlider() &&
                    (player.getMainHandStack().isEmpty() || player.getMainHandStack().getItem() instanceof GliderItem)) {
                rightArm.pitch = (float) Math.PI;
                rightArm.roll = (float) -Math.PI / 16;
            }
        }
    }

}
