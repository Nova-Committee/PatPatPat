package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.common.util.Utilities;
import committee.nova.vlserverparticle.ParticleUtils;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ForgeEventHandler {
    @ForgeSubscribe
    public void onPattableUpdate(LivingEvent.LivingUpdateEvent e) {
        final EntityLivingBase l = e.entityLiving;
        final World w = l.worldObj;
        if (w.isRemote) return;
        final long time = w.getWorldInfo().getWorldTotalTime() - l.entityId;
        if (time % 5 != 0) return;
        final List<String> pattedSounds = Utilities.getPattedSounds(l);
        if (pattedSounds.isEmpty()) return;
        final int joy = Math.max(0, Utilities.getJoyFromEntity(l) - 1);
        Utilities.setJoyForEntity(l, joy);
        final Random r = l.getRNG();
        if (joy > 0) {
            if (l instanceof EntityLiving) ((EntityLiving) l).livingSoundTime = -120;
            if (time % 12 == 0) l.playSound(pattedSounds.get(r.nextInt(pattedSounds.size())),
                    1.0F + r.nextFloat() / 5.0F, 1.0F + r.nextFloat() / 4.0F);
            if (r.nextInt(101) < 3) {
                l.heal(1.0F);
                ParticleUtils.sendParticle(w, "heart", l.posX, l.posY + .75, l.posZ,
                        .0, .0, .0, .0, 1);
                if (r.nextBoolean()) {
                    final EntityXPOrb orb = new EntityXPOrb(w, l.posX, l.posY, l.posZ, 1 + r.nextInt(5));
                    w.spawnEntityInWorld(orb);
                }
            }
        }
        try {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            final DataOutputStream dos = new DataOutputStream(os);
            dos.writeInt(l.entityId);
            dos.writeInt(joy);
            PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket("patpatpat:pat", os.toByteArray()), w.provider.dimensionId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @ForgeSubscribe
    public void onPat(AttackEntityEvent event) {
        final Entity e = event.target;
        if (!event.entityPlayer.isSneaking() || !Utilities.isPattable(e)) return;
        event.setCanceled(true);
        if (e.worldObj.isRemote) return;
        if (e instanceof EntityAnimal) {
            final EntityAnimal animal = (EntityAnimal) e;
            if (animal.isInLove() || animal.isDead) return;
            if (e instanceof EntityTameable && !((EntityTameable) e).isTamed()) return;
        }
        Utilities.setJoyForEntity(e, 10);
    }
}
