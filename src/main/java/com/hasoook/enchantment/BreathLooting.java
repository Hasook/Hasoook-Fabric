package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BreathLooting extends Enchantment {
    protected BreathLooting(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        // 判断使用者氧气值是否小于最大值
        if (user.getAir() < user.getMaxAir()) {
            // 增加氧气值，但不超过最大氧气值
            int newBreath = Math.min(user.getAir() + (5 * level), user.getMaxAir());
            user.setAir(newBreath);
        }

        // 如果被攻击者是生物实体并且具有氧气值
        if (target instanceof LivingEntity) {
            LivingEntity attackedEntity = (LivingEntity) target;

            // 判断被攻击者氧气值是否大于0
            if (attackedEntity.getAir() > 0) {
                // 减少氧气值，但不低于0
                attackedEntity.setAir(attackedEntity.getAir() - (5 * level));
            }
        }
    }

    public int getMinPower(int level) {
        return level * 5;
    }
    public int getMaxLevel() {
        return 2;
    }  //最大等级

}
