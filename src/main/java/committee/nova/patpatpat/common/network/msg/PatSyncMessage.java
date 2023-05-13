package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.util.CommonUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PatSyncMessage {
    private final int id;
    private final int joy;

    public PatSyncMessage(PacketBuffer buffer) {
        id = buffer.readInt();
        joy = buffer.readInt();
    }

    public PatSyncMessage(int id, int joy) {
        this.id = id;
        this.joy = joy;
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(id);
        buffer.writeInt(joy);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final World w = Minecraft.getInstance().level;
            if (w == null) return;
            final Entity e = w.getEntity(id);
            if (!(CommonUtilities.isPattable(e))) return;
            e.getCapability(PatPatPat.PAT).ifPresent(p -> p.setJoy(joy));
        });
        ctx.get().setPacketHandled(true);
    }
}
