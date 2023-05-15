package committee.nova.patpatpat;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("patpatpat")
public class PatPatPat {
    public static final String MODID = "patpatpat";
    private static final Logger LOGGER = LogManager.getLogger();

    public PatPatPat() {

    }

    public static boolean jammiesDetected() {
        return ModList.get().isLoaded("catjammies");
    }
}
