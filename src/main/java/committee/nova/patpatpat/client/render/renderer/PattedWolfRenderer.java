package committee.nova.patpatpat.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import committee.nova.patpatpat.client.render.layer.PattedWolfCollarLayer;
import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.atomic.AtomicBoolean;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PattedWolfRenderer extends MobRenderer<Wolf, PattedWolfModel<Wolf>> {
    private static final ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public PattedWolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PattedWolfModel<>(ctx.bakeLayer(ModelLayers.WOLF)), .5F);
        this.addLayer(new PattedWolfCollarLayer(this));
    }

    @Override
    protected float getBob(Wolf wolf, float f) {
        final AtomicBoolean b = new AtomicBoolean();
        wolf.getCapability(PatCapability.PAT).ifPresent(p -> b.set(p.getJoy() > 0));
        return b.get() ? super.getBob(wolf, f) : wolf.getTailAngle();
    }

    @Override
    public void render(Wolf wolf, float f1, float f2, PoseStack ps, MultiBufferSource buffer, int i) {
        if (wolf.isWet()) {
            float f = wolf.getWetShade(f2);
            this.model.setColor(f, f, f);
        }
        super.render(wolf, f1, f2, ps, buffer, i);
        if (wolf.isWet()) this.model.setColor(1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getTextureLocation(Wolf wolf) {
        return wolf.isTame() ? WOLF_TAME_LOCATION : wolf.isAngry() ? WOLF_ANGRY_LOCATION : WOLF_LOCATION;
    }
}
