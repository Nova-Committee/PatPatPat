package committee.nova.patpatpat.client.render.renderer;

import committee.nova.patpatpat.client.render.feature.PattedWolfCollarFeature;
import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class PattedWolfRenderer extends MobEntityRenderer<WolfEntity, PattedWolfModel<WolfEntity>> {
    private static final Identifier WILD_TEXTURE = new Identifier("textures/entity/wolf/wolf.png");
    private static final Identifier TAMED_TEXTURE = new Identifier("textures/entity/wolf/wolf_tame.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/wolf/wolf_angry.png");

    public PattedWolfRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PattedWolfModel<>(ctx.getPart(EntityModelLayers.WOLF)), 0.5F);
        this.addFeature(new PattedWolfCollarFeature(this));
    }

    protected float getAnimationProgress(WolfEntity wolfEntity, float f) {
        return Utilities.getJoyFromEntity(wolfEntity) == 0 ? wolfEntity.getTailAngle() : wolfEntity.age + f;
    }

    public void render(WolfEntity wolfEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (wolfEntity.isFurWet()) {
            float h = wolfEntity.getFurWetBrightnessMultiplier(g);
            this.model.setColorMultiplier(h, h, h);
        }

        super.render(wolfEntity, f, g, matrixStack, vertexConsumerProvider, i);
        if (wolfEntity.isFurWet()) {
            this.model.setColorMultiplier(1.0F, 1.0F, 1.0F);
        }

    }

    public Identifier getTexture(WolfEntity wolfEntity) {
        if (wolfEntity.isTamed()) {
            return TAMED_TEXTURE;
        } else {
            return wolfEntity.hasAngerTime() ? ANGRY_TEXTURE : WILD_TEXTURE;
        }
    }
}
