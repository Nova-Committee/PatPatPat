package committee.nova.patpatpat.client.compat;

import com.buuz135.catjammies.IJammyDetector;
import committee.nova.patpatpat.PatPatPat;

import java.util.concurrent.atomic.AtomicBoolean;

public class CatJammiesCompat {
    public static void init() {
        IJammyDetector.DETECTORS.add((world, cat) -> {
            final AtomicBoolean b = new AtomicBoolean();
            cat.getCapability(PatPatPat.PAT).ifPresent(p -> b.set(p.getJoy() > 0));
            return b.get() ? 1 : 0;
        });
    }
}
