package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PattedCatModel<T extends Cat> extends CatModel<T> {
    public PattedCatModel(ModelPart p) {
        super(p);
    }

    @Override
    public void setupAnim(T cat, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        cat.getCapability(PatCapability.PAT).ifPresent(p -> {
            if (p.getJoy() > 0) {
                this.head.xRot = Mth.sin(ageInTicks) * .2F;
                this.head.zRot = Mth.cos(ageInTicks) * .01F;
                this.head.yRot = Mth.cos(ageInTicks) * .01F;
                this.tail2.xRot = 2.670354F + Mth.sin(ageInTicks) * .2F;
                this.tail2.zRot = Mth.cos(ageInTicks) * .02F;
                this.tail2.yRot = Mth.cos(ageInTicks) * .02F;
            }
        });
    }
}
