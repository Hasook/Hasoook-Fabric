package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class Combine extends Enchantment {
    protected Combine(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public int getMinPower(int level) {
        return level * 5;
    }
    public int getMaxLevel() {
        return 1;
    }  //最大等级

}
