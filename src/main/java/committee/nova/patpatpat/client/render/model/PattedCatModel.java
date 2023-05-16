package committee.nova.patpatpat.client.render.model;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.MathHelper;

public class PattedCatModel<T extends CatEntity> extends CatEntityModel<T> {
    public PattedCatModel(ModelPart f) {
        super(f);
    }

    @Override
    public void setAngles(T cat, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.setAngles(cat, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
        if (Utilities.getJoyFromEntity(cat) == 0) return;
        this.head.pitch = MathHelper.sin(ageInTicks) * .2F;
        this.head.roll = MathHelper.cos(ageInTicks) * .01F;
        this.head.yaw = MathHelper.cos(ageInTicks) * .01F;
        this.lowerTail.pitch = 2.670354F + MathHelper.sin(ageInTicks) * .2F;
        this.lowerTail.roll = MathHelper.cos(ageInTicks) * .02F;
        this.lowerTail.yaw = MathHelper.cos(ageInTicks) * .02F;
    }
}
