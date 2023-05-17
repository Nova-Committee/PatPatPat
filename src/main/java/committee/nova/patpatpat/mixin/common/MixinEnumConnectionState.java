package committee.nova.patpatpat.mixin.common;

import committee.nova.patpatpat.common.network.handler.PacketHandler;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnumConnectionState.class)
public abstract class MixinEnumConnectionState {
    @Shadow
    protected abstract EnumConnectionState registerPacket(EnumPacketDirection direction, Class<? extends Packet<?>> packetClass);

    @Mixin(targets = "net/minecraft/network/EnumConnectionState$2")
    public abstract static class Play extends MixinEnumConnectionState {
        @Inject(method = "<init>", at = @At("RETURN"))
        private void registerModPackets(CallbackInfo ci) {
            PacketHandler.registerPlayPacket(this::registerPacket);
        }
    }
}
