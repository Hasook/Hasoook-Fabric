package com.hasoook.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends Entity {

    /*这是从KCN_WS的mixin教程中所学习的代码，有些许修改
    非常感谢KCN_WS*/

    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "explode" , at = @At("HEAD"), cancellable = true)
    public void mixinExplode(CallbackInfo ci) {
        //如果执行位置是服务端 并且 所在世界维度是地狱
        if (!this.getWorld().isClient && this.getWorld().getRegistryKey().equals(World.NETHER)) {
            //阻止这个事件的发生
            ci.cancel();
            //移除实体
            this.discard();
            //将当前世界中生物对方块破坏的规则值存储在名为bl变量中
            boolean bl = getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            //在苦力怕位置生成一个带火焰的爆炸
            getWorld().createExplosion(this, getX(), getY(), getZ(), 3F, bl, World.ExplosionSourceType.MOB);
        }

    }

}
