package committee.nova.patpatpat.client.network;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.util.CommonUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ClientPacketUtils {
    public static Runnable handleSync(int id, int joy) {
        return () -> {
            final World w = Minecraft.getInstance().level;
            if (w == null) return;
            final Entity e = w.getEntity(id);
            if (!(CommonUtilities.isPattable(e))) return;
            e.getCapability(PatPatPat.PAT).ifPresent(p -> p.setJoy(joy));
        };
    }
}
