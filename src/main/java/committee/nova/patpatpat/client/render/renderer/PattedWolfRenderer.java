package committee.nova.patpatpat.client.render.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import committee.nova.patpatpat.PatPatPat;
import committee.nova.patpatpat.client.render.layer.PattedWolfCollarLayer;
import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.atomic.AtomicBoolean;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PattedWolfRenderer extends MobRenderer<WolfEntity, PattedWolfModel<WolfEntity>> {
    private static final ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public PattedWolfRenderer(EntityRendererManager manager) {
        super(manager, new PattedWolfModel<>(), .5F);
        this.addLayer(new PattedWolfCollarLayer(this));
    }

    protected float getBob(WolfEntity wolf, float f) {
        final AtomicBoolean b = new AtomicBoolean();
        wolf.getCapability(PatPatPat.PAT).ifPresent(p -> b.set(p.getJoy() > 0));
        return b.get() ? super.getBob(wolf, f) : wolf.getTailAngle();
    }

    public void render(WolfEntity wolf, float f1, float f2, MatrixStack ps, IRenderTypeBuffer buffer, int i) {
        if (wolf.isWet()) {
            float f = wolf.getWetShade(f2);
            this.model.setColor(f, f, f);
        }
        super.render(wolf, f1, f2, ps, buffer, i);
        if (wolf.isWet()) this.model.setColor(1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getTextureLocation(WolfEntity wolf) {
        return wolf.isTame() ? WOLF_TAME_LOCATION : wolf.isAngry() ? WOLF_ANGRY_LOCATION : WOLF_LOCATION;
    }
}
