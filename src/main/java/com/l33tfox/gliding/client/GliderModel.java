package com.l33tfox.gliding.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class GliderModel extends EntityModel<Entity> {

    private final ModelPart cloth;
    private final ModelPart frame;

    public GliderModel(ModelPart root) {
        cloth = root.getChild("cloth");
        frame = root.getChild("frame");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData cloth = modelPartData.addChild("cloth", ModelPartBuilder.create().uv(0, 21).cuboid(-3.0F, -9.4F, -10.0F, 6.0F, 1.0F, 20.0F, new Dilation(0.0F))
                .uv(30, 93).cuboid(-13.0F, -2.0F, -10.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 93).cuboid(-13.0F, -2.0F, 8.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 93).cuboid(12.0F, -2.0F, 8.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(94, 0).cuboid(12.0F, -2.0F, -10.0F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        ModelPartData cube_r1 = cloth.addChild("cube_r1", ModelPartBuilder.create().uv(52, 27).cuboid(-1.0F, -2.0F, 0.0F, 1.0F, 7.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, -5.0F, -10.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData cube_r2 = cloth.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-4.7F, 0.0F, 0.0F, 6.0F, 1.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -8.9F, -10.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r3 = cloth.addChild("cube_r3", ModelPartBuilder.create().uv(0, 42).cuboid(-1.3F, 0.0F, 0.0F, 6.0F, 1.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -8.9F, -10.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData cube_r4 = cloth.addChild("cube_r4", ModelPartBuilder.create().uv(52, 0).cuboid(0.0F, -2.0F, 0.0F, 1.0F, 7.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -5.0F, -10.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData frame = modelPartData.addChild("frame", ModelPartBuilder.create().uv(24, 85).cuboid(6.0F, 5.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(52, 54).cuboid(-13.0F, 1.0F, -10.0F, 2.0F, 2.0F, 20.0F, new Dilation(0.0F))
                .uv(84, 84).cuboid(-11.0F, -3.0F, -9.0F, 2.0F, 2.0F, 18.0F, new Dilation(0.0F))
                .uv(44, 84).cuboid(9.0F, -3.0F, -9.0F, 2.0F, 2.0F, 18.0F, new Dilation(0.0F))
                .uv(24, 89).cuboid(-12.0F, 5.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(44, 76).cuboid(-14.0F, -1.0F, 6.0F, 28.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(44, 80).cuboid(-14.0F, -1.0F, -8.0F, 28.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 63).cuboid(11.0F, 1.0F, -10.0F, 2.0F, 2.0F, 20.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        ModelPartData cube_r5 = frame.addChild("cube_r5", ModelPartBuilder.create().uv(16, 85).cuboid(-4.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F))
                .uv(44, 63).cuboid(16.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 5.6F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r6 = frame.addChild("cube_r6", ModelPartBuilder.create().uv(8, 85).cuboid(-4.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 85).cuboid(16.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 5.6F, 0.0F, 0.7854F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        cloth.render(matrices, vertexConsumer, light, overlay, color);
        frame.render(matrices, vertexConsumer, light, overlay, color);
    }
}
