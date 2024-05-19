package com.hasoook.block;

import com.hasoook.Hasoook;
import com.hasoook.block.custom.AirBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.POWDER_SNOW).sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block AIRBLOCK = registerBlock("air_block",
            new AirBlock(FabricBlockSettings.copyOf(Blocks.GLASS).sounds(BlockSoundGroup.AMETHYST_BLOCK).noBlockBreakParticles()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Hasoook.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Hasoook.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Hasoook.LOGGER.info("Registering ModBlocks for " + Hasoook.MOD_ID);
    }
}