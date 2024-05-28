package com.hasoook.mixin;

import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SoulEntanglementMixin extends Entity {
    private Vec3d previousPos; // 存储实体位置

    public SoulEntanglementMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At(value = "HEAD"))
    private void applySlowdown(Vec3d movementInput, CallbackInfo ci) {
        // 获取鞋子上的灵魂纠缠的附魔等级
        int Level = EnchantmentHelper.getLevel(ModEnchantments.SoulEntanglement, ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.FEET));
        // 获取实体当前位置
        Vec3d entityPos = this.getPos();
        double posX = entityPos.x;
        double posY = entityPos.y;
        double posZ = entityPos.z;
        // 获取实体下方0.2格位置
        double posYUnder = posY - 0.2;
        int posXInt = (int) Math.floor(posX);
        int posYInt = (int) Math.floor(posYUnder);
        int posZInt = (int) Math.floor(posZ);
        BlockPos blockPosUnderFeet = new BlockPos(posXInt, posYInt, posZInt);

        if (EnchantmentHelper.getLevel(ModEnchantments.SoulEntanglement, ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.FEET)) > 0) {
            // 获取脚下方块
            BlockState blockStateUnderFeet = this.getWorld().getBlockState(blockPosUnderFeet);
            Block blockUnderFeet = blockStateUnderFeet.getBlock();

            // 检查脚下方块是否是灵魂沙或灵魂土
            if (blockUnderFeet == Blocks.SOUL_SAND || blockUnderFeet == Blocks.SOUL_SOIL) {
                // 设置减速因子
                double slowdownFactor = -0.02 * Level - 0.2;
                this.setVelocity(this.getVelocity().multiply(slowdownFactor, 1 - Level * 0.1, slowdownFactor));

                // 检查实体是否移动
                if (previousPos != null && !this.getPos().equals(previousPos) && Math.random() < 0.1) {
                    // 生成灵魂粒子
                    Vec3d vec3d = this.getVelocity();
                    this.getWorld().addParticle(ParticleTypes.SOUL, this.getX() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(), this.getY() + 0.1, this.getZ() + (this.random.nextDouble() - 0.5) * (double)this.getWidth(), vec3d.x * -0.2, 0.1, vec3d.z * -0.2);
                    // 召唤尖牙
                    if (Math.random() < 0.1) {
                        EvokerFangsEntity evoker = EntityType.EVOKER_FANGS.create(this.getWorld());
                        evoker.setPosition(this.getX(), this.getY(), this.getZ());
                        this.getWorld().spawnEntity(evoker);
                    }
                }

                // 更新位置
                previousPos = this.getPos();
            }
        }
    }

}
