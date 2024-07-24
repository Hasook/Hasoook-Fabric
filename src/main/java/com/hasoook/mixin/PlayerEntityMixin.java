package com.hasoook.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Final
    @Shadow
    private PlayerInventory inventory;

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void onDropItem(ItemStack itemStack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (EnchantmentHelper.getLevel(Enchantments.BINDING_CURSE, itemStack) > 0) {
            // 物品是否有绑定诅咒
            cir.cancel();
            cir.setReturnValue(null);
            ItemStack Stack = itemStack.copy();
            //取消丢弃然后复制一个物品

            for (int slot = 0; slot < this.inventory.size(); ++slot) {
                // 把物品放入玩家背包里的空槽位
                ItemStack currentStack = this.inventory.getStack(slot);
                if (currentStack.isEmpty()) {
                    this.inventory.setStack(slot, Stack);
                    break;
                }
            }
        }
    }

}