package com.hasoook.mixin;

import com.hasoook.block.ModBlocks;
import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class VoidWalkerMixin extends Entity {
    @Shadow public abstract boolean isFallFlying();

    public VoidWalkerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"), cancellable = true)
    public void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        // 获取触发此方法的实体
        Entity entity = (Entity)(Object)this;
        // 检查实体是否正在潜行
        boolean isSneaking = entity.isSneaking();
        // 检查实体鞋子上是否有附魔“虚空行者”
        boolean hasFeatherFallingEnchantment = false;
        // 检查实体是否为生物实体，并且没有潜行和滑翔
        if (entity instanceof LivingEntity && !isSneaking && !isFallFlying()) {
            ItemStack feetSlot = ((LivingEntity)entity).getEquippedStack(EquipmentSlot.FEET);
            hasFeatherFallingEnchantment = EnchantmentHelper.getLevel(ModEnchantments.VoidWalker, feetSlot) > 0;
        }

        // 如果鞋子上有附魔“虚空行者”，则在其脚下生成空气方块
        if (hasFeatherFallingEnchantment) {
            World world = entity.getEntityWorld();
            BlockPos belowPos = pos.down();

            // 检查实体脚下10格内的方块是否全部是空气或空气方块
            boolean allAirOrAirBlock = true;
            for (int i = 0; i < 8 && allAirOrAirBlock; i++) {
                BlockPos checkPos = belowPos.add(0, -i, 0);
                if (!world.isAir(checkPos) && world.getBlockState(checkPos).getBlock() != ModBlocks.AIRBLOCK) {
                    allAirOrAirBlock = false;
                    break;
                }
            }

            // 替换脚下3x3范围内的所有空气为空气方块
            if (allAirOrAirBlock) {
                for (int xOffset = -1; xOffset <= 1; xOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        BlockPos checkPos = belowPos.add(xOffset, 0, zOffset);
                        // 检查方块是否是空气或者空气方块
                        if (world.isAir(checkPos) || world.getBlockState(checkPos).getBlock() == ModBlocks.AIRBLOCK) {
                            world.setBlockState(checkPos, ModBlocks.AIRBLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
    }

}

