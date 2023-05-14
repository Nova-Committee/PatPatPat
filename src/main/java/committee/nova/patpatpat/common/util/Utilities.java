package committee.nova.patpatpat.common.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilities {
    private static final Map<Class<? extends Entity>, List<String>> pattables = Maps.newHashMap();

    public static void addPattable(Class<? extends Entity> entityClass, List<String> sounds) {
        pattables.put(entityClass, sounds);
    }

    public static boolean isPattable(Entity e) {
        if (e == null) return false;
        for (final Class<?> c : pattables.keySet()) if (c.isAssignableFrom(e.getClass())) return true;
        return false;
    }

    public static List<String> getPattedSounds(Entity e) {
        final List<String> l = new ArrayList<String>();
        for (final Map.Entry<Class<? extends Entity>, List<String>> t : pattables.entrySet())
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
        pattables.put(EntityOcelot.class, ImmutableList.of(
                "mob.cat.purr", "mob.cat.purreow"
        ));
        pattables.put(EntityWolf.class, ImmutableList.of(
                "mob.wolf.whine", "mob.wolf.panting"
        ));
    }
}
