package com.hasoook;

import com.hasoook.block.ModBlocks;
import com.hasoook.block.entity.ModBlockEntities;
import com.hasoook.effect.ModStatusEffects;
import com.hasoook.enchantment.ModEnchantments;
import com.hasoook.item.ModItemGroups;
import com.hasoook.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hasoook implements ModInitializer {
	public static final String MOD_ID = "hasoook";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroup();
		ModItems.registerModItem();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerModEnchantments();
		ModStatusEffects.registerModEffect();
	}
}