package committee.nova.patpatpat.client.render.renderer;

import committee.nova.patpatpat.client.render.feature.PattedCatCollarFeature;
import committee.nova.patpatpat.client.render.model.PattedCatModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.List;

public class PattedCatRenderer extends MobEntityRenderer<CatEntity, PattedCatModel<CatEntity>> {
    public PattedCatRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new PattedCatModel<>(0.0F), 0.4F);
        this.addFeature(new PattedCatCollarFeature(this));
    }

    public Identifier getTexture(CatEntity catEntity) {
        return catEntity.getTexture();
    }

    protected void scale(CatEntity catEntity, MatrixStack matrixStack, float f) {
        super.scale(catEntity, matrixStack, f);
        matrixStack.scale(0.8F, 0.8F, 0.8F);
    }

    protected void setupTransforms(CatEntity catEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(catEntity, matrixStack, f, g, h);
        float i = catEntity.getSleepAnimation(h);
        if (i <= .0F) return;
        matrixStack.translate((double) (0.4F * i), (double) (0.15F * i), (double) (0.1F * i));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerpAngleDegrees(i, 0.0F, 90.0F)));
        final BlockPos blockPos = catEntity.getBlockPos();
        final List<PlayerEntity> list = catEntity.world.getNonSpectatingEntities(PlayerEntity.class, (new Box(blockPos)).expand(2.0D, 2.0D, 2.0D));
        for (PlayerEntity p : list)
            if (p.isSleeping()) {
                matrixStack.translate((double) (0.15F * i), 0.0D, 0.0D);
                break;
            }
    }
}
