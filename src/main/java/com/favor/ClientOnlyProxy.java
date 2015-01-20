package com.favor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ClientOnlyProxy extends CommonProxy {
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
		} else if(player instanceof EntityPlayerSP)
		{
			return Minecraft.getMinecraft().playerController.isInCreativeMode();
		}
		return false;
	}
}
