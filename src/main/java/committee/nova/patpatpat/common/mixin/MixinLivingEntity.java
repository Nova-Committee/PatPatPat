package committee.nova.patpatpat.common.mixin;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.api.IPattable;
import committee.nova.patpatpat.common.util.Utilities;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements IPattable {
    private int joy;

    @Shadow
    public abstract void heal(float amount);

    @Shadow
    public abstract net.minecraft.util.math.random.Random getRandom();

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void inject$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("patpatpat_joy", joy);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void inject$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        joy = nbt.getInt("patpatpat_joy");
    }

    @Override
    public int getJoy() {
        return joy;
    }

    @Override
    public void setJoy(int joy) {
        this.joy = joy;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void inject$tick(CallbackInfo ci) {
        if (getWorld().isClient) return;
        final long time = getWorld().getTime() - getId();
        if (time % 5 != 0) return;
        final List<SoundEvent> sounds = Utilities.getPattedSounds(this);
        if (sounds.isEmpty()) return;
        final int joy0 = Math.max(0, joy - 1);
        joy = joy0;
        final Random r = getRandom();
        if (joy0 > 0) {
            if (time % 12 == 0) {
                getWorld().playSound(null, getX(), getY(), getZ(),
                        sounds.get(r.nextInt(sounds.size())), getSoundCategory(),
                        1.0F + r.nextFloat() / 5.0F, 1.0F + r.nextFloat() / 4.0F);
            }
            if (r.nextInt(101) < 3) {
                heal(1.0F);
                if (getWorld() instanceof ServerWorld) {
                    final ServerWorld sw = ((ServerWorld) getWorld());
                    sw.spawnParticles(ParticleTypes.HEART, getX(), getY() + .75, getZ(),
                            1, .0, .0, .0, .0);
                    if (r.nextBoolean()) {
                        final ExperienceOrbEntity orb = new ExperienceOrbEntity(sw, getX(), getY(), getZ(), 1 + r.nextInt(5));
                        sw.spawnEntity(orb);
                    }
                }
            }
        } else setSilent(false);
        final PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(getId());
        buf.writeInt(joy);
        if (!(getWorld() instanceof ServerWorld)) return;
        for (final ServerPlayerEntity p : PlayerLookup.world((ServerWorld) getWorld()))
            ServerPlayNetworking.send(p, PatPatPat.PAT, buf);
    }
}
