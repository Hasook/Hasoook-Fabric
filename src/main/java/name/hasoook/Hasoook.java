package name.hasoook;

import name.hasoook.enchantment.ModEnchantments;
import name.hasoook.item.ModItemGroups;
import name.hasoook.item.ModItems;
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
		ModEnchantments.registerModEnchantments();

	}
}