package committee.nova.patpatpat.mixin.client;

import committee.nova.patpatpat.client.PatPatPatClient;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
    @Shadow
    @Final
    private Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void inject$init(TextureManager renderEngineIn, ItemRenderer itemRendererIn, CallbackInfo ci) {
        PatPatPatClient.addEntityRenderers(entityRenderMap, (RenderManager) (Object) this);
    }
}
