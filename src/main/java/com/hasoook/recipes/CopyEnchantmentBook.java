package com.hasoook.recipes;

import com.hasoook.Hasoook;
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
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.WRITABLE_BOOK) {
                    count++;
                } else if (stack.getItem() == Items.ENCHANTED_BOOK) {
                    count2 ++;
                }
            }
        }
        return count == 1 && count2 == 1;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.WRITABLE_BOOK) {
                    count++;
                } else if (stack.getItem() == Items.ENCHANTED_BOOK) {
                    count2 ++;
                    itemSlot = i;
                }
            }
        }
        if (count == 1 && count2 == 1) {
            ItemStack stack = inventory.getStack(itemSlot);
            Item item = inventory.getStack(itemSlot).getItem();
            if (stack.hasNbt()) {
                NbtCompound nbt = stack.getNbt();
                ItemStack stack2 = new ItemStack(item,2);
                stack2.setNbt(nbt);
                return stack2;
            }
            return new ItemStack(item,2);
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
