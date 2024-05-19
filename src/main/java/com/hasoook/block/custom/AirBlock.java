package com.hasoook.block.custom;

import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class AirBlock extends Block {
    public AirBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape FALLING_SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9f, 1.0);

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        EntityShapeContext entityShapeContext;
        Entity entity;
        if (context instanceof EntityShapeContext && (entity = (entityShapeContext = (EntityShapeContext)context).getEntity()) != null) {
            if (entity.fallDistance > 2.5f) {
                return FALLING_SHAPE;
            }
            boolean bl = entity instanceof FallingBlockEntity;
            boolean VoidWalking = false;

            // 如果是生物实体
            if (entity instanceof LivingEntity) {
                // 获取脚部装备栏
                ItemStack feetStack = ((LivingEntity)entity).getEquippedStack(EquipmentSlot.FEET);
                // 是否具有虚空行走附魔
                VoidWalking = feetStack != null && EnchantmentHelper.getLevel(ModEnchantments.VoidWalker, feetStack) > 0;
            }

            // 判断实体是否为下落方块或者拥有虚空行者附魔且在方块上方且不是下落状态
            if (bl || VoidWalking && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()) {
                return super.getCollisionShape(state, world, pos, context);
            }
        }
        // 若不满足上述条件则返回空形状
        return VoxelShapes.empty();
    }

}
