package com.favorofthegods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favorofthegods.altar.BlockAltar;

public class ClientOnlyProxy extends CommonProxy {
	@Override
	public void preInit()
	{
		super.preInit();
	}
	
	@Override
	public void init()
	{
		super.init();
		ItemModelMesher render = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		Item itemBlockAltar = GameRegistry.findItem(FavorOfTheGods.MODID, CommonProxy.altar.getName());
		render.register(itemBlockAltar, 0, new ModelResourceLocation(FavorOfTheGods.prependModID(CommonProxy.altar.getName(), ':'), "inventory"));
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
