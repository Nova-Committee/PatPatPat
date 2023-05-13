package committee.nova.patpatpat.common.event.handler;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import committee.nova.patpatpat.common.network.handler.NetworkHandler;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler {
    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent e) {
        e.enqueueWork(() -> CapabilityManager.INSTANCE.register(PatCapability.IPat.class, new Capability.IStorage<PatCapability.IPat>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<PatCapability.IPat> capability, PatCapability.IPat instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<PatCapability.IPat> capability, PatCapability.IPat instance, Direction side, INBT nbt) {

            }
        }, () -> null));
        e.enqueueWork(NetworkHandler::registerMessage);
    }
}
