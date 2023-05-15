package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.common.util.Utilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PatMessage implements IMessage {
    private int entityId;
    private int joy;

    public PatMessage() {
    }

    public void writeData(int entityId, int joy) {
        this.entityId = entityId;
        this.joy = joy;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        joy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(joy);
    }

    public static class Handler implements IMessageHandler<PatMessage, IMessage> {
        @Override
        public IMessage onMessage(PatMessage message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) return null;
            final Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                final EntityPlayer p = mc.player;
                if (p == null) return;
                final World w = p.world;
                if (w == null) return;
                final Entity e = w.getEntityByID(message.entityId);
                if (!Utilities.isPattable(e)) return;
                Utilities.setJoyForEntity(e, message.joy);
            });
            return null;
        }
    }
}
