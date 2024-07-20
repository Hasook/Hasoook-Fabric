package com.hasoook.mixin;

import com.hasoook.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class OneHitObliteratorMixin extends Entity {

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow public abstract void onDeath(DamageSource damageSource);

    public OneHitObliteratorMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        if (!this.getWorld().isClient && this.getMainHandStack().getItem() == ModItems.ONE_HIT_OBLITERATOR && this.isAlive() && amount > 0.0f) {
            this.setHealth(0); // 把生命值设置为0
            this.onDeath(source); // 调用死亡逻辑（不然死亡后没有掉落物）
        }
    }

}
