package name.hasoook.enchantment;

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
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return super.getMaxLevel();
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (user instanceof PlayerEntity player && target instanceof LivingEntity entity) {
            if (!player.getWorld().isClient()) {
                ServerWorld world = (ServerWorld) player.getWorld();
                EntityType.LIGHTNING_BOLT.spawn(world, null, null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), null,false, false);
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
