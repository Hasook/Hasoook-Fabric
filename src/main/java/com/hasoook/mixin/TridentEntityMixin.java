package com.hasoook.mixin;

import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends Entity {
    public TridentEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        TridentEntity trident = (TridentEntity) (Object) this;
        LivingEntity owner = (LivingEntity) trident.getOwner();

        // 三叉戟是否有飞雷神附魔
        if (EnchantmentHelper.getLevel(ModEnchantments.Hiraishin, trident.getItemStack()) > 0) {
            int sharpnessLevel = EnchantmentHelper.getLevel(ModEnchantments.Hiraishin, trident.getItemStack());
            int distance = sharpnessLevel * 16;
            // 检查玩家是否在潜行
            if (owner != null && owner.isSneaking()) {
                // 所有者与三叉戟之间的距离
                double distanceSq = owner.squaredDistanceTo(trident);


                if (distanceSq <= distance * distance) {
                    // 判断距离是否在 16 * 附魔等级 内

                    owner.teleport(trident.getX(), trident.getY(), trident.getZ());
                    // 将玩家传送到三叉戟的位置
                    getWorld().playSound(null, trident.getX(), trident.getY(), trident.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    trident.remove(Entity.RemovalReason.DISCARDED);
                    // 移除三叉戟实体

                    ItemStack tridentStack = trident.getItemStack().copy();
                    // 复制三叉戟的物品给玩家
                    if (owner instanceof ServerPlayerEntity && !((ServerPlayerEntity) owner).isCreative()) {
                        PlayerEntity player = (PlayerEntity) owner;
                        if (!player.getInventory().insertStack(tridentStack)) {
                            // 如果无法插入物品，则丢弃在世界上
                            player.dropItem(tridentStack, false);
                        }
                    }
                }
            }
        }
    }

}
