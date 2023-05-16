package committee.nova.patpatpat.client.render.feature;

import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class PattedWolfCollarFeature extends FeatureRenderer<WolfEntity, PattedWolfModel<WolfEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/wolf/wolf_collar.png");

    public PattedWolfCollarFeature(FeatureRendererContext<WolfEntity, PattedWolfModel<WolfEntity>> ctx) {
        super(ctx);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, WolfEntity wolfEntity, float f, float g, float h, float j, float k, float l) {
        if (wolfEntity.isTamed() && !wolfEntity.isInvisible()) {
            float[] fs = wolfEntity.getCollarColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN, matrixStack, vertexConsumerProvider, i, wolfEntity, fs[0], fs[1], fs[2]);
        }
    }
}
