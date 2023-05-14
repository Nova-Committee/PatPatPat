package committee.nova.patpatpat.common.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtilities {
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
        pattables.put(CatEntity.class, ImmutableList.of(SoundEvents.CAT_PURR, SoundEvents.CAT_PURREOW));
        pattables.put(WolfEntity.class, ImmutableList.of(SoundEvents.WOLF_PANT, SoundEvents.WOLF_WHINE));
    }
}
