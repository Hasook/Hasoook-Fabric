package com.hasoook.item;

import com.hasoook.Hasoook;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

//模组创造物品选项卡
public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Hasoook.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hasoook"))
                    .icon(() -> new ItemStack(ModItems.RUBY)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY); //模组物品
                        entries.add(Items.SUGAR); //原版物品


                    }).build());

    public static void registerItemGroup() {
        Hasoook.LOGGER.info("Registering Item Group for " + Hasoook.MOD_ID);
    }
}
