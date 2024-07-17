package com.hasoook.mixin;

import com.hasoook.item.custom.One_Hit_Obliterator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class OneHitObliteratorMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        // 检查受伤的实体是否为 LivingEntity
        LivingEntity injuredEntity = (LivingEntity)(Object)this;

        // 检查受伤的实体是否手持钻石剑
        ItemStack heldItem = injuredEntity.getMainHandStack();
        if (heldItem.getItem() == Items.DIAMOND_SWORD) {
            // 如果受伤的实体手持钻石剑，则杀死该实体
            injuredEntity.kill();
            info.setReturnValue(true); // 取消原始方法的执行
        }
    }
}