package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.capabilities.PatCapability;
import committee.nova.patpatpat.common.network.handler.NetworkHandler;
import committee.nova.patpatpat.common.network.msg.PatSyncMessage;
import committee.nova.patpatpat.common.util.CommonUtilities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class ForgeBusEventHandler {
    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> e) {
        if (CommonUtilities.isPattable(e.getObject()))
            e.addCapability(new ResourceLocation(PatPatPat.MODID, PatPatPat.MODID), new PatCapability.Provider());
    }

    @SubscribeEvent
    public static void onPattableTick(LivingEvent.LivingUpdateEvent e) {
        final LivingEntity c = e.getEntityLiving();
        final Level w = c.level;
        if (w.isClientSide) return;
        final long time = w.getGameTime() - c.getId();
        if (time % 5 != 0) return;
        final List<SoundEvent> pattedSounds = CommonUtilities.getPattedSounds(c);
        if (pattedSounds.isEmpty()) return;
        c.getCapability(PatCapability.PAT).ifPresent(p -> {
            final int joy = Math.max(0, p.getJoy() - 1);
            p.setJoy(joy);
            final Random r = c.getRandom();
            if (joy > 0) {
                if (time % 12 == 0) {
                    w.playSound(null, c.getX(), c.getY(), c.getZ(),
                            pattedSounds.get(r.nextInt(pattedSounds.size())), c.getSoundSource(),
                            1.0F + r.nextFloat() / 5.0F, 1.0F + r.nextFloat() / 4.0F);
                }
                if (r.nextInt(101) < 3) {
                    c.heal(1.0F);
                    if (w instanceof final ServerLevel sw) {
                        sw.sendParticles(ParticleTypes.HEART, c.getX(), c.getY() + .75, c.getZ(),
                                1, .0, .0, .0, .0);
                        if (r.nextBoolean()) {
                            final ExperienceOrb orb = new ExperienceOrb(sw, c.getX(), c.getY(), c.getZ(), 1 + r.nextInt(5));
                            sw.addFreshEntity(orb);
                        }
                    }
                }
            } else c.setSilent(false);
            final PatSyncMessage msg = new PatSyncMessage(c.getId(), p.getJoy());
            NetworkHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(w::dimension), msg);
        });
    }

    @SubscribeEvent
    public static void onPat(AttackEntityEvent e) {
        final Entity l = e.getTarget();
        if (!e.getPlayer().isShiftKeyDown() || !(CommonUtilities.isPattable(l))) return;
        e.setCanceled(true);
        if (l.level.isClientSide) return;
        if (l instanceof final Animal animal) {
            if (animal.isInLove() || animal.isDeadOrDying()) return;
            if (l instanceof TamableAnimal && !((TamableAnimal) l).isTame()) return;
        }
        l.getCapability(PatCapability.PAT).ifPresent(p -> p.setJoy(10));
        l.setSilent(true);
    }
}
