package com.hasoook.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class LureItemBobberRendererMixin extends EntityRenderer {
    protected LureItemBobberRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Unique
    private static final Identifier TEXTURE = new Identifier("textures/entity/fishing_hook.png");
    @Unique
    private static final RenderLayer LAYER;

    public void render(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        PlayerEntity playerEntity = fishingBobberEntity.getPlayerOwner();
        if (playerEntity != null) {
            matrixStack.push();
            matrixStack.push();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            matrixStack.multiply(this.dispatcher.getRotation());
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            MatrixStack.Entry entry = matrixStack.peek();
            Matrix4f matrix4f = entry.getPositionMatrix();
            Matrix3f matrix3f = entry.getNormalMatrix();
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
            matrixStack.pop();
            int j = playerEntity.getMainArm() == Arm.RIGHT ? 1 : -1;
            ItemStack itemStack = playerEntity.getMainHandStack();
            ItemStack offhandStack = playerEntity.getStackInHand(Hand.OFF_HAND);
            if (!itemStack.isOf(Items.FISHING_ROD) && offhandStack.isOf(Items.FISHING_ROD)) {
                j = -j;
            } else if (EnchantmentHelper.getLevel(Enchantments.LURE, offhandStack) > 0 && !itemStack.isOf(Items.FISHING_ROD)) {
                j = -j;
            }

            float h = playerEntity.getHandSwingProgress(g);
            float k = MathHelper.sin(MathHelper.sqrt(h) * 3.1415927F);
            float l = MathHelper.lerp(g, playerEntity.prevBodyYaw, playerEntity.bodyYaw) * 0.017453292F;
            double d = (double)MathHelper.sin(l);
            double e = (double)MathHelper.cos(l);
            double m = (double)j * 0.35;
            double n = 0.8;
            double o;
            double p;
            double q;
            float r;
            double s;
            if ((this.dispatcher.gameOptions == null || this.dispatcher.gameOptions.getPerspective().isFirstPerson()) && playerEntity == MinecraftClient.getInstance().player) {
                s = 960.0 / (double)(Integer)this.dispatcher.gameOptions.getFov().getValue();
                Vec3d vec3d = this.dispatcher.camera.getProjection().getPosition((float)j * 0.525F, -0.1F);
                vec3d = vec3d.multiply(s);
                vec3d = vec3d.rotateY(k * 0.5F);
                vec3d = vec3d.rotateX(-k * 0.7F);
                o = MathHelper.lerp((double)g, playerEntity.prevX, playerEntity.getX()) + vec3d.x;
                p = MathHelper.lerp((double)g, playerEntity.prevY, playerEntity.getY()) + vec3d.y;
                q = MathHelper.lerp((double)g, playerEntity.prevZ, playerEntity.getZ()) + vec3d.z;
                r = playerEntity.getStandingEyeHeight();
            } else {
                o = MathHelper.lerp((double)g, playerEntity.prevX, playerEntity.getX()) - e * m - d * 0.8;
                p = playerEntity.prevY + (double)playerEntity.getStandingEyeHeight() + (playerEntity.getY() - playerEntity.prevY) * (double)g - 0.45;
                q = MathHelper.lerp((double)g, playerEntity.prevZ, playerEntity.getZ()) - d * m + e * 0.8;
                r = playerEntity.isInSneakingPose() ? -0.1875F : 0.0F;
            }

            s = MathHelper.lerp((double)g, fishingBobberEntity.prevX, fishingBobberEntity.getX());
            double t = MathHelper.lerp((double)g, fishingBobberEntity.prevY, fishingBobberEntity.getY()) + 0.25;
            double u = MathHelper.lerp((double)g, fishingBobberEntity.prevZ, fishingBobberEntity.getZ());
            float v = (float)(o - s);
            float w = (float)(p - t) + r;
            float x = (float)(q - u);
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getLineStrip());
            MatrixStack.Entry entry2 = matrixStack.peek();

            for(int z = 0; z <= 16; ++z) {
                renderFishingLine(v, w, x, vertexConsumer2, entry2, percentage(z, 16), percentage(z + 1, 16));
            }

            matrixStack.pop();
            super.render(fishingBobberEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }
    }

    @Unique
    private static float percentage(int value, int max) {
        return (float)value / (float)max;
    }

    @Unique
    private static void vertex(VertexConsumer buffer, Matrix4f matrix, Matrix3f normalMatrix, int light, float x, int y, int u, int v) {
        buffer.vertex(matrix, x - 0.5F, (float)y - 0.5F, 0.0F).color(255, 255, 255, 255).texture((float)u, (float)v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    @Unique
    private static void renderFishingLine(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
        float f = x * segmentStart;
        float g = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float h = z * segmentStart;
        float i = x * segmentEnd - f;
        float j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - g;
        float k = z * segmentEnd - h;
        float l = MathHelper.sqrt(i * i + j * j + k * k);
        i /= l;
        j /= l;
        k /= l;
        buffer.vertex(matrices.getPositionMatrix(), f, g, h).color(0, 0, 0, 255).normal(matrices.getNormalMatrix(), i, j, k).next();
    }

    static {
        LAYER = RenderLayer.getEntityCutout(TEXTURE);
    }



}
