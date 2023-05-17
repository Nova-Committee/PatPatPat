package committee.nova.patpatpat.common.network.handler;

import committee.nova.patpatpat.common.network.msg.SPacketJoySync;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;

public class PacketHandler {
    public interface PacketRegistrationReceiver {
        EnumConnectionState registerPacket(EnumPacketDirection direction, Class<? extends Packet<?>> packetClass);
    }

    public static void registerPlayPacket(PacketRegistrationReceiver receiver) {
        receiver.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketJoySync.class);
    }
}
