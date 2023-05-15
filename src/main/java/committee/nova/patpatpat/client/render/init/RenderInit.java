package committee.nova.patpatpat.client.render.init;

import committee.nova.patpatpat.client.render.renderer.RenderPattedOcelot;
import committee.nova.patpatpat.client.render.renderer.RenderPattedWolf;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderInit {
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelot.class, RenderPattedOcelot::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, RenderPattedWolf::new);
    }
}
