package committee.nova.patpatpat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import committee.nova.patpatpat.data.JoyState;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public final class Patpatpat extends JavaPlugin implements Listener {
    private static final Map<EntityType, List<Sound>> pattedSound = Maps.newHashMap();
    private final NamespacedKey dataKey = new NamespacedKey(this, "pat");
    private final String channel = "patpatpat:pat";

    static {
        pattedSound.put(EntityType.CAT, ImmutableList.of(Sound.ENTITY_CAT_PURR, Sound.ENTITY_CAT_PURREOW));
        pattedSound.put(EntityType.WOLF, ImmutableList.of(Sound.ENTITY_WOLF_WHINE, Sound.ENTITY_WOLF_PANT));
    }

    @Override
    public void onEnable() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> tickPattable(getServer()), 0, 5);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, channel);
        getLogger().info("PatPatPat initialized!");
    }

    @EventHandler
    public void onLivingHurt(EntityDamageByEntityEvent event) {
        final Entity damage = event.getDamager();
        if (!(damage instanceof Player)) return;
        final Player player = (Player) damage;
        if (!player.isSneaking()) return;
        final Entity patted = event.getEntity();
        if (!isPattable(patted)) return;
        event.setCancelled(true);
        if (patted.isDead()) return;
        if (patted instanceof Animals) {
            final Animals a = (Animals) patted;
            if (a.isLoveMode()) return;
        }
        patted.getPersistentDataContainer().set(dataKey, JoyState.INSTANCE, 10);
        patted.setSilent(true);
    }

    public void tickPattable(Server server) {
        final List<World> worlds = server.getWorlds();
        for (final World world : worlds) {
            final Collection<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class);
            for (final LivingEntity p : entities) {
                final long time = world.getGameTime() - p.getEntityId();
                if (!isPattable(p)) continue;
                final Integer rawJoy = p.getPersistentDataContainer().get(dataKey, JoyState.INSTANCE);
                final int oJoy = rawJoy == null ? 0 : rawJoy;
                final int joy = Math.max(0, oJoy - 1);
                p.getPersistentDataContainer().set(dataKey, JoyState.INSTANCE, joy);
                final Random r = new Random();
                if (joy > 0) {
                    if (time % 12 == 0) {
                        final List<Sound> sounds = getPattedSounds(p);
                        if (!sounds.isEmpty())
                            world.playSound(p.getEyeLocation(), sounds.get(r.nextInt(sounds.size())), 1.0F, 1.0F);
                    }
                    if (r.nextInt(101) < 3) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0));
                        world.spawnParticle(Particle.HEART, p.getEyeLocation(), 1);
                        final Entity re = world.spawnEntity(p.getEyeLocation(), EntityType.EXPERIENCE_ORB);
                        if (re instanceof ExperienceOrb) ((ExperienceOrb) re).setExperience(1);
                    }
                } else p.setSilent(false);
                final Integer nRawJoy = p.getPersistentDataContainer().get(dataKey, JoyState.INSTANCE);
                sendPatMsg(world, p.getEntityId(), nRawJoy == null ? 0 : nRawJoy);
            }
        }
    }

    private static List<Sound> getPattedSounds(Entity e) {
        final List<Sound> l = new ArrayList<>();
        for (final Map.Entry<EntityType, List<Sound>> t : pattedSound.entrySet())
            if (e.getType().equals(t.getKey())) l.addAll(t.getValue());
        return l;
    }

    private static boolean isPattable(Entity e) {
        if (e == null) return false;
        return pattedSound.containsKey(e.getType());
    }

    private void sendPatMsg(World world, int eId, int joy) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(os);) {
            dos.writeByte(0);
            dos.writeInt(eId);
            dos.writeInt(joy);
            world.sendPluginMessage(this, channel, os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
