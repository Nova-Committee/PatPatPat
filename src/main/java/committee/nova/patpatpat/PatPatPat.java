package committee.nova.patpatpat;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("patpatpat")
public class PatPatPat {
    public static final String MODID = "patpatpat";

    public PatPatPat() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> "", (a, b) -> true));
    }
}
