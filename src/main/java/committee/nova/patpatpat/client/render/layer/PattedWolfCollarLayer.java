package committee.nova.patpatpat.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedWolfCollarLayer extends RenderLayer<Wolf, PattedWolfModel<Wolf>> {
    private static final ResourceLocation COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public PattedWolfCollarLayer(RenderLayerParent<Wolf, PattedWolfModel<Wolf>> renderer) {
        super(renderer);
    }

    public void render(PoseStack ps, MultiBufferSource buffer, int packedLightIn, Wolf wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!wolf.isTame() || wolf.isInvisible()) return;
        float[] afloat = wolf.getCollarColor().getTextureDiffuseColors();
        renderColoredCutoutModel(this.getParentModel(), COLLAR, ps, buffer, packedLightIn, wolf, afloat[0], afloat[1], afloat[2]);
    }
}
