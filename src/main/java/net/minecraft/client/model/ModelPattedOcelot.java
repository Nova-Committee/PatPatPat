package net.minecraft.client.model;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelPattedOcelot extends ModelOcelot {
    @Override
    public void setRotationAngles(float var1, float var2, float ageInTicks, float var4, float var5, float var6, Entity ocelot) {
        super.setRotationAngles(var1, var2, ageInTicks, var4, var5, var6, ocelot);
        if (Utilities.getJoyFromEntity(ocelot) == 0) return;
        ocelotHead.rotateAngleX = MathHelper.sin(ageInTicks) * .2F;
        ocelotHead.rotateAngleZ = MathHelper.cos(ageInTicks) * .01F;
        ocelotHead.rotateAngleY = MathHelper.cos(ageInTicks) * .01F;
        ocelotTail2.rotateAngleX = 2.670354F + MathHelper.sin(ageInTicks) * .2F;
        ocelotTail2.rotateAngleZ = MathHelper.cos(ageInTicks) * .02F;
        ocelotTail2.rotateAngleY = MathHelper.cos(ageInTicks) * .02F;
    }
}
