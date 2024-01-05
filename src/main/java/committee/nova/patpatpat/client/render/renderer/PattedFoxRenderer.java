package committee.nova.patpatpat.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import committee.nova.patpatpat.client.render.layer.PattedFoxHeldItemLayer;
import committee.nova.patpatpat.client.render.model.PattedFoxModel;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PattedFoxRenderer extends MobRenderer<Fox, PattedFoxModel<Fox>> {
    private static final ResourceLocation RED_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/fox.png");
    private static final ResourceLocation RED_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/fox_sleep.png");
    private static final ResourceLocation SNOW_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox.png");
    private static final ResourceLocation SNOW_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");

    public PattedFoxRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PattedFoxModel<>(ctx.bakeLayer(ModelLayers.FOX)), 0.4F);
        this.addLayer(new PattedFoxHeldItemLayer(this, ctx.getItemInHandRenderer()));
    }

    protected void setupRotations(Fox pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        if (pEntityLiving.isPouncing() || pEntityLiving.isFaceplanted()) {
            float f = -Mth.lerp(pPartialTicks, pEntityLiving.xRotO, pEntityLiving.getXRot());
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f));
        }

    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Fox pEntity) {
        if (pEntity.getVariant() == Fox.Type.RED) {
            return pEntity.isSleeping() ? RED_FOX_SLEEP_TEXTURE : RED_FOX_TEXTURE;
        } else {
            return pEntity.isSleeping() ? SNOW_FOX_SLEEP_TEXTURE : SNOW_FOX_TEXTURE;
        }
    }
}
