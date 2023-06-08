package committee.nova.patpatpat.common.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import committee.nova.patpatpat.common.api.IPattable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilities {
    private static final Map<Class<? extends Entity>, List<SoundEvent>> pattables = Maps.newHashMap();

    public static void addPattable(Class<? extends Entity> entityClass, List<SoundEvent> sounds) {
        pattables.put(entityClass, sounds);
    }

    public static boolean isPattable(Entity e) {
        if (e == null) return false;
        for (final Class<?> c : pattables.keySet()) if (c.isAssignableFrom(e.getClass())) return true;
        return false;
    }

    public static List<SoundEvent> getPattedSounds(Entity e) {
        final List<SoundEvent> l = new ArrayList<>();
        for (final Map.Entry<Class<? extends Entity>, List<SoundEvent>> t : pattables.entrySet())
            if (t.getKey().isAssignableFrom(e.getClass())) l.addAll(t.getValue());
        return l;
    }

    public static void setJoyForEntity(Entity e, int joy) {
        if (!(e instanceof IPattable)) return;
        ((IPattable) e).setJoy(joy);
    }

    public static int getJoyFromEntity(Entity e) {
        if (!(e instanceof IPattable)) return 0;
        return ((IPattable) e).getJoy();
    }

    static {
        pattables.put(CatEntity.class, ImmutableList.of(SoundEvents.ENTITY_CAT_PURR, SoundEvents.ENTITY_CAT_PURREOW));
        pattables.put(WolfEntity.class, ImmutableList.of(SoundEvents.ENTITY_WOLF_PANT, SoundEvents.ENTITY_WOLF_WHINE));
    }
}
