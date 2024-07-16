package com.hasoook.item;

import com.hasoook.Hasoook;
import com.hasoook.eitity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    //注册物品
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings())); //红宝石

    public static final Item CATTIVA_SPAWN_EGG = registerItem("cattiva_spawn_egg",
            new SpawnEggItem(ModEntities.CATTIVA, 0xffc5c9, 0x976069,
                    new FabricItemSettings()));

    //创造物品选项卡
    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Hasoook.MOD_ID, name), item);
    }

    public static void registerModItem() {
        Hasoook.LOGGER.info("Registering Mod Items for" + Hasoook.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
