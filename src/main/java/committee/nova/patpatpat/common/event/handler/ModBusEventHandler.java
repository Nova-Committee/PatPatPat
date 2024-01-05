package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.common.network.handler.NetworkHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler {
    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent e) {
        e.enqueueWork(NetworkHandler::registerMessage);
    }
}
