package committee.nova.patpatpat.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import committee.nova.patpatpat.client.render.model.PattedWolfModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedWolfCollarLayer extends LayerRenderer<WolfEntity, PattedWolfModel<WolfEntity>> {
    private static final ResourceLocation COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public PattedWolfCollarLayer(IEntityRenderer<WolfEntity, PattedWolfModel<WolfEntity>> renderer) {
        super(renderer);
    }

    public void render(MatrixStack ps, IRenderTypeBuffer buffer, int packedLightIn, WolfEntity wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (wolf.isTame() && !wolf.isInvisible()) {
            float[] afloat = wolf.getCollarColor().getTextureDiffuseColors();
            renderColoredCutoutModel(this.getParentModel(), COLLAR, ps, buffer, packedLightIn, wolf, afloat[0], afloat[1], afloat[2]);
        }
    }
}
