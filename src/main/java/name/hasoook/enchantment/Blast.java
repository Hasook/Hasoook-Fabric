package name.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class Blast extends Enchantment {
    public Blast(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        boolean bl = user.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        user.getWorld().createExplosion(user, target.getX(), target.getY(), target.getZ(), 1F * getMaxLevel(), bl, World.ExplosionSourceType.MOB);
    }

    public int getMinPower(int level) {
        return 3;
    }

    //最大等级
    public int getMaxLevel() {
        return 3;
    }

    //是否为诅咒
    public boolean isCursed() {
        return false;
    }

}