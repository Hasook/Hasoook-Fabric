package com.hasoook.event;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UseEntityHandler implements UseEntityCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        // 获取玩家主手物品
        ItemStack heldItem = player.getStackInHand(hand);

        if (!world.isClient && heldItem.getItem() == Items.CHAIN && hasBindingCurse(heldItem)) {
            String targetName = entity.getName().getString();
            String playerName = player.getName().getString();
            String targetUUID = entity.getUuid().toString(); // 获取目标实体的UUID

            player.sendMessage(Text.of("与 " + targetName + " 建立了生命连接"), true);
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            heldItem.decrement(1);

            if (entity instanceof PlayerEntity targetPlayer) {
                targetPlayer.sendMessage(Text.of("与 " + playerName + " 建立了生命连接"), true);
            }

        }
        return ActionResult.PASS;
    }

    // 检查玩家主手物品是否附魔有绑定诅咒
    private boolean hasBindingCurse(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.BINDING_CURSE, stack) > 0;
    }

}
