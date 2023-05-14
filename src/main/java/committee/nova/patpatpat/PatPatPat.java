package committee.nova.patpatpat;

import committee.nova.patpatpat.client.network.handler.NetworkHandler;
import committee.nova.patpatpat.client.render.init.RenderInit;
import committee.nova.patpatpat.common.event.handler.ForgeEventHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = PatPatPat.MODID, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
        clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"patpatpat:pat"}, packetHandler = NetworkHandler.class))
public class PatPatPat {
    public static final String MODID = "patpatpat";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        if (e.getSide() == Side.CLIENT) RenderInit.init();
    }
}
