package committee.nova.patpatpat.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import committee.nova.patpatpat.client.render.model.PattedCatModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedCatCollarLayer extends LayerRenderer<CatEntity, PattedCatModel<CatEntity>> {
    private static final ResourceLocation COLLAR = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final CatModel<CatEntity> model = new CatModel<>(0.01f);
    ;

    public PattedCatCollarLayer(IEntityRenderer<CatEntity, PattedCatModel<CatEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack ps, IRenderTypeBuffer buffer, int packedLightIn, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!cat.isTame()) return;
        final float[] f = cat.getCollarColor().getTextureDiffuseColors();
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, COLLAR, ps, buffer, packedLightIn, cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f[0], f[1], f[2]);
    }
}
