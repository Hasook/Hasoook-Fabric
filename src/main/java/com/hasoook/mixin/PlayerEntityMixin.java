package com.hasoook.mixin;

import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // 飞雷神三叉戟
        if (isSneaking() && !getWorld().isClient) {
            World world = getEntityWorld();
            double range = 32.0; //寻找三叉戟的范围
            BlockPos pos = getBlockPos();

            world.getEntitiesByClass(TridentEntity.class, new Box(pos).expand(range), trident ->
                    trident.getOwner() == this && EnchantmentHelper.getLevel(ModEnchantments.Hiraishin, trident.getItemStack()) > 0
            ).stream().findFirst().ifPresent(trident -> {
                teleport(trident.getX(), trident.getY(), trident.getZ());
                world.playSound(null, trident.getX(), trident.getY(), trident.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        }
    }

}

