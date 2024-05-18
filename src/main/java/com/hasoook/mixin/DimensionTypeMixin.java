package com.hasoook.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DimensionType.class) // 只有被 @Mixin 修饰的类才会被识别并注入
public class DimensionTypeMixin {

    /*@作者 TuRou
    使水可以放置在所有维度上。*/

    /**
     * @author
     * @reason
     */
    @Overwrite()
    public boolean ultrawarm() {
        return false; // 所有维度都返回 false, 包括地狱
    }

}
