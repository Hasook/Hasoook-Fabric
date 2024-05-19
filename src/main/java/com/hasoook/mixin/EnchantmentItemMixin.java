package com.hasoook.mixin;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Enchantment.class)
public class EnchantmentItemMixin {
    /**
     * @author
     * Hasoook
     * @reason
     *
     */
    @Overwrite
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

}
