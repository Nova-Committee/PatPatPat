package committee.nova.patpatpat.client.render.init;

import committee.nova.patpatpat.client.render.renderer.RenderPattedWolf;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelPattedOcelot;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

public class RenderInit {
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelot.class, new RenderOcelot(new ModelPattedOcelot(), .4F));
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, new RenderPattedWolf());
    }
}
