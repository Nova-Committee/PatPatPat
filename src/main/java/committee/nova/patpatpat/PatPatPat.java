package committee.nova.patpatpat;

import committee.nova.patpatpat.client.compat.CatJammiesCompat;
import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patpatpat")
public class PatPatPat {
    public static final String MODID = "patpatpat";
    private static final Logger LOGGER = LogManager.getLogger();

    @CapabilityInject(PatCapability.IPat.class)
    public static Capability<PatCapability.IPat> PAT;

    public PatPatPat() {
        if (jammiesDetected()) DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CatJammiesCompat::init);
    }

    public static boolean jammiesDetected() {
        return ModList.get().isLoaded("catjammies");
    }
}
