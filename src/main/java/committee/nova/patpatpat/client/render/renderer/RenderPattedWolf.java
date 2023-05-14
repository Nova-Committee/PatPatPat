package committee.nova.patpatpat.client.render.renderer;

import committee.nova.patpatpat.common.util.Utilities;
import net.minecraft.client.model.ModelPattedWolf;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;

public class RenderPattedWolf extends RenderWolf {
    public RenderPattedWolf() {
        super(new ModelPattedWolf(), new ModelPattedWolf(), .5F);
    }

    @Override
    protected float getTailRotation(EntityWolf wolf, float partial) {
        return Utilities.getJoyFromEntity(wolf) == 0 ? super.getTailRotation(wolf, partial) : wolf.ticksExisted + partial;
    }
}
