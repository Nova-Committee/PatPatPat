package committee.nova.patpatpat.common.mixin;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void inject$attack(Entity target, CallbackInfo ci) {
        if (!isSneaking() || !Utilities.isPattable(target)) return;
        ci.cancel();
        if (world.isClient) return;
        if (target instanceof AnimalEntity) {
            final AnimalEntity animal = (AnimalEntity) target;
            if (animal.isInLove() || animal.isDead()) return;
            if (target instanceof TameableEntity && !((TameableEntity) target).isTamed()) return;
        }
        Utilities.setJoyForEntity(target, 10);
        target.setSilent(true);
    }
}
