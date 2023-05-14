package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.common.network.handler.NetworkHandler;
import committee.nova.patpatpat.common.network.msg.PatMessage;
import committee.nova.patpatpat.common.util.Utilities;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.List;
import java.util.Random;

public class ForgeEventHandler {
    @SubscribeEvent
    public void onPattableUpdate(LivingEvent.LivingUpdateEvent e) {
        final EntityLivingBase l = e.entityLiving;
        final World w = l.worldObj;
        if (w.isRemote) return;
        final long time = w.getWorldInfo().getWorldTotalTime();
        if ((time - l.getEntityId()) % 5 != 0) return;
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
                if (w instanceof WorldServer) ((WorldServer) w).func_147487_a("heart", l.posX, l.posY + .75, l.posZ,
                        1, .0, .0, .0, .0);
                if (r.nextBoolean()) {
                    final EntityXPOrb orb = new EntityXPOrb(w, l.posX, l.posY, l.posZ, 1 + r.nextInt(5));
                    w.spawnEntityInWorld(orb);
                }
            }
        }
        final PatMessage msg = new PatMessage();
        msg.writeData(l.getEntityId(), Utilities.getJoyFromEntity(l));
        NetworkHandler.instance.sendToDimension(msg, w.provider.dimensionId);
    }

    @SubscribeEvent
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
