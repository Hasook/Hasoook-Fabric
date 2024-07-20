package com.hasoook.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class One_Hit_Obliterator extends Item {

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true; // 附魔光效
    }

    public One_Hit_Obliterator(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.EPIC).maxDamage(100)); // 堆叠上限为1 伤害100
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.kill(); // 使用kill杀死目标
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable(""));
        tooltip.add(Text.translatable("item.hasoook.one_hit_obliterator.tooltip1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.hasoook.one_hit_obliterator.tooltip2").formatted(Formatting.DARK_GREEN));
        tooltip.add(Text.translatable("item.hasoook.one_hit_obliterator.tooltip3").formatted(Formatting.DARK_GREEN));
        // 模仿游戏原版中工具的属性的描述
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false; // 在创造模式下，不允许这个武器破坏方块（模仿剑一样的效果）
    }

}