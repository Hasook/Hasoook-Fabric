package com.hasoook.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FishingBobberEntity.class)
public abstract class LureItemBobberMixin extends Entity {
    public LureItemBobberMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private boolean removeIfInvalid(PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();  // 获取玩家主手物品栏中的物品
        ItemStack itemStack2 = player.getOffHandStack();  // 获取玩家副手物品栏中的物品
        boolean bl = itemStack.isOf(Items.FISHING_ROD) || EnchantmentHelper.getLevel(Enchantments.LURE, itemStack) > 0;    // 检查主手物品是否为钓鱼竿或有饵钓附魔
        boolean bl2 = itemStack2.isOf(Items.FISHING_ROD) || EnchantmentHelper.getLevel(Enchantments.LURE, itemStack2) > 0;  // 检查主手物品是否为钓鱼竿或有饵钓附魔

        if (!player.isRemoved() && player.isAlive() && (bl || bl2) && !(this.squaredDistanceTo(player) > 1024.0)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

}
