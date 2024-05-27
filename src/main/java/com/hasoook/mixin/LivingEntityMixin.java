package com.hasoook.mixin;

import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At(value = "HEAD"))
    private void applySlowdown(Vec3d movementInput, CallbackInfo ci) {
        // 检查实体是否有附魔"SoulEntanglement"
        if (EnchantmentHelper.getLevel(ModEnchantments.SoulEntanglement, ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.FEET)) > 0) {
            // 如果实体脚上穿着"SoulEntanglement"附魔，施加减速效果
            double slowdownFactor = 0.5; // 这是一个示例值，你可以根据需要调整
            this.setVelocity(this.getVelocity().multiply(slowdownFactor, 1.0, slowdownFactor));
        }
    }

}
