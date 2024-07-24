package com.hasoook.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public abstract class LureItemMixin extends Item {
    public LureItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ItemStack offhandStack = user.getStackInHand(Hand.OFF_HAND);

        if (user.fishHook != null && offhandStack.getItem() != Items.FISHING_ROD) {
            if (!world.isClient) {
                int damageAmount = user.fishHook.use(itemStack);
                itemStack.damage(damageAmount, user, p -> {
                    p.sendToolBreakStatus(hand);
                });
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            if (!world.isClient && offhandStack.getItem() != Items.FISHING_ROD && EnchantmentHelper.getLevel(Enchantments.LURE, itemStack) > 0) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
                world.spawnEntity(new FishingBobberEntity(user, world, EnchantmentHelper.getLuckOfTheSea(itemStack), EnchantmentHelper.getLevel(Enchantments.LURE, itemStack)));
            } else {
                return TypedActionResult.pass(itemStack);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }
}
