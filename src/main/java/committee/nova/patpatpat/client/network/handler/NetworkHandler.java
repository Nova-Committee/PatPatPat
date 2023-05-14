package committee.nova.patpatpat.client.network.handler;

import committee.nova.patpatpat.common.util.Utilities;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class NetworkHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p) {
        final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
        if (!packet.channel.equals("patpatpat:pat")) return;
        try {
            final World world = ((AbstractClientPlayer) p).worldObj;
            final int entityId = dis.readInt();
            final int joy = dis.readInt();
            final Entity e = world.getEntityByID(entityId);
            if (e == null) return;
            Utilities.setJoyForEntity(e, joy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
