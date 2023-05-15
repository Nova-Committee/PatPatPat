package net.minecraft.client.model;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.MathHelper;

public class ModelPattedWolf extends ModelWolf {
    @Override
    public void setRotationAngles(float var1, float var2, float ageInTicks, float var4, float var5, float var6, Entity e) {
        super.setRotationAngles(var1, var2, ageInTicks, var4, var5, var6, e);
        if (Utilities.getJoyFromEntity(e) == 0) return;
        wolfHeadMain.rotateAngleX = MathHelper.sin(ageInTicks) * .2F;
        wolfHeadMain.rotateAngleZ = MathHelper.cos(ageInTicks) * .01F;
        wolfHeadMain.rotateAngleY = MathHelper.cos(ageInTicks) * .01F;
        wolfTail.rotateAngleX = ((EntityWolf) e).getTailRotation() + MathHelper.sin(ageInTicks) * .2F;
        wolfTail.rotateAngleZ = MathHelper.cos(ageInTicks) * .5F;
        wolfTail.rotateAngleY = MathHelper.cos(ageInTicks) * .5F;
    }
}
