package com.hasoook.recipes;

import com.hasoook.Hasoook;
import com.hasoook.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class WrittenBookPage extends SpecialCraftingRecipe {
    public WrittenBookPage(CraftingRecipeCategory category) {
        super(category);
    }

    private int itemSlot;

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        int count = 0; // 记录羽毛的数量
        int count2 = 0; // 记录成书的数量
        int count3 = 0; // 记录墨囊的数量
        int count4 = 0; // 记录两者之外物品的数量
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.FEATHER) {
                    count++;
                } else if (stack.getItem() == Items.WRITTEN_BOOK) {
                    count2++;
                } else if (stack.getItem() == Items.INK_SAC) {
                    count3++;
                } else count4++;
            }
        }
        return count == 1 && count2 == 1 && count3 == 1 && count4 == 0;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        int count = 0; // 记录羽毛的数量
        int count2 = 0; // 记录成书的数量
        int count3 = 0; // 记录墨囊的数量
        int count4 = 0; // 记录两者之外物品的数量
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.FEATHER) {
                    count++;
                } else if (stack.getItem() == Items.WRITTEN_BOOK) {
                    count2++;
                    itemSlot = i;
                } else if (stack.getItem() == Items.INK_SAC) {
                    count3++;
                } else count4++;

            }
        }
        if (count == 1 && count2 == 1 && count3 == 1 && count4 == 0) {
            ItemStack stack = inventory.getStack(itemSlot);
            if (stack.hasNbt()) {
                NbtCompound nbt = stack.getNbt(); // 保存成书的nbt
                ItemStack stack2 = new ItemStack(Items.WRITABLE_BOOK); // 设置一个新的书与笔
                stack2.setNbt(nbt); // 给书与笔设置nbt
                return stack2; // 返回设置好的书与笔
            }
            return new ItemStack(Items.WRITABLE_BOOK,1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Hasoook.WRITTEN_BOOK_PAGE;
    }
}
