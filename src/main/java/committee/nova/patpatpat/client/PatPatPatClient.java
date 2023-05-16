package committee.nova.patpatpat.client;

import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.client.render.renderer.PattedCatRenderer;
import committee.nova.patpatpat.client.render.renderer.PattedWolfRenderer;
import committee.nova.patpatpat.common.util.Utilities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class PatPatPatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityType.CAT, (m, ctx) -> new PattedCatRenderer(m));
        EntityRendererRegistry.INSTANCE.register(EntityType.WOLF, (m, ctx) -> new PattedWolfRenderer(m));
        ClientPlayNetworking.registerGlobalReceiver(PatPatPat.PAT, (client, handler, buf, responseSender) -> {
            final int id = buf.readInt();
            final int joy = buf.readInt();
            client.execute(() -> {
                final World world = client.world;
                if (world == null) return;
                final Entity e = world.getEntityById(id);
                if (!Utilities.isPattable(e)) return;
                Utilities.setJoyForEntity(e, joy);
            });
        });
    }
}
