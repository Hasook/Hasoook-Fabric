package com.hasoook.item;

import com.hasoook.Hasoook;
import com.hasoook.block.ModBlocks;
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
    public static final ItemGroup HASOOOK_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Hasoook.MOD_ID, "air_block"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hasoook"))
                    .icon(() -> new ItemStack(Items.ENCHANTED_BOOK)).entries((displayContext, entries) -> {
                        entries.add(Items.SUGAR); // 原版物品
                        entries.add(ModItems.RUBY); // 模组物品
                        entries.add(ModItems.TOTEM_OF_SURRENDER); // 模组物品
                        entries.add(ModBlocks.RUBY_BLOCK); // 模组方块
                        entries.add(ModBlocks.AIRBLOCK); // 空气方块
                        entries.add(ModItems.ONE_HIT_OBLITERATOR); // 必杀之剑
                        entries.add(ModItems.CATTIVA_SPAWN_EGG); // 捣蛋猫刷怪蛋

                    }).build());

    public static void registerItemGroup() {
        Hasoook.LOGGER.info("Registering Item Group for " + Hasoook.MOD_ID);
    }
}
