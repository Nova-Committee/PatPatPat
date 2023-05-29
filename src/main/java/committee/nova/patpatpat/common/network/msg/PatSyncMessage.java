package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.client.network.ClientPacketUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientPacketUtils.handleSync(id, joy)));
        ctx.get().setPacketHandled(true);
    }
}
