package com.hasoook.enchantment;

import com.hasoook.Hasoook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static Enchantment Blast = register("blast", //爆炸
            new Blast(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static Enchantment Blunt = register("blunt", //磨钝
            new Blunt(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static Enchantment Thunder = register("thunder", //落雷
            new Thunder(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static Enchantment Combine = register("combine", //兼容
            new Combine(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static Enchantment VoidWalker = register("void_walker", //虚空行者
            new VoidWalker(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET}));


    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Hasoook.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments(){
        System.out.println("注册附魔");
    }
}
