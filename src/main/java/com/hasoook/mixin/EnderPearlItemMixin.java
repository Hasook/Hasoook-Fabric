package com.hasoook.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin extends Item {
    public EnderPearlItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use" , at = @At("HEAD"))
    public void enderPearlUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        // 获取玩家手中的物品栈
        ItemStack itemStack = user.getStackInHand(hand);
        // 获取多重射击附魔等级
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, itemStack);
        // 检查玩家是否在服务器端且物品具有多重射击附魔
        if (!user.getWorld().isClient && multishotLevel >= 1){
            // 循环生成末影珍珠，次数为1 + 多重射击等级
            for (int i = 0; i < 1 + multishotLevel; i++) {
                EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, user); // 创建末影珍珠实体
                enderPearlEntity.setItem(itemStack); // 设置末影珍珠的物品栈
                enderPearlEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 8.0f); // 设置末影珍珠的速度
                world.spawnEntity(enderPearlEntity); // 生成末影珍珠实体
            }
        }

    }

}
