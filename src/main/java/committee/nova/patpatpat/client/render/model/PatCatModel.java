package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.PatPatPat;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PatCatModel<T extends CatEntity> extends CatModel<T> {
    public PatCatModel(float f) {
        super(f);
    }

    @Override
    public void setupAnim(final T cat, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch) {
        super.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        cat.getCapability(PatPatPat.PAT).ifPresent(p -> {
            if (p.getJoy() > 0) {
                this.head.xRot = MathHelper.sin(ageInTicks) * 0.2f;
                this.head.zRot = MathHelper.cos(ageInTicks) * 0.01f;
                this.head.yRot = MathHelper.cos(ageInTicks) * 0.01f;
            }
        });
    }
}
