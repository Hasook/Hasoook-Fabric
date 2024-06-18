package com.hasoook.effect;

import com.hasoook.effect.custom.NormalEffect;
import com.hasoook.effect.custom.PretentiousnessEffect;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.hasoook.Hasoook.MOD_ID;

public class ModStatusEffects {

    // 构造函数为空的私有类，用于表示IRON效果
    public static final StatusEffect PRETENTIOUSNESS = new PretentiousnessEffect(StatusEffectCategory.BENEFICIAL, 0x4aedd9);

    private static void ironAttributeModifiers(EntityAttributeInstance instance) {
        // 可以在这里添加自定义效果对实体属性的修改
    }

    public static void registerModEffect(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "pretentiousness"), ModStatusEffects.PRETENTIOUSNESS);
    }

}
