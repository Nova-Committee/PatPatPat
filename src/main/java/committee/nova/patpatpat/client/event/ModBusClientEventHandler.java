package committee.nova.patpatpat.client.event;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.client.render.renderer.PatCatRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEventHandler {
    @SubscribeEvent
    public static void registerRenderer(final FMLClientSetupEvent event) {
        if (PatPatPat.jammiesDetected()) return;
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CAT, PatCatRenderer::new);
    }
}
