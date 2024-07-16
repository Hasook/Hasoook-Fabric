package com.hasoook.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class PretentiousnessEffect extends StatusEffect {
    public PretentiousnessEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // 这个方法在每个 tick 都会调用，以检查是否应应用药水效果
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // 这个方法在应用药水效果时会被调用，所以我们可以在这里实现自定义功能。
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            // 创建一个抗性提升效果
            StatusEffectInstance resistanceEffect = new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 0, false, false);
            // 添加效果到实体
            entity.addStatusEffect(resistanceEffect);
        }
    }

}
