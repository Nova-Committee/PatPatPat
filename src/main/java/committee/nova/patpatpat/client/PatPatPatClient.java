package committee.nova.patpatpat.client;

import committee.nova.patpatpat.client.render.renderer.RenderPattedOcelot;
import committee.nova.patpatpat.client.render.renderer.RenderPattedWolf;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

import java.util.Map;

public class PatPatPatClient {
    public static void addEntityRenderers(Map<Class<? extends Entity>, Render<? extends Entity>> map, RenderManager manager) {
        map.put(EntityWolf.class, new RenderPattedWolf(manager));
        map.put(EntityOcelot.class, new RenderPattedOcelot(manager));
    }
}
