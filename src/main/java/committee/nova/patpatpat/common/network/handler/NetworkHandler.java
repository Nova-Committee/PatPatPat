package committee.nova.patpatpat.common.network.handler;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.common.network.msg.PatSyncMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int id = 0;

    public static int nextId() {
        return id++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(PatPatPat.MODID, "pat"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.messageBuilder(PatSyncMessage.class, nextId())
                .encoder(PatSyncMessage::toBytes)
                .decoder(PatSyncMessage::new)
                .consumer(PatSyncMessage::handler)
                .add();
    }
}
