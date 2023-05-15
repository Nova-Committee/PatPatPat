package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import committee.nova.patpatpat.common.util.CommonUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PatSyncMessage {
    private final int id;
    private final int joy;

    public PatSyncMessage(FriendlyByteBuf buffer) {
        id = buffer.readInt();
        joy = buffer.readInt();
    }

    public PatSyncMessage(int id, int joy) {
        this.id = id;
        this.joy = joy;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeInt(joy);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        final Level w = Minecraft.getInstance().level;
        if (w == null) return;
        final Entity e = w.getEntity(id);
        if (!(CommonUtilities.isPattable(e))) return;
        e.getCapability(PatCapability.PAT).ifPresent(p -> p.setJoy(joy));
        ctx.get().setPacketHandled(true);
    }
}
