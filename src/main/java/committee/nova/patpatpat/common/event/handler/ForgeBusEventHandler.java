package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.capabilities.PatCapability;
import committee.nova.patpatpat.common.network.handler.NetworkHandler;
import committee.nova.patpatpat.common.network.msg.PatSyncMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;

@Mod.EventBusSubscriber
public class ForgeBusEventHandler {
    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof CatEntity)
            e.addCapability(new ResourceLocation(PatPatPat.MODID, PatPatPat.MODID), new PatCapability.Provider());
    }

    @SubscribeEvent
    public static void onCatTick(LivingEvent.LivingUpdateEvent e) {
        final LivingEntity c = e.getEntityLiving();
        final World w = c.level;
        final long time = w.getDayTime();
        if (time % 5 != 0) return;
        if (!(c instanceof CatEntity)) return;
        if (w.isClientSide) return;
        c.getCapability(PatPatPat.PAT).ifPresent(p -> {
            final int joy = p.getJoy() - 1;
            p.setJoy(Math.max(0, joy));
            final Random r = c.getRandom();
            if (joy > 0) {
                if (time % 12 == 0) w.playSound(null, c.getX(), c.getY(), c.getZ(),
                        r.nextBoolean() ? SoundEvents.CAT_PURR : SoundEvents.CAT_PURREOW, c.getSoundSource(),
                        1.0F + r.nextFloat() / 5.0F, 1.0F + r.nextFloat() / 4.0F);
                if (r.nextInt(101) < 10) {
                    c.heal(1.0F);
                    if (w instanceof ServerWorld)
                        ((ServerWorld) w).sendParticles(ParticleTypes.HAPPY_VILLAGER, c.getX(), c.getY() + .75, c.getZ(),
                                1, .0, .0, .0, .0);
                }
            } else c.setSilent(false);
            final PatSyncMessage msg = new PatSyncMessage(c.getId(), p.getJoy());
            NetworkHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(w::dimension), msg);
        });
    }

    @SubscribeEvent
    public static void onPat(AttackEntityEvent e) {
        final Entity l = e.getTarget();
        if (!e.getPlayer().isShiftKeyDown() || !(l instanceof CatEntity)) return;
        if (!l.level.isClientSide) {
            l.getCapability(PatPatPat.PAT).ifPresent(p -> p.setJoy(10));
            l.setSilent(true);
        }
        e.setCanceled(true);
    }
}
