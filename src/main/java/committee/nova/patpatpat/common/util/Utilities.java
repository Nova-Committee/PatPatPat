package committee.nova.patpatpat.common.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;

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
        final NBTTagCompound tag = e.getEntityData();
        if (!tag.hasKey("pat")) tag.setTag("pat", new NBTTagCompound());
        tag.getCompoundTag("pat").setInteger("joy", joy);
    }

    public static int getJoyFromEntity(Entity e) {
        final NBTTagCompound tag = e.getEntityData();
        if (!tag.hasKey("pat")) {
            final NBTTagCompound pat = new NBTTagCompound();
            pat.setInteger("joy", 0);
            tag.setTag("pat", pat);
            return 0;
        }
        return tag.getCompoundTag("pat").getInteger("joy");
    }

    static {
        pattables.put(EntityOcelot.class, ImmutableList.of(SoundEvents.ENTITY_CAT_PURR, SoundEvents.ENTITY_CAT_PURREOW));
        pattables.put(EntityWolf.class, ImmutableList.of(SoundEvents.ENTITY_WOLF_PANT, SoundEvents.ENTITY_WOLF_WHINE));
    }
}
