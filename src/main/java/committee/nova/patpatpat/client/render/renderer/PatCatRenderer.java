package committee.nova.patpatpat.client.render.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import committee.nova.patpatpat.client.render.layer.CatCollarLayer;
import committee.nova.patpatpat.client.render.model.PatCatModel;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PatCatRenderer extends MobRenderer<CatEntity, PatCatModel<CatEntity>> {
    public PatCatRenderer(EntityRendererManager manager) {
        super(manager, new PatCatModel<>(.0F), .4F);
        addLayer(new CatCollarLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CatEntity cat) {
        return cat.getResourceLocation();
    }

    @Override
    protected void scale(final CatEntity cat, final MatrixStack ps, final float partial) {
        super.scale(cat, ps, partial);
        ps.scale(.8F, .8F, .8F);
    }

    @Override
    protected void setupRotations(CatEntity cat, MatrixStack ps, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(cat, ps, ageInTicks, rotationYaw, partialTicks);
        final float f = cat.getLieDownAmount(partialTicks);
        if (f <= .0F) return;
        ps.translate(.4F * f, .15F * f, .1F * f);
        ps.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.rotLerp(f, .0F, 90.0F)));
        final BlockPos blockpos = cat.blockPosition();
        for (final PlayerEntity playerentity : cat.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(blockpos).inflate(2.0, 2.0, 2.0))) {
            if (playerentity.isSleeping()) {
                ps.translate(.15 * f, .0, .0);
                break;
            }
        }
    }
}
