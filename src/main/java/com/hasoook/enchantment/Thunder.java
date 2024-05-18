package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class Thunder extends Enchantment {
    public Thunder(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxLevel() {
        return super.getMaxLevel();
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        // 当一个实体攻击目标时触发的方法

        // 如果 攻击者为玩家 且 目标是生物实体
        if (user instanceof PlayerEntity player && target instanceof LivingEntity entity) {
            // 如果不是在客户端进行操作
            if (!player.getWorld().isClient()) {
                // 获取玩家所在的服务器世界
                ServerWorld world = (ServerWorld) player.getWorld();
                // 在目标位置生成闪电
                EntityType.LIGHTNING_BOLT.spawn(world,null, null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), null, false, false);
            }
        }
        // 调用父类的onTargetDamaged方法，实现额外的处理
        super.onTargetDamaged(user, target, level);
    }
}
