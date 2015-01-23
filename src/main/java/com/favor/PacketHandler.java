package com.favor;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
// TODO: FINISH THIS
public class PacketHandler implements IMessage {
	private int[] favors = new int[Favor.numGods];
	private EntityPlayer player = null;
	
	public PacketHandler(){}
	
	public PacketHandler(int[] favors, EntityPlayer player)
	{
		this.favors = favors;
		this.player = player;
	}
	
	public void fromBytes(ByteBuf buf)
	{
		for(int x = 0; x < Favor.numGods; x++)
		{
			favors[x] = ByteBufUtils.readVarShort(buf);
		}
	}
	
	public void toBytes(ByteBuf buf)
	{
		for(int x = 0; x < favors.length; x++)
		{
			ByteBufUtils.writeVarShort(buf, favors[x]);
		}
		
		ByteBufUtils.writeUTF8String(buf, player.get);
	}

	public static class Handler implements IMessageHandler<PacketHandler, IMessage>
	{
		public IMessage onMessage(PacketHandler message, MessageContext context)
		{
			//System.out.println(String.format("Received %s from %s", message, context.getServerHandler().playerEntity));
			System.out.println(message.player);
			System.out.println(message.favors + ": " + message.favors[0] + " : " + message.favors[1]);
			Favor favorProp = Favor.get(message.player);
			if(favorProp == null)
				return null;
			
			for(int x : message.favors)
			{
				favorProp.setFavor(message.favors[x], x);
			}
			return null;
		}
	}
}
