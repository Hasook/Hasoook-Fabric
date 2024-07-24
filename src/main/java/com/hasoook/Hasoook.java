package com.hasoook;

import com.hasoook.block.ModBlocks;
import com.hasoook.block.entity.ModBlockEntities;
import com.hasoook.effect.ModStatusEffects;
import com.hasoook.eitity.ModEntities;
import com.hasoook.eitity.custom.CattivaEntity;
import com.hasoook.enchantment.ModEnchantments;
import com.hasoook.item.ModItemGroups;
import com.hasoook.item.ModItems;
import com.hasoook.potions.ModPotions;
import com.hasoook.recipes.CopyEnchantmentBook;
import com.hasoook.recipes.LureSwords;
import com.hasoook.recipes.WrittenBookPage;
import com.hasoook.util.ModCustomTrades;
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
	public static final RecipeSerializer<WrittenBookPage> WRITTEN_BOOK_PAGE = RecipeSerializer.register("written_book_page", new SpecialRecipeSerializer<>(WrittenBookPage::new));
	public static final RecipeSerializer<LureSwords> LURE_SWORDS = RecipeSerializer.register("lure_swords", new SpecialRecipeSerializer<>(LureSwords::new));

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroup();
		ModItems.registerModItem();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerModEnchantments();
		ModStatusEffects.registerModEffect();
		ModPotions.registerPotions();
		ModCustomTrades.addTrade();

		FabricDefaultAttributeRegistry.register(ModEntities.CATTIVA, CattivaEntity.setAttributes());
	}
}