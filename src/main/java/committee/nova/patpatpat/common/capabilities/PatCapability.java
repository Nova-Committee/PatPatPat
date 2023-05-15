package committee.nova.patpatpat.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class PatCapability {
    public static final Capability<IPat> PAT = CapabilityManager.get(new CapabilityToken<>() {
    });

    public interface IPat extends INBTSerializable<CompoundTag> {
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
        public CompoundTag serializeNBT() {
            final CompoundTag tag = new CompoundTag();
            tag.putInt("joy", joy);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            joy = tag.getInt("joy");
        }
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private IPat pat;

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == PAT ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return getOrCreateCapability().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            getOrCreateCapability().deserializeNBT(tag);
        }

        @Nonnull
        IPat getOrCreateCapability() {
            if (pat == null) this.pat = new PatImpl();
            return this.pat;
        }
    }
}
