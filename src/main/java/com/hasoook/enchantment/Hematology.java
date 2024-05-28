package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public class Hematology extends Enchantment {
    protected Hematology(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        Random random = user.getRandom();
        // 创建消息数组
        String[] messages = {
                "<name> 你人真好",
                "<name> 谢谢你啊",
                "<name> 感动哭了"
        };
        // 从消息数组中随机选择一条消息
        String message = messages[random.nextInt(messages.length)];

        // 从实体身上选择带有血包附魔的装备，并返回装备槽和对应的物品
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(ModEnchantments.Hematology, user);
        if (ThornsEnchantment.shouldDamageAttacker(level, random)) {
            if (attacker instanceof LivingEntity) {
                // 恢复攻击者的血量，受附魔等级影响
                float healingAmount = ThornsEnchantment.getDamageAmount(level, random) * (1f + random.nextFloat());
                ((LivingEntity) attacker).heal(healingAmount);
                // 生成爱心粒子效果在攻击者位置
                if (((LivingEntity) attacker).getWorld() instanceof ServerWorld) {
                    ((ServerWorld) ((LivingEntity) attacker).getWorld()).spawnParticles(ParticleTypes.HEART, attacker.getX(), attacker.getY() + attacker.getHeight(), attacker.getZ(), 2, 0.4, 0.2, 0.4, 0.1);

                }
                // 如果被攻击者是玩家，发送消息
                if (user instanceof PlayerEntity) {
                    // 替换消息中的攻击者名称
                    String attackerName = attacker instanceof LivingEntity ? ((LivingEntity) attacker).getDisplayName().getString() : attacker.getType().getName().getString();
                    String formattedMessage = message.replace("name", attackerName);
                    ((PlayerEntity) user).sendMessage(Text.of(formattedMessage), false);
                }
            }
            if (entry != null) {
                // 对该装备造成伤害，伤害值为2，同时发送装备损坏的状态给用户
                entry.getValue().damage(1, user, entity -> entity.sendEquipmentBreakStatus((EquipmentSlot) entry.getKey()));
            }
        }
    }

    public boolean isCursed() {
        return true;
    }
    public int getMinPower(int level) {
        return level * 5;
    }
    public int getMaxLevel() {
        return 3;
    }

}
