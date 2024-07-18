package com.hasoook.eitity.clint;

import com.hasoook.Hasoook;
import com.hasoook.eitity.custom.CattivaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CattivaRenderer extends GeoEntityRenderer<CattivaEntity> {
    public CattivaRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CattivaModel());
    }

    @Override
    public Identifier getTextureLocation(CattivaEntity animatable) {
        return new Identifier(Hasoook.MOD_ID, "textures/entity/cattiva.png");
    }

    @Override
    public void render(CattivaEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
