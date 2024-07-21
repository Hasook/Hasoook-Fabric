package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Hiraishin extends Enchantment {
    protected Hiraishin(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public int getMinPower(int level) {
        return 10 + level * 7;
    }

    public int getMaxPower(int level) {
        return 50;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(Items.TRIDENT);
        // 只有三叉戟可以附魔
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.LOYALTY && other != Enchantments.CHANNELING && other != Enchantments.RIPTIDE;
        // 与忠诚、引雷、激流不兼容
    }
}
