package com.favorofthegods;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.favorofthegods.altar.TileAltar;
import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class PacketFavors implements IMessage {
	private ArrayList<Integer> godFavors;
	private EntityPlayer player;
	private BlockPos altarPos;
	
	public PacketFavors(){}
	
	public PacketFavors(EntityPlayer player, BlockPos altarPos)
	{
		this.player = player;
		this.altarPos = altarPos;
	}

	public void fromBytes(ByteBuf buf)
	{
		godFavors = new ArrayList<Integer>();
		altarPos = new BlockPos(ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5));
		
		int size = ByteBufUtils.readVarInt(buf, 2);
		
		for(int i = 0; i < size; i++)
		{
			godFavors.add(i, ByteBufUtils.readVarInt(buf, 5));
		}
	}

	public void toBytes(ByteBuf buf)
	{
		Favor favor = FavorHandler.getFavor(player);
		if(favor != null)
		{
			ByteBufUtils.writeVarInt(buf, altarPos.getX(), 5);
			ByteBufUtils.writeVarInt(buf, altarPos.getY(), 5);
			ByteBufUtils.writeVarInt(buf, altarPos.getZ(), 5);
			
			List<Integer> godList = favor.getFavors();
			ByteBufUtils.writeVarInt(buf, godList.size(), 2);
			
			for(int i = 0; i < godList.size(); i++)
			{
				ByteBufUtils.writeVarInt(buf, godList.get(i), 5);
			}
		}
	}

	public static class Handler implements IMessageHandler<PacketFavors, IMessage>
	{

		public IMessage onMessage(PacketFavors message, MessageContext ctx)
		{
			System.out.println("Message received: syncing Favor");
			
			TileAltar altar = (TileAltar)Minecraft.getMinecraft().theWorld.getTileEntity(message.altarPos);
			if(altar != null)
			{
				System.out.println("Altar found; setting favor");
				altar.setFavors(message.godFavors);
			}
			
			return null;
		}
		
	}
}
