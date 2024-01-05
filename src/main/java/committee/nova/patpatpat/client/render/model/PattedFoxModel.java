package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class PattedFoxModel<T extends Fox> extends FoxModel<T> {
    public PattedFoxModel(ModelPart p) {
        super(p);
    }

    @Override
    public void setupAnim(T fox, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(fox, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        fox.getCapability(PatCapability.PAT).ifPresent(p -> {
            if (p.getJoy() <= 0) return;
            final ModelPart head = ((List<ModelPart>) headParts()).get(0);
            head.xRot = Mth.sin(ageInTicks) * .2F;
            head.zRot = Mth.cos(ageInTicks) * .01F;
            head.yRot = Mth.cos(ageInTicks) * .01F;
            final ModelPart tail = (((List<ModelPart>) bodyParts()).get(0).getChild("tail"));
            final float tailXRot0;
            if (fox.isSleeping()) tailXRot0 = young ? -2.1816616F : -2.6179938F;
            else if (fox.isSitting()) tailXRot0 = ((float) Math.PI / 4.0F);
            else tailXRot0 = -0.05235988F;
            tail.xRot = tailXRot0 + Mth.sin(ageInTicks) * .2F;
        });
    }
}
