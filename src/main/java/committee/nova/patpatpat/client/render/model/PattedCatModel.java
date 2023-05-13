package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.PatPatPat;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedCatModel<T extends CatEntity> extends CatModel<T> {
    public PattedCatModel(float f) {
        super(f);
    }

    @Override
    public void setupAnim(T cat, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        cat.getCapability(PatPatPat.PAT).ifPresent(p -> {
            if (p.getJoy() > 0) {
                this.head.xRot = MathHelper.sin(ageInTicks) * 0.2f;
                this.head.zRot = MathHelper.cos(ageInTicks) * 0.01f;
                this.head.yRot = MathHelper.cos(ageInTicks) * 0.01f;
                this.tail2.xRot = 2.670354F + MathHelper.sin(ageInTicks) * 0.2f;
                this.tail2.zRot = MathHelper.cos(ageInTicks) * 0.02f;
                this.tail2.yRot = MathHelper.cos(ageInTicks) * 0.02f;
            }
        });
    }
}
