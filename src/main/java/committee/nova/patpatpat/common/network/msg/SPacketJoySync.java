package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SPacketJoySync implements Packet<INetHandlerPlayClient> {
    private int id;
    private int joy;

    public SPacketJoySync() {

    }

    public void writeData(int id, int joy) {
        this.id = id;
        this.joy = joy;
    }

    @Override
    public void readPacketData(PacketBuffer buf) {
        buf.readByte();
        id = buf.readInt();
        joy = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeByte(0);
        buf.writeInt(id);
        buf.writeInt(joy);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        if (!(handler instanceof NetHandlerPlayClient)) return;
        final NetHandlerPlayClient s = (NetHandlerPlayClient) handler;
        final Minecraft mc = Minecraft.getInstance();
        PacketThreadUtil.checkThreadAndEnqueue(this, s, mc);
        if (mc.player == null) return;
        final World world = mc.player.world;
        final Entity e = world.getEntityByID(id);
        if (!Utilities.isPattable(e)) return;
        Utilities.setJoyForEntity(e, joy);
    }
}
