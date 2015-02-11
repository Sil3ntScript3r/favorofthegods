package com.favorofthegods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.favorofthegods.blocks.BlockListClient;

public class ClientOnlyProxy extends CommonProxy {
	@Override
	public void preInit()
	{
		super.preInit();
		BlockListClient.preInit();
	}
	
	@Override
	public void init()
	{
		super.init();
		BlockListClient.init();
	}
	
	@Override
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
