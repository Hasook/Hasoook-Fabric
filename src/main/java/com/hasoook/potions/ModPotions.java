package com.hasoook.potions;

import com.hasoook.Hasoook;
import com.hasoook.effect.ModStatusEffects;
import com.hasoook.item.ModItems;
import com.hasoook.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static Potion PRETENTIOUSNESS_POTION;

    public static Potion registerPotion(String name, int duration, int amplifier, StatusEffect statusEffects) {
        return Registry.register(Registries.POTION, new Identifier(Hasoook.MOD_ID, name),
                new Potion(new StatusEffectInstance(statusEffects, duration, amplifier)));
    }

    public static void registerPotions() {
        PRETENTIOUSNESS_POTION = registerPotion("pretentiousness_potion",3600,0, ModStatusEffects.PRETENTIOUSNESS);

        registerPotionRecipes();
    }

    private static void registerPotionRecipes() {
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, Items.DIAMOND, ModPotions.PRETENTIOUSNESS_POTION);
    }

}
