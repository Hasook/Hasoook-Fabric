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

public class CopyEnchantmentBook extends SpecialCraftingRecipe {
    public CopyEnchantmentBook(CraftingRecipeCategory category) {
        super(category);
    }

    private int itemSlot;

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.WRITABLE_BOOK && EnchantmentHelper.getLevel(ModEnchantments.Transcription, stack) > 0) {
                    count++;
                } else if (stack.getItem() == Items.ENCHANTED_BOOK) {
                    count2++;
                } else count3++;
            }
        }
        return count >= 1 && count2 == 1 && count3 == 0;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.WRITABLE_BOOK && EnchantmentHelper.getLevel(ModEnchantments.Transcription, stack) > 0) {
                    count++;
                } else if (stack.getItem() == Items.ENCHANTED_BOOK) {
                    count2++;
                    itemSlot = i;
                } else {
                    count3++;
                }
            }
        }
        if (count >= 1 && count2 == 1 && count3 == 0) {
            ItemStack stack = inventory.getStack(itemSlot);
            Item item = inventory.getStack(itemSlot).getItem();
            if (stack.hasNbt()) {
                NbtCompound nbt = stack.getNbt();
                ItemStack stack2 = new ItemStack(item,count + 1);
                stack2.setNbt(nbt);
                return stack2;
            }
            return new ItemStack(item,count + 1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Hasoook.COPY_ENCHANTMENT_BOOK;
    }
}
