package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Transcription extends Enchantment {
    protected Transcription(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public boolean isTreasure() {
        return true;
        // 是否为宝藏附魔
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(Items.WRITABLE_BOOK);
        // 只有书与笔可以接受抄写附魔
    }

}
