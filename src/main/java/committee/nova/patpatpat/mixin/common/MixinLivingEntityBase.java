package committee.nova.patpatpat.mixin.common;

import committee.nova.patpatpat.common.api.IPattable;
import committee.nova.patpatpat.common.network.msg.SPacketJoySync;
import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(EntityLivingBase.class)
public abstract class MixinLivingEntityBase extends Entity implements IPattable {
    @Shadow
    public abstract Random getRNG();

    @Shadow
    public abstract void heal(float healAmount);

    private int joy;

    public MixinLivingEntityBase(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Inject(method = "writeAdditional", at = @At("HEAD"))
    private void inject$write(NBTTagCompound tag, CallbackInfo ci) {
        tag.putInt("patpatpat_joy", joy);
    }

    @Inject(method = "readAdditional", at = @At("HEAD"))
    private void inject$read(NBTTagCompound tag, CallbackInfo ci) {
        joy = tag.getInt("patpatpat_joy");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void inject$tick(CallbackInfo ci) {
        if (world.isRemote) return;
        if (!(world instanceof WorldServer)) return;
        final WorldServer sw = ((WorldServer) world);
        final long time = sw.getGameTime() - getEntityId();
        if (time % 5 != 0) return;
        final List<SoundEvent> sounds = Utilities.getPattedSounds(this);
        if (sounds.isEmpty()) return;
        final int joy0 = Math.max(0, joy - 1);
        joy = joy0;
        final Random r = getRNG();
        if (joy0 > 0) {
            if (time % 12 == 0) {
                sw.playSound(null, posX, posY, posZ,
                        sounds.get(r.nextInt(sounds.size())), getSoundCategory(),
                        1.0F + r.nextFloat() / 5.0F, 1.0F + r.nextFloat() / 4.0F);
            }
            if (r.nextInt(101) < 3) {
                heal(1.0F);
                sw.spawnParticle(Particles.HEART, posX, posY + .75, posZ,
                        1, .0, .0, .0, .0);
                if (r.nextBoolean()) {
                    final EntityXPOrb orb = new EntityXPOrb(sw, posX, posY, posZ, 1 + r.nextInt(5));
                    sw.spawnEntity(orb);
                }
            }
        } else setSilent(false);
        final SPacketJoySync sync = new SPacketJoySync();
        sync.writeData(getEntityId(), joy);
        sw.getServer().getPlayerList().sendPacketToAllPlayersInDimension(sync, world.dimension.getType());
    }

    @Override
    public int getJoy() {
        return joy;
    }

    @Override
    public void setJoy(int joy) {
        this.joy = joy;
    }
}
