package committee.nova.patpatpat.client.network;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import committee.nova.patpatpat.common.util.CommonUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientPacketUtils {
    public static Runnable handleSync(int id, int joy) {
        return () -> {
            final Level w = Minecraft.getInstance().level;
            if (w == null) return;
            final Entity e = w.getEntity(id);
            if (!(CommonUtilities.isPattable(e))) return;
            e.getCapability(PatCapability.PAT).ifPresent(p -> p.setJoy(joy));
        };
    }
}
