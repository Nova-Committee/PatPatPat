package committee.nova.patpatpat.mixin.common;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
    protected MixinEntityPlayer(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"), cancellable = true)
    private void inject$attack(Entity target, CallbackInfo ci) {
        if (!isSneaking() || !Utilities.isPattable(target)) return;
        ci.cancel();
        if (world.isRemote) return;
        if (target instanceof EntityAnimal) {
            final EntityAnimal animal = (EntityAnimal) target;
            if (animal.isInLove() || animal.getHealth() <= .0F) return;
            if (target instanceof EntityTameable && !((EntityTameable) target).isTamed()) return;
        }
        Utilities.setJoyForEntity(target, 10);
        target.setSilent(true);
    }
}
