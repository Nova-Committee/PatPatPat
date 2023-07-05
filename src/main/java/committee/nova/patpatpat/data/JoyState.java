package committee.nova.patpatpat.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum JoyState implements PersistentDataType<Integer, Integer> {
    INSTANCE;

    @Nonnull
    @Override
    public Class<Integer> getPrimitiveType() {
        return Integer.class;
    }

    @Nonnull
    @Override
    public Class<Integer> getComplexType() {
        return Integer.class;
    }

    @Nonnull
    @Override
    public Integer toPrimitive(Integer complex, PersistentDataAdapterContext context) {
        return complex;
    }

    @Nonnull
    @Override
    public Integer fromPrimitive(Integer primitive, PersistentDataAdapterContext context) {
        return primitive;
    }
}
