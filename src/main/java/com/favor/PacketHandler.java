package com.favor;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessage {
	private int[] favors = new int[Favor.numGods];
	private EntityPlayer player;
	
	// Required, used when receiving a message(I think)
	public PacketHandler(){}
	
	// Init the Favor and Player variables when we send a message
	public PacketHandler(int[] favors, EntityPlayer player)
	{
		this.favors = favors;
		this.player = player;
	}
	
	// Decode the message
	public void fromBytes(ByteBuf buf)
	{
		for(int x = 0; x < Favor.numGods; x++)
		{
			favors[x] = ByteBufUtils.readVarInt(buf, 5);
		}
		
		// Get the player from the name given in the bytes
		String targetPlayer = ByteBufUtils.readUTF8String(buf);
		player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(targetPlayer);
	}
	
	// Encode the message
	public void toBytes(ByteBuf buf)
	{
		for(int x = 0; x < favors.length; x++)
		{
			ByteBufUtils.writeVarInt(buf, favors[x], 5);
		}
		
		ByteBufUtils.writeUTF8String(buf, player.getName());
		System.out.println(player.getName());
	}

	// Message receiver
	public static class Handler implements IMessageHandler<PacketHandler, IMessage>
	{
		public IMessage onMessage(PacketHandler message, MessageContext context)
		{
			//System.out.println(String.format("Received %s from %s", message, context.getServerHandler().playerEntity));
			System.out.println(message.player);
			System.out.println(message.favors[0] + " : " + message.favors[1]);
			Favor favorProp = Favor.get(message.player);
			if(favorProp == null)
				return null;
			
			// Re-assign everything
			for(int x = 0; x < message.favors.length; x++)
			{
				favorProp.setFavor(message.favors[x], x);
			}
			return null;
		}
	}
}
