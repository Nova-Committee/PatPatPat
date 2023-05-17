package committee.nova.patpatpat.client.render.renderer;

import committee.nova.patpatpat.client.render.layer.LayerPattedWolfCollar;
import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.model.ModelPattedWolf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RenderPattedWolf extends RenderLiving<EntityWolf> {
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation ANGRY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public RenderPattedWolf(RenderManager manager) {
        super(manager, new ModelPattedWolf(), .5F);
        this.addLayer(new LayerPattedWolfCollar(this));
    }

    @Override
    protected float handleRotationFloat(EntityWolf wolf, float partial) {
        return Utilities.getJoyFromEntity(wolf) == 0 ? wolf.getTailRotation() : wolf.ticksExisted + partial;
    }

    public void doRender(EntityWolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.isWolfWet()) {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            GlStateManager.color3f(f, f, f);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityWolf entity) {
        if (entity.isTamed()) {
            return TAMED_WOLF_TEXTURES;
        } else {
            return entity.isAngry() ? ANGRY_WOLF_TEXTURES : WOLF_TEXTURES;
        }
    }
}
