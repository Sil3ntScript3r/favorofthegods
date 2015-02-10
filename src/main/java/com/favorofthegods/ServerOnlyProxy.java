package com.favorofthegods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerOnlyProxy extends CommonProxy {
	public void preInit()
	{
		super.preInit();
	}
	
	public void init()
	{
		super.init();
	}
	
	public void postInit()
	{
		super.postInit();
	}
	
	public boolean playerIsInCreativeMode(EntityPlayer player)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			return playerMP.theItemInWorldManager.isCreative();
		}
		return false;
	}
}
