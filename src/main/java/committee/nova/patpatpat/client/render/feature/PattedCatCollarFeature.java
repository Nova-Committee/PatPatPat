package committee.nova.patpatpat.client.render.feature;

import committee.nova.patpatpat.client.render.model.PattedCatModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;

public class PattedCatCollarFeature extends FeatureRenderer<CatEntity, PattedCatModel<CatEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/cat/cat_collar.png");
    private final CatEntityModel<CatEntity> model = new PattedCatModel<>(.01F);

    public PattedCatCollarFeature(FeatureRendererContext<CatEntity, PattedCatModel<CatEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CatEntity catEntity, float f, float g, float h, float j, float k, float l) {
        if (!catEntity.isTamed()) return;
        float[] fs = catEntity.getCollarColor().getColorComponents();
        render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, catEntity, f, g, j, k, l, h, fs[0], fs[1], fs[2]);
    }
}