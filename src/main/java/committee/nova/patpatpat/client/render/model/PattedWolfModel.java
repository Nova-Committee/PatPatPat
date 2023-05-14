package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.PatPatPat;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class PattedWolfModel<T extends WolfEntity> extends WolfModel<T> {
    public PattedWolfModel() {
        super();
    }

    @Override
    public void setupAnim(T wolf, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setupAnim(wolf, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        wolf.getCapability(PatPatPat.PAT).ifPresent(p -> {
            if (p.getJoy() > 0) {
                final ModelRenderer head = ((List<ModelRenderer>) headParts()).get(0);
                head.xRot = MathHelper.sin(ageInTicks) * .2F;
                head.zRot = MathHelper.cos(ageInTicks) * .01F;
                head.yRot = MathHelper.cos(ageInTicks) * .01F;
                final ModelRenderer tail = ((List<ModelRenderer>) bodyParts()).get(5);
                tail.xRot = wolf.getTailAngle() + MathHelper.sin(ageInTicks) * .2F;
                tail.zRot = MathHelper.cos(ageInTicks) * .3F;
                tail.yRot = MathHelper.cos(ageInTicks) * .3F;
            }
        });
    }
}
