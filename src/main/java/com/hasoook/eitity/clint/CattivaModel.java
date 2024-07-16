package com.hasoook.eitity.clint;

import com.hasoook.Hasoook;
import com.hasoook.eitity.custom.CattivaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CattivaModel extends GeoModel<CattivaEntity> {
    @Override
    public Identifier getModelResource(CattivaEntity animatable) {
        return new Identifier(Hasoook.MOD_ID, "geo/cattiva.geo.json");
    }

    @Override
    public Identifier getTextureResource(CattivaEntity animatable) {
        return new Identifier(Hasoook.MOD_ID, "textures/entity/cattiva.png");
    }

    @Override
    public Identifier getAnimationResource(CattivaEntity animatable) {
        return new Identifier(Hasoook.MOD_ID, "animations/cattiva.animation.json");
    }

    @Override
    public void setCustomAnimations(CattivaEntity animatable, long instanceId, AnimationState<CattivaEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("bone");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
