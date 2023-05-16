package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class PattedWolfModel<T extends WolfEntity> extends WolfEntityModel<T> {
    @Override
    public void setAngles(T wolf, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setAngles(wolf, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        if (Utilities.getJoyFromEntity(wolf) == 0) return;
        final ModelPart head = ((List<ModelPart>) getHeadParts()).get(0);
        head.pitch = MathHelper.sin(ageInTicks) * .2F;
        head.roll = MathHelper.cos(ageInTicks) * .01F;
        head.yaw = MathHelper.cos(ageInTicks) * .01F;
        final ModelPart tail = ((List<ModelPart>) getBodyParts()).get(5);
        tail.pitch = wolf.getTailAngle() + MathHelper.sin(ageInTicks) * .2F;
        tail.roll = MathHelper.cos(ageInTicks) * .5F;
        tail.yaw = MathHelper.cos(ageInTicks) * .5F;
    }
}
