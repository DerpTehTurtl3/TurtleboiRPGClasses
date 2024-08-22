package net.turtleboi.turtlerpgclasses.entity.weapons;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.turtleboi.turtlerpgclasses.entity.ModEntities;
import net.turtleboi.turtlerpgclasses.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ThrowableDagger extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> HIT_BLOCK = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> ROTATE_VARIATION = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(ThrowableDagger.class, EntityDataSerializers.BYTE);
    private ItemStack daggerItem = ItemStack.EMPTY;
    private boolean dealtDamage = false;
    public float lastYP = 0.0F;
    public float lastZP = 0.0F;
    private int returnTickCount = 0;

    public ThrowableDagger(EntityType<? extends ThrowableDagger> entityType, Level level) {
        super(entityType, level);
    }

    public ThrowableDagger(Level level, LivingEntity thrower, ItemStack itemStack, ResourceLocation resourceLocation, Float thrownDamage) {
        super(ModEntities.THROWABLE_DAGGER.get(), thrower, level);
        if (itemStack != null && this.daggerItem.isEmpty()) {
            this.daggerItem = itemStack.copy();
        }
        this.entityData.set(ITEM_STACK, this.daggerItem);
        this.entityData.set(TEXTURE, resourceLocation.toString());
        this.entityData.set(ID_FOIL, this.daggerItem.hasFoil());
        this.entityData.set(DAMAGE, thrownDamage);
        this.entityData.set(HIT_BLOCK, false);
        this.entityData.set(RETURNING, false);

        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(this.daggerItem));

        float rotateVariation = 1.0F + level.random.nextFloat() * 44.0F;
        this.entityData.set(ROTATE_VARIATION, rotateVariation);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.WOODEN_DAGGER.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEM_STACK, ItemStack.EMPTY);
        this.entityData.define(ID_FOIL, false);
        this.entityData.define(TEXTURE, "");
        this.entityData.define(DAMAGE, 0.0F);
        this.entityData.define(HIT_BLOCK, false);
        this.entityData.define(RETURNING, false);
        this.entityData.define(ROTATE_VARIATION, 0.0F);
        this.entityData.define(LOYALTY_LEVEL, (byte) 0);
    }

    public float getDaggerDamage() {
        return this.entityData.get(DAMAGE);
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(this.entityData.get(TEXTURE));
    }

    public float getRotateVariation() {
        return this.entityData.get(ROTATE_VARIATION);
    }

    public boolean isReturning() {
        return this.entityData.get(RETURNING);
    }

    public byte getLoyaltyLevel() {
        return this.entityData.get(LOYALTY_LEVEL);
    }

    @Override
    public void tick() {
        super.tick();
        this.daggerItem = this.entityData.get(ITEM_STACK);

        if (this.getLoyaltyLevel() > 0 && !this.level.isClientSide) {
            Player owner = (Player) this.getOwner();
            if (this.dealtDamage || hasHitBlock()) {
                if (owner != null && !this.isAcceptableReturnOwner()) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                    this.discard();
                } else if (owner != null) {
                    this.entityData.set(RETURNING, true);
                    this.returnTickCount++;
                    this.setNoGravity(true);
                    this.noPhysics = true;
                    Vec3 vec3 = new Vec3(owner.getX() - this.getX(), (owner.getEyeY() - 1) - this.getY(), owner.getZ() - this.getZ());
                    this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * this.getLoyaltyLevel(), this.getZ());
                    if (this.level.isClientSide) {
                        this.yOld = this.getY();
                    }
                    double d0 = 0.1 * this.getLoyaltyLevel();
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
                    if (this.returnTickCount == 0) {
                        this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                    }
                    if (this.distanceTo(owner) < 1.0D) {
                        owner.getInventory().add(this.getPickupItem());
                        this.discard();
                    }
                }
            }
        }
    }

    private boolean isAcceptableReturnOwner() {
        Entity owner = this.getOwner();
        if (owner != null && owner.isAlive()) {
            return !owner.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.isReturning()) {
            this.entityData.set(HIT_BLOCK, true);
        } else {
            this.entityData.set(HIT_BLOCK, true);
            this.setDeltaMovement(Vec3.ZERO);
        }
        this.dealtDamage = true;
    }

    public boolean hasHitBlock() {
        return this.entityData.get(HIT_BLOCK);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (!this.isReturning()) {
            Entity owner = this.getOwner();
            Entity target = result.getEntity();
            float damage = this.getDaggerDamage();
            if (target instanceof LivingEntity livingTarget) {
                damage += EnchantmentHelper.getDamageBonus(this.daggerItem, livingTarget.getMobType());
                int fireAspectLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, this.daggerItem);
                if (fireAspectLevel > 0) {
                    livingTarget.setSecondsOnFire(fireAspectLevel * 4);
                }
            }

            if (!this.dealtDamage) {
                DamageSource source = DamageSource.thrown(this, owner == null ? this : owner);

                if (target.hurt(source, damage)) {
                    if (target instanceof LivingEntity) {
                        assert owner != null;
                        EnchantmentHelper.doPostHurtEffects((LivingEntity) target, owner);
                    }
                    this.dealtDamage = true;
                }

                if (!this.level.isClientSide) {
                    this.daggerItem.hurtAndBreak(1, (LivingEntity) this.getOwner(), (player) -> {
                        player.broadcastBreakEvent(player.getUsedItemHand());
                        this.playSound(SoundEvents.ITEM_BREAK, 10.0F, 1.0F);
                    });
                    this.entityData.set(ITEM_STACK, this.daggerItem.copy());
                    if (this.daggerItem.isEmpty()) {
                        this.discard();
                    }
                }
            }
            this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
            this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
        }
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (!this.level.isClientSide && hasHitBlock() && this.isAlive()) {
            double distanceToPlayer = this.distanceToSqr(player);
            if (distanceToPlayer < 16.0) {
                this.moveTowardPlayer(player);
                if (distanceToPlayer < 4.0) {
                    ItemStack itemstack = this.getPickupItem();
                    if (player.getInventory().add(itemstack)) {
                        this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                        this.discard();
                    }
                }
            }
        }
    }

    private void moveTowardPlayer(Player player) {
        Vec3 direction = new Vec3(player.getX() - this.getX(), player.getY() + (player.getEyeHeight() - 1) - this.getY(), player.getZ() - this.getZ());
        direction = direction.normalize();
        double speed = 0.15;
        this.setDeltaMovement(direction.scale(speed));
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    public ItemStack getPickupItem() {
        if (this.daggerItem.isEmpty()) {
            return new ItemStack(ModItems.WOODEN_DAGGER.get());
        }
        return this.daggerItem.copy();
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return !this.entityData.get(RETURNING);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.entityData.get(RETURNING);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Dagger", this.daggerItem.save(new CompoundTag()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
        compound.putBoolean("HitBlock", this.entityData.get(HIT_BLOCK));
        compound.putBoolean("Returning", this.entityData.get(RETURNING));
        compound.putFloat("LastYP", this.lastYP);
        compound.putFloat("LastZP", this.lastZP);
        compound.putFloat("RotateVariation", this.entityData.get(ROTATE_VARIATION));
        compound.putByte("LoyaltyLevel", this.getLoyaltyLevel());
        if (!this.entityData.get(TEXTURE).isEmpty()) {
            compound.putString("Texture", this.entityData.get(TEXTURE));
        }
        compound.putFloat("Damage", this.entityData.get(DAMAGE));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Dagger", 10)) {
            this.daggerItem = ItemStack.of(compound.getCompound("Dagger"));
        }
        this.dealtDamage = compound.getBoolean("DealtDamage");
        if (compound.contains("HitBlock")) {
            this.entityData.set(HIT_BLOCK, compound.getBoolean("HitBlock"));
        }
        if (compound.contains("Returning")) {
            this.entityData.set(RETURNING, compound.getBoolean("Returning"));
        }
        if (compound.contains("LastYP")) {
            this.lastYP = compound.getFloat("LastYP");
        }
        if (compound.contains("LastZP")) {
            this.lastZP = compound.getFloat("LastZP");
        }
        if (compound.contains("RotateVariation")) {
            this.entityData.set(ROTATE_VARIATION, compound.getFloat("RotateVariation"));
        }
        if (compound.contains("LoyaltyLevel")) {
            this.entityData.set(LOYALTY_LEVEL, compound.getByte("LoyaltyLevel"));
        }
        this.entityData.set(ID_FOIL, this.daggerItem.hasFoil());
        if (compound.contains("Texture", 8)) {
            this.entityData.set(TEXTURE, compound.getString("Texture"));
        }
        if (compound.contains("Damage", 5)) {
            this.entityData.set(DAMAGE, compound.getFloat("Damage"));
        }
        this.entityData.set(ITEM_STACK, this.daggerItem);
    }
}
