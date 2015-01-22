package com.favor;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessage {
	private int[] favors = new int[Favor.numGods];
	
	public PacketHandler(){}
	
	public PacketHandler(int[] favors)
	{
		this.favors = favors;
	}
	
	public void fromBytes(ByteBuf buf)
	{
		for(int x : favors)
		{
			
		}
	}
	
	public void toBytes(ByteBuf buf)
	{
		for(int x : favors)
		{
			ByteBufUtils.writeVarInt(buf, favors[x], 0);
		}
	}

	public static class Handler implements IMessageHandler<PacketHandler, IMessage>
	{
		public IMessage onMessage(PacketHandler message, MessageContext context)
		{
			System.out.println(String.format("Recieved %s from %s", message.favors, context.getServerHandler().playerEntity.getDisplayName()));
			
			return null;
		}
	}
}
