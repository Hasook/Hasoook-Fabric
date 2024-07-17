package com.hasoook.eitity.custom;

import com.hasoook.eitity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class CattivaEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public CattivaEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private int interactionCount = 0;

    public static DefaultAttributeContainer.Builder setAttributes() {
        // 默认属性
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this)); //游泳
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2)); // 受伤后逃跑
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(Items.COD, Items.SALMON), false)); // 诱惑
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0f, 1.0, 1.2, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test)); //逃离玩家
        this.goalSelector.add(2, new AnimalMateGoal(this, 0.8));  // 交配
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1)); //游荡
        this.goalSelector.add(4, new FollowParentGoal(this, 1));  // 跟随父母目标
        this.goalSelector.add(4, new LookAroundGoal(this)); //环顾四周
        this.goalSelector.add(8, new PounceAtTargetGoal(this, 0.4F));  // 向目标跳扑攻击
        this.goalSelector.add(9, new AttackGoal(this));  // 攻击目标

        // 设置目标选择器
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            this.interactionCount++;
            player.sendMessage(Text.of("互动了" + interactionCount + "次"), false);
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CATTIVA.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walk", Animation.LoopType.LOOP));
            //行走动画
        }else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.normal", Animation.LoopType.LOOP));
            //空闲动画
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
