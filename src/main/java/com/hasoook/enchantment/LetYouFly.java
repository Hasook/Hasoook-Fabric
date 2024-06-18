package com.hasoook.enchantment;

import com.hasoook.effect.ModStatusEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class LetYouFly extends Enchantment {
    public LetYouFly(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget) {
            if (livingTarget.hasStatusEffect(ModStatusEffects.PRETENTIOUSNESS)) {
                // 应用向上的运动向量
                livingTarget.setVelocity(0, 0.4 * level, 0); // 根据需要调整数值
            }
        }
    }

    public int getMinPower(int level) {
        return 5 + 20 * (level - 1);
    }

    public int getMaxLevel() {
        return 5;
    }

}
