package com.hasoook.eitity.custom;

import com.hasoook.eitity.ModEntities;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class CattivaEntity extends TameableEntity implements GeoEntity {
     int mining = 0;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(CattivaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public CattivaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        // 默认属性
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this)); //游泳
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.0)); // 受伤后逃跑
        this.goalSelector.add(1, new SitGoal(this)); //可以坐下
        this.goalSelector.add(1, new AnimalMateGoal(this, 0.8));  // 交配
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(Items.TROPICAL_FISH, Items.COOKED_COD, Items.COOKED_SALMON), false)); // 诱惑
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0f, 1.0, 1.0, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test)); //逃离玩家
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));  // 添加跟随主人目标
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1)); //游荡
        this.goalSelector.add(4, new FollowParentGoal(this, 1));  // 跟随父母目标
        this.goalSelector.add(5, new PounceAtTargetGoal(this, 0.4F));  // 向目标跳扑攻击
        this.goalSelector.add(6, new AttackGoal(this));  // 攻击目标
        this.goalSelector.add(7, new LookAroundGoal(this)); //环顾四周
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));  // 添加看向玩家目标

        // 设置目标选择器
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(1, new AnimalMateGoal(this, 0.8f));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (this.isBreedingItem(stack)){
            return super.interactMob(player, hand);
        }
        Item item = stack.getItem(); // 获取玩家手上的物品
        Item itemForTaming = Items.COOKED_COD; //用来驯服的物品（鳕鱼）
        // 如果物品=熟鳕鱼or熟鲑鱼，而且捣蛋猫没有被驯服
        if (item == Items.COOKED_COD || item == Items.COOKED_SALMON && !this.isTamed()) {
            if (this.getWorld().isClient) {
                return ActionResult.CONSUME;
                //如果玩家不是创造模式，而且捣蛋猫没有被驯服
            } else if (!player.getAbilities().creativeMode && !this.isTamed()) {
                stack.decrement(1); //减少一个物品
            }
            if (!this.getWorld().isClient && this.random.nextInt(3) == 0 && !this.isTamed()) {
                setOwner(player);
                this.navigation.recalculatePath();
                this.setTarget(null);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                setSit(true);
            }else if (!this.isTamed()){
                this.getWorld().sendEntityStatus(this, (byte)6);
            }
            return ActionResult.SUCCESS;
        }
        if (isTamed() && isOwner(player) && !this.getWorld().isClient && hand == Hand.MAIN_HAND) {
            setSit(!isSitting());
            return ActionResult.SUCCESS;
        }
        if (stack.getItem() == itemForTaming) {
            return ActionResult.PASS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.TROPICAL_FISH;
    }

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    @Override
    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SITTING, nbt.getBoolean("sitting"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("sitting", this.dataTracker.get(SITTING));
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(30);
        }else {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20);
        }
    }

    @Override
    public Team getScoreboardTeam() {
        return super.getScoreboardTeam();
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
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
            // 行走动画
        }else if (this.isSitting()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.mining", Animation.LoopType.LOOP));
            // 坐下动画
            return PlayState.CONTINUE;
        }else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.normal", Animation.LoopType.LOOP));
            // 空闲动画
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}
