package com.hasoook.recipes;

import com.google.common.collect.ImmutableMap;
import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class LureSwords extends SpecialCraftingRecipe {
    public LureSwords(CraftingRecipeCategory category) {
        super(category);
    }

    private int itemSlot;

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        int swordCount = 0; // 记录剑
        int fishingRodCount = 0; // 记录钓竿
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof SwordItem && EnchantmentHelper.getLevel(Enchantments.LURE, stack) == 0) {
                    swordCount++;
                } else if (stack.getItem() == Items.FISHING_ROD) {
                    fishingRodCount++;
                } else fishingRodCount -= 9;
            }
        }
        return swordCount == 1 && fishingRodCount == 1;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        int swordCount = 0;
        int fishingRodCount = 0;

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof SwordItem && EnchantmentHelper.getLevel(Enchantments.LURE, stack) == 0) {
                    swordCount++;
                    itemSlot = i;
                } else if (stack.getItem() == Items.FISHING_ROD) {
                    fishingRodCount++;
                } else {
                    fishingRodCount -= 9;
                }
            }
        }

        // 检查合成条件：必须有且只有一个钻石剑和一个钓竿
        if (swordCount == 1 && fishingRodCount == 1) {
            ItemStack swordStack = inventory.getStack(itemSlot).copy();  // 复制钻石剑的物品栈

            // 处理NBT复制和附魔
            if (swordStack.hasNbt()) {
                NbtCompound nbt = swordStack.getNbt().copy();  // 复制剑的NBT数据
                swordStack.setCount(1);  // 设置数量为1，以便后续创建新物品栈
                swordStack.setNbt(nbt);  // 给新物品设置复制的NBT数据
                swordStack.addEnchantment(Enchantments.LURE, 1);  // 添加钓饵附魔
            }

            return swordStack;
        }

        return ItemStack.EMPTY;  // 合成条件不满足，返回空物品栈
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }
}
