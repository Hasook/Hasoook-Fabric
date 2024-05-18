package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class CursedLv2 extends Enchantment {


    protected CursedLv2(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public int getMinPower(int level) {
        return 1;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean isCursed() {
        return true;
    }

}