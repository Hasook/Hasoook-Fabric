package com.hasoook.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class VoidAttractionMixin extends Entity {
    public VoidAttractionMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"), cancellable = true)
    public void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        System.out.println("111");
    }
}
