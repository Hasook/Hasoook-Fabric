package name.hasoook.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class Blunt extends Enchantment {
    protected Blunt(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    public float getAttackDamage(int level, EntityGroup group) {
        return -1 * level;
    }
    public int getMinPower(int level) {
        return 3;
    }
    public boolean isCursed() {
        return true;
    }  //是否为诅咒
    public int getMaxLevel() {
        return 5;
    }  //最大等级

}
