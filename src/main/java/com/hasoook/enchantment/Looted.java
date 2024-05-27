package com.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class Looted extends Enchantment {
    protected Looted(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getWorld().isClient && target instanceof LivingEntity && Math.random() < 0.05  + level * 0.05) {
            LivingEntity targetEntity = (LivingEntity) target;
            ItemStack mainHandItem = user.getMainHandStack();
            if (!mainHandItem.isEmpty()) {
                // 复制物品栈并扔出
                ItemStack clonedItemStack = mainHandItem.copy();
                clonedItemStack.setCount(1); // 仅扔出一份物品
                Vec3d userPos = user.getPos();
                Vec3d targetPos = targetEntity.getPos();
                if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
                    clonedItemStack.damage(1, user.getRandom(), null);
                }
                ItemEntity itemEntity = new ItemEntity(targetEntity.getWorld(),
                        userPos.x, userPos.y + 1, userPos.z, clonedItemStack);

                // 设置物品实体的运动方向和倍率
                itemEntity.setVelocity(targetPos.subtract(userPos).normalize().multiply(0.4));

                itemEntity.setPickupDelay(20); // 物品不能被捡起的时间
                targetEntity.getWorld().spawnEntity(itemEntity);
                // 从玩家主手中移除物品
                mainHandItem.decrement(1);
            }
        }
    }

    public int getMinPower(int level) {
        return level * 5;
    }
    public boolean isCursed() {
        return true;
    }  //是否为诅咒
    public int getMaxLevel() {
        return 3;
    }  //最大等级

}
