package com.hasoook;

import com.hasoook.block.ModBlocks;
import com.hasoook.block.entity.ModBlockEntities;
import com.hasoook.effect.ModStatusEffects;
import com.hasoook.eitity.ModEntities;
import com.hasoook.eitity.custom.CattivaEntity;
import com.hasoook.enchantment.ModEnchantments;
import com.hasoook.event.UseEntityHandler;
import com.hasoook.item.ModItemGroups;
import com.hasoook.item.ModItems;
import com.hasoook.potions.ModPotions;
import com.hasoook.recipes.CopyEnchantmentBook;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hasoook implements ModInitializer {
	public static final String MOD_ID = "hasoook";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RecipeSerializer<CopyEnchantmentBook> COPY_ENCHANTMENT_BOOK = RecipeSerializer.register("copy_enchantment_book", new SpecialRecipeSerializer<>(CopyEnchantmentBook::new));

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroup();
		ModItems.registerModItem();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerModEnchantments();
		ModStatusEffects.registerModEffect();
		ModPotions.registerPotions();

		FabricDefaultAttributeRegistry.register(ModEntities.CATTIVA, CattivaEntity.setAttributes());

		UseEntityCallback.EVENT.register(new UseEntityHandler());
	}
}