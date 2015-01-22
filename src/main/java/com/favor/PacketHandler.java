package com.favor;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessage {
	private String text;
	
	public PacketHandler(){}
	
	public PacketHandler(String text)
	{
		this.text = text;
	}
	
	public void fromBytes(ByteBuf buf)
	{
		text = ByteBufUtils.readUTF8String(buf);
	}
	
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, text);
	}

	public static class Handler implements IMessageHandler<PacketHandler, IMessage>
	{
		public IMessage onMessage(PacketHandler message, MessageContext context)
		{
			System.out.println(String.format("Recieved %s from %s", message.text, context.getServerHandler().playerEntity.getDisplayName()));
			return null;
		}
	}
}
