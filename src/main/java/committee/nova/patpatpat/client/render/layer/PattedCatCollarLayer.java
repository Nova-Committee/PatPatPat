package committee.nova.patpatpat.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import committee.nova.patpatpat.client.render.model.PattedCatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedCatCollarLayer extends RenderLayer<Cat, PattedCatModel<Cat>> {
    private static final ResourceLocation COLLAR = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final PattedCatModel<Cat> model;

    public PattedCatCollarLayer(RenderLayerParent<Cat, PattedCatModel<Cat>> renderer, EntityModelSet ctx) {
        super(renderer);
        this.model = new PattedCatModel<>(ctx.bakeLayer(ModelLayers.CAT_COLLAR));
    }

    @Override
    public void render(PoseStack ps, MultiBufferSource buffer, int packedLightIn, Cat cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!cat.isTame()) return;
        final float[] f = cat.getCollarColor().getTextureDiffuseColors();
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, COLLAR, ps, buffer, packedLightIn, cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f[0], f[1], f[2]);
    }
}
