package com.hasoook.block.custom;

import com.hasoook.block.entity.AirBlockBlockEntity;
import com.hasoook.block.entity.ModBlockEntities;
import com.hasoook.enchantment.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AirBlock extends BlockWithEntity implements BlockEntityProvider {

    public AirBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // 生成白色烟雾
        double x = pos.getX() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();
        world.addParticle(ParticleTypes.CLOUD, x, pos.getY()+0.7, z,0.0, 0.0, 0.0);
    }

    private static final VoxelShape FALLING_SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9, 1.0);
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
                // 是否有虚空行走附魔
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

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AirBlockBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.AIR_BLOCK_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
