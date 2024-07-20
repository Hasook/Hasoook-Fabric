package com.hasoook.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModCustomTrades {

    public static void addTrade() {
        sellSilkBag();
    }

    private static void sellSilkBag() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            // 创建一个可书写的书 ItemStack
            ItemStack stack = new ItemStack(Items.WRITABLE_BOOK);

            // 创建 NBT 数据
            NbtCompound baseNbt = new NbtCompound();
            NbtList list = new NbtList();
            NbtCompound enchantmentTag = new NbtCompound();
            enchantmentTag.putString("id", "hasoook:transcription");
            enchantmentTag.putInt("lvl", 1);
            list.add(enchantmentTag);
            baseNbt.put("Enchantments", list);

            // 将 NBT 数据设置到 ItemStack
            stack.setNbt(baseNbt);
            factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 5), stack, 1, 3, 0.5f));
        });
    }

}
