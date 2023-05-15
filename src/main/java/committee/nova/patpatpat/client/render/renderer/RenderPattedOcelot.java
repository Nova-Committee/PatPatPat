package committee.nova.patpatpat.client.render.renderer;

import net.minecraft.client.model.ModelPattedOcelot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RenderPattedOcelot extends RenderLiving<EntityOcelot> {
    private static final ResourceLocation BLACK_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/black.png");
    private static final ResourceLocation OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/ocelot.png");
    private static final ResourceLocation RED_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/red.png");
    private static final ResourceLocation SIAMESE_OCELOT_TEXTURES = new ResourceLocation("textures/entity/cat/siamese.png");

    public RenderPattedOcelot(RenderManager manager) {
        super(manager, new ModelPattedOcelot(), .4F);
    }

    protected ResourceLocation getEntityTexture(EntityOcelot entity) {
        switch (entity.getTameSkin()) {
            case 0:
            default:
                return OCELOT_TEXTURES;
            case 1:
                return BLACK_OCELOT_TEXTURES;
            case 2:
                return RED_OCELOT_TEXTURES;
            case 3:
                return SIAMESE_OCELOT_TEXTURES;
        }
    }

    protected void preRenderCallback(EntityOcelot ocelot, float partialTickTime) {
        super.preRenderCallback(ocelot, partialTickTime);

        if (ocelot.isTamed()) {
            GlStateManager.scale(0.8F, 0.8F, 0.8F);
        }
    }
}
