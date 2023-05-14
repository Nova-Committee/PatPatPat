package committee.nova.patpatpat.common.network.msg;

import committee.nova.patpatpat.common.util.Utilities;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
            final EntityPlayer p = Minecraft.getMinecraft().thePlayer;
            if (p == null) return null;
            final World w = p.worldObj;
            if (w == null) return null;
            final Entity e = w.getEntityByID(message.entityId);
            if (!Utilities.isPattable(e)) return null;
            Utilities.setJoyForEntity(e, message.joy);
            return null;
        }
    }
}
