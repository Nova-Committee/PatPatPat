package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Wolf;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class PattedWolfModel<T extends Wolf> extends WolfModel<T> {
    public PattedWolfModel(ModelPart p) {
        super(p);
    }

    @Override
    public void setupAnim(T wolf, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(wolf, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        wolf.getCapability(PatCapability.PAT).ifPresent(p -> {
            if (p.getJoy() > 0) {
                final ModelPart head = ((List<ModelPart>) headParts()).get(0);
                head.xRot = Mth.sin(ageInTicks) * .2F;
                head.zRot = Mth.cos(ageInTicks) * .01F;
                head.yRot = Mth.cos(ageInTicks) * .01F;
                final ModelPart tail = ((List<ModelPart>) bodyParts()).get(5);
                tail.xRot = wolf.getTailAngle() + Mth.sin(ageInTicks) * .2F;
                tail.zRot = Mth.cos(ageInTicks) * .5F;
                tail.yRot = Mth.cos(ageInTicks) * .5F;
            }
        });
    }
}
