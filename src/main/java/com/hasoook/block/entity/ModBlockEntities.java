package com.hasoook.block.entity;

import com.hasoook.Hasoook;
import com.hasoook.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<AirBlockBlockEntity> AIR_BLOCK_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Hasoook.MOD_ID, "air_block"),
                    FabricBlockEntityTypeBuilder.create(AirBlockBlockEntity::new,
                            ModBlocks.AIRBLOCK).build());


    public static void registerBlockEntities() {
        Hasoook.LOGGER.info("Registering Block Entities for " + Hasoook.MOD_ID);
    }
}
