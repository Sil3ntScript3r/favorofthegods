package com.favorofthegods;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public abstract class Util {
	public static void sendChat(EntityPlayer player, String message, World world)
	{
		if(!world.isRemote)
		{
			player.addChatComponentMessage(new ChatComponentText(message));
		}
	}
}
