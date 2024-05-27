package com.hasoook.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FishingRodItem.class)
public abstract class FishingRodItemMixin extends Item {
    public FishingRodItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.fishHook != null) {
            // 获取鱼钩的位置
            Vec3d bobberPos = user.fishHook.getPos();
            // 鱼钩是否在地上
            if (world.getBlockState(user.fishHook.getBlockPos().down()).isSolidBlock(world, user.fishHook.getBlockPos().down())) {
                // 检查鱼竿上是否有力量附魔
                if (EnchantmentHelper.getLevel(Enchantments.POWER, itemStack) > 0) {
                    // 只有当鱼钩未勾住实体时，才将运动向量应用到玩家身上
                    if (user.fishHook.getHookedEntity() == null) {
                        // 计算指向鱼钩位置的运动向量并乘以倍数
                        Vec3d motionVector = bobberPos.subtract(user.getX(), user.getY(), user.getZ()).normalize().multiply(0.3 + 0.3 * EnchantmentHelper.getLevel(Enchantments.POWER, itemStack));
                        user.addVelocity(motionVector.x, motionVector.y, motionVector.z);
                    }
                    // 给勾住的实体应用运动向量
                    if (user.fishHook.getHookedEntity() != null) {
                        Vec3d hookedMotionVector = bobberPos.subtract(user.fishHook.getHookedEntity().getX(), user.fishHook.getHookedEntity().getY(), user.fishHook.getHookedEntity().getZ()).normalize().multiply(EnchantmentHelper.getLevel(Enchantments.POWER, itemStack));
                        user.fishHook.getHookedEntity().addVelocity(hookedMotionVector.x * 100, hookedMotionVector.y * 0.1, hookedMotionVector.z * 100);
                    }
                }
            }

            if (!world.isClient) {
                int i = user.fishHook.use(itemStack);
                itemStack.damage(i, user, p -> p.sendToolBreakStatus(hand));
            }
            // 播放收回鱼钩的音效
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            // 如果鱼钩不处于激活状态，则正常处理抛出鱼钩的情况
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            if (!world.isClient) {
                int i = EnchantmentHelper.getLure(itemStack);
                int j = EnchantmentHelper.getLuckOfTheSea(itemStack);
                world.spawnEntity(new FishingBobberEntity(user, world, j, i));
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }


}
