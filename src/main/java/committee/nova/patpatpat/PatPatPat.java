package committee.nova.patpatpat;

import committee.nova.patpatpat.common.capabilities.PatCapability;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
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

        MinecraftForge.EVENT_BUS.register(this);
    }
}
