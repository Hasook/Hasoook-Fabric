package com.hasoook.enchantment;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public class FrostThorns extends Enchantment {
    protected FrostThorns(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        Random random = user.getRandom();
        // 从实体身上选择带有冰霜荆棘附魔的装备，并返回装备槽和对应的物品
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(ModEnchantments.FrostThorns, user);
        if (ThornsEnchantment.shouldDamageAttacker(level, random)) {
            if (attacker != null) {
                attacker.damage(user.getDamageSources().thorns(user), ThornsEnchantment.getDamageAmount(level, random));
                ((LivingEntity) attacker).setFrozenTicks(200); // 设置冰冻值为 200
            }
            if (entry != null) {
                // 对该装备造成伤害，伤害值为2，同时发送装备损坏的状态给用户
                entry.getValue().damage(2, user, entity -> entity.sendEquipmentBreakStatus((EquipmentSlot)entry.getKey()));
            }
        }
    }

    public int getMinPower(int level) {
        return level * 5;
    }
    public int getMaxLevel() {
        return 3;
    }  //最大等级

}
