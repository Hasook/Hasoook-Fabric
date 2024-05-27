package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class VoidAttraction extends Enchantment {
    protected VoidAttraction(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public int getMinPower(int level) {
        return level * 4;
    }
    public boolean isCursed() {
        return true;
    }  //是否为诅咒
    public int getMaxLevel() {
        return 2;
    }  //最大等级

}
