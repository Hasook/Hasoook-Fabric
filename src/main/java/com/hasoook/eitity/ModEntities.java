package com.hasoook.eitity;

import com.hasoook.Hasoook;
import com.hasoook.eitity.custom.CattivaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CattivaEntity> CATTIVA = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Hasoook.MOD_ID, "cattiva"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CattivaEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 1.75f)).build());
}
