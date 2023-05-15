package committee.nova.patpatpat.client.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import committee.nova.patpatpat.client.render.layer.PattedCatCollarLayer;
import committee.nova.patpatpat.client.render.model.PattedCatModel;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PattedCatRenderer extends MobRenderer<Cat, PattedCatModel<Cat>> {
    public PattedCatRenderer(EntityRendererProvider.Context manager) {
        super(manager, new PattedCatModel<>(manager.bakeLayer(ModelLayers.CAT_COLLAR)), .4F);
        addLayer(new PattedCatCollarLayer(this, manager.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Cat cat) {
        return cat.getResourceLocation();
    }

    @Override
    protected void scale(final Cat cat, final PoseStack ps, final float partial) {
        super.scale(cat, ps, partial);
        ps.scale(.8F, .8F, .8F);
    }

    @Override
    protected void setupRotations(Cat cat, PoseStack ps, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(cat, ps, ageInTicks, rotationYaw, partialTicks);
        final float f = cat.getLieDownAmount(partialTicks);
        if (f <= .0F) return;
        ps.translate(.4F * f, .15F * f, .1F * f);
        ps.mulPose(Vector3f.ZP.rotationDegrees(Mth.rotLerp(f, .0F, 90.0F)));
        final BlockPos blockpos = cat.blockPosition();
        for (final Player player : cat.level.getEntitiesOfClass(Player.class, new AABB(blockpos).inflate(2.0, 2.0, 2.0))) {
            if (player.isSleeping()) {
                ps.translate(.15 * f, .0, .0);
                break;
            }
        }
    }
}
