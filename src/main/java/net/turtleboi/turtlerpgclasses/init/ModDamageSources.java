package net.turtleboi.turtlerpgclasses.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {

    public static DamageSource causeBleedDamage(Entity source) {
        return new BleedDamageSource("bleed", source);
    }

    public static class BleedDamageSource extends DamageSource {
        private final Entity source;

        public BleedDamageSource(String damageTypeIn, Entity source) {
            super(damageTypeIn);
            this.source = source;
        }

        @Nullable
        @Override
        public Entity getEntity() {
            return this.source;
        }

        @Override
        public boolean isBypassArmor() {
            return true;
        }

        @Override
        public boolean isMagic() {
            return false;
        }

        @Override
        public boolean isExplosion() {
            return false;
        }

        @Override
        public boolean scalesWithDifficulty() {
            return false;
        }

        @Override
        public boolean isProjectile() {
            return false;
        }

        @Override
        public boolean isFall() {
            return false;
        }

        @Override
        public boolean isFire() {
            return false;
        }

        @Override
        public boolean isNoAggro() {
            return false;
        }

        @Override
        public boolean isCreativePlayer() {
            return false;
        }
    }
}
