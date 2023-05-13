package committee.nova.patpatpat.common.capabilities;

import committee.nova.patpatpat.PatPatPat;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PatCapability {
    public interface IPat extends INBTSerializable<CompoundNBT> {
        int getJoy();

        void setJoy(int joy);
    }

    public static class PatImpl implements IPat {
        private int joy;

        @Override
        public int getJoy() {
            return joy;
        }

        @Override
        public void setJoy(int joy) {
            this.joy = joy;
        }

        @Override
        public CompoundNBT serializeNBT() {
            final CompoundNBT tag = new CompoundNBT();
            tag.putInt("joy", joy);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundNBT tag) {
            joy = tag.getInt("joy");
        }
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
        private IPat pat;

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == PatPatPat.PAT ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return getOrCreateCapability().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT tag) {
            getOrCreateCapability().deserializeNBT(tag);
        }

        @Nonnull
        IPat getOrCreateCapability() {
            if (pat == null) this.pat = new PatCapability.PatImpl();
            return this.pat;
        }
    }
}
