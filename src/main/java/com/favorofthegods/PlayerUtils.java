package com.favorofthegods;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public abstract class PlayerUtils {
	public static void sendChat(EntityPlayer player, String message)
	{
		if(!player.worldObj.isRemote)
		{
			player.addChatComponentMessage(new ChatComponentText(message));
		}
	}
}
