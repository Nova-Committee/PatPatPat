package committee.nova.patpatpat.client.event;

import committee.nova.patpatpat.client.render.renderer.PattedCatRenderer;
import committee.nova.patpatpat.client.render.renderer.PattedFoxRenderer;
import committee.nova.patpatpat.client.render.renderer.PattedWolfRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClientEventHandler {
    @SubscribeEvent
    public static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.WOLF, PattedWolfRenderer::new);
        event.registerEntityRenderer(EntityType.CAT, PattedCatRenderer::new);
        event.registerEntityRenderer(EntityType.FOX, PattedFoxRenderer::new);
    }
}
