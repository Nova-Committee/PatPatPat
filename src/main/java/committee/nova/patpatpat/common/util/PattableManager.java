package committee.nova.patpatpat.common.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PattableManager {
    private static final Map<Class<? extends Entity>, List<SoundEvent>> pattables = new HashMap<>();

    public static void addPattable(Class<? extends Entity> entityClass, List<SoundEvent> soundEvents) {
        pattables.put(entityClass, soundEvents);
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

    static {
        pattables.put(Cat.class, ImmutableList.of(SoundEvents.CAT_PURR, SoundEvents.CAT_PURREOW));
        pattables.put(Wolf.class, ImmutableList.of(SoundEvents.WOLF_PANT, SoundEvents.WOLF_WHINE));
        pattables.put(Fox.class, ImmutableList.of(SoundEvents.FOX_SLEEP, SoundEvents.FOX_SNIFF));
    }
}
